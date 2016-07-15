package com.marantle.nutcracker.parser;


import com.marantle.nutcracker.model.Salary;
import com.marantle.nutcracker.model.Person;
import com.marantle.nutcracker.model.WorkDay;
import com.marantle.nutcracker.model.WorkShift;
import static com.marantle.nutcracker.util.MyUtilities.*;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * parses data for the given file into persons, salaries, workdays and workshifts
 */
public class DataParser {

    private Logger log = Logger.getLogger(DataParser.class);
    private Charset charset = Charset.forName("UTF-8");
    private String fileName;
    private List<Person> persons;
    private List<WorkShift> shifts;
    private List<WorkDay> workDays;
    private List<Salary> salaries;

    @Autowired
    private ResourceLoader resourceLoader;


    /**
     * populates the shifts and persons lists from the csv file
     * @throws IOException
     * @throws URISyntaxException
     */
    private void parseShiftsAndPersons()
            throws IOException, URISyntaxException {

        this.persons = new ArrayList<>();
        this.shifts = new ArrayList<>();
        List<String> erroneusLines = new ArrayList<>();
        List<String> fileLines;
        fileLines = getFileLines(fileName);

        //skip the header line
        fileLines.stream().skip(1).forEach((String lineInFile) -> {

            String[] splitLine = lineInFile.split(",");
//                try {
            String personName = splitLine[0];
            try {
                int personId = Integer.parseInt(splitLine[1]);
                LocalDate date = LocalDate.parse(splitLine[2], CSV_DATE_FORMAT);
                LocalTime startTime = LocalTime.parse(splitLine[3], CSV_TIME_FORMAT);
                LocalDateTime startDateTime = LocalDateTime.of(date, startTime);
                LocalTime endTime = LocalTime.parse(splitLine[4], CSV_TIME_FORMAT);
                LocalDateTime endDateTime = LocalDateTime.of(date, endTime);
                if (endTime.isBefore(startTime)) {
                    // shift must end on the next day, adjust
                    endDateTime = endDateTime.plusDays(1);
                }

                Person person = new Person(personId, personName);
                if (!this.persons.contains(person))
                    this.persons.add(person);
                WorkShift shift = new WorkShift(personId, date, startDateTime, endDateTime);
                this.shifts.add(shift);
            } catch (NumberFormatException e) {
                log.warn(String.format("Unable to read integer id from line [%s] ", lineInFile));
                erroneusLines.add(lineInFile);
            } catch (DateTimeParseException e) {
                log.warn(String.format("Unable to read date or time from line [%s], must match %s or %s ", lineInFile, "d.M.yyyy", "H:m"));
                erroneusLines.add(lineInFile);
            } catch (IndexOutOfBoundsException e) {
                log.warn(String.format("Index out of bounds when reading line, must have 5 elements [%s] ", lineInFile));
                erroneusLines.add(lineInFile);
            }
        });

        if (!erroneusLines.isEmpty()) {
            log.warn(String.format("%d lines failed to parse.", erroneusLines.size()));
        }
    }

    /**
     * Get the file contents using java.nio
     *
     * @param fileName
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */
    private List<String> getFilePath1(String fileName) throws URISyntaxException, IOException {
        URI fileUri = null;
        fileUri = DataParser.class.getClassLoader().getResource(fileName).toURI();
        List<String> list = Files.readAllLines(Paths.get(fileUri));
        return list;
    }

    /**
     * get the file contents using inputstream
     *
     * @param fileName
     * @return
     * @throws URISyntaxException
     */
    private List<String> getFilePath2(String fileName) throws URISyntaxException {
        Resource resource = resourceLoader.getResource("classpath:"+fileName);

        String[] lines = null;
        try (InputStream inputStream = resource.getInputStream()) {

            StringWriter writer = new StringWriter();
            IOUtils.copy(inputStream, writer, "UTF-8");
            String theString = writer.toString();
            lines = theString.split("\\r?\\n");
        } catch (IOException e) {
            log.fatal("Error reading csv file", e);
        }
        return Arrays.asList(lines);
    }

    /**
     * get a list if lines read from the given filename
     * @param fileName
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */
    private List<String> getFileLines(String fileName) throws URISyntaxException, IOException {
        List<String> list = null;
        if (Objects.isNull(resourceLoader)) {
            //if resourceloader is null, try alternative method
            list = getFilePath1(fileName);
        } else {
            list = getFilePath2(fileName);
        }
        return list;
    }

    /**
     * populates the workDays list with WorkDay object which contain hourly breakdown for a day
     */
    private void generateWorkDayData() {
        this.workDays = new ArrayList<>();
        Comparator<WorkShift> byByStartTime = (e1, e2) -> e1.getStart().compareTo(e2.getStart());
        this.shifts.stream().sorted(byByStartTime).forEach(shift -> {
            WorkDay workDay = null;
            workDay = fetchWorkDay(this.workDays, shift.getPersonId(), shift.getWorkDate());
            LocalDateTime shiftStart = shift.getStart();
            LocalDateTime shiftEnd = shift.getEnd();

            int timeIncrement = 15;
            //loop the shift 15 minutes at a time and check for evening or overtime each iteration
            while (shiftStart.isBefore(shiftEnd)) {
                shiftStart = shiftStart.plusMinutes(timeIncrement);
                workDay.setAllHours(workDay.getAllHours().plusMinutes(15));
                if (isInOvertime(workDay)) {
                    workDay.setOvertimeHours(workDay.getOvertimeHours().plusMinutes(timeIncrement));
                } else if (isEvening(shift.getWorkDate(), shiftStart)) {
                    workDay.setEveningHours(workDay.getEveningHours().plusMinutes(timeIncrement));
                } else {
                    workDay.setRegularHours(workDay.getRegularHours().plusMinutes(timeIncrement));
                }
            }

            if (!this.workDays.contains(workDay))
                this.workDays.add(workDay);
        });

    }

    /**
     * Returns a new WorkDay if one exists for personId and workDate, otherwise return a new WorkDay
     *
     * @param workDayList
     * @param personId
     * @param workDate    the LocalDate to find by
     * @return new or existing WorkDay
     */
    private WorkDay fetchWorkDay(List<WorkDay> workDayList, int personId, LocalDate workDate) {
        Optional<WorkDay> result = workDayList.stream()
                .filter(day -> day.getPersonId() == personId && day.getWorkDate().equals(workDate))
                .findFirst();
        WorkDay workDay;

        if (result.isPresent()) {
            workDay = result.get();
        } else {
            workDay = new WorkDay();
            workDay.setPersonId(personId);
            workDay.setWorkDate(workDate);
        }
        return workDay;
    }

    /**
     * Check if dateTime is in the evening, evening is 18:00 to 6:00
     *
     * @param workDate
     * @param dateTime
     * @return
     */
    private boolean isEvening(LocalDate workDate, LocalDateTime dateTime) {
        LocalDateTime eveningStartDateTime = LocalDateTime.of(workDate, EVENING_START);
        LocalDateTime eveningEndDateTime = LocalDateTime.of(workDate, EVENING_END).plusDays(1);
        LocalDateTime earlyEveningEndDateTime = LocalDateTime.of(workDate, EVENING_END);

        //if between 18:00 and 6:00 or before 6:00 in the morning

        return dateTime.isAfter(eveningStartDateTime) && dateTime.isBefore(eveningEndDateTime)
                || dateTime.isBefore(earlyEveningEndDateTime);
    }

    /**
     * @param newDay
     * @return if given WorkDay has gone to overtime
     */
    private boolean isInOvertime(WorkDay newDay) {
        return newDay.getAllHours().isAfter(LocalTime.of(8, 0));
    }

    private void calculateSalaries() {
        this.salaries = new ArrayList<>();
        this.workDays.forEach(personsWorkDay -> {
            Salary salary = calculateSalaryForDay(personsWorkDay);
            this.salaries.add(salary);
        });
    }

    /**
     * @param workDay a workDay to calculate
     * @return a Salary object that has calculated salaries for the given WorkDay and Person
     */
    private Salary calculateSalaryForDay(WorkDay workDay) {
        Salary salary = new Salary(workDay);

        LocalTime allHoursT = workDay.getAllHours();
        LocalTime regularHoursT = workDay.getRegularHours();
        LocalTime eveningHoursT = workDay.getEveningHours();
        LocalTime overtimeHoursT = workDay.getOvertimeHours();

        //convert time to decimal
        double allHours = allHoursT.getHour() + allHoursT.getMinute() / 60.0;
        double regularHours = regularHoursT.getHour() + regularHoursT.getMinute() / 60.0;
        double eveningHours = eveningHoursT.getHour() + eveningHoursT.getMinute() / 60.0;

        double overtimeSalary = 0;
        double overtimeCompensation = 0;
        double overtimeCounter = 0.0;
        double compensationMultiplier = OVERTIME_COMPENSATIONS[0];

        //calculate overtime compensation by looping through the overtime 15 minutes at a time
        while (overtimeHoursT.isAfter(LocalTime.of(0, 0))) {
            double quarterHour = 0.25;
            overtimeCounter += quarterHour;

            //limit maximum compensationLevel to last compensationLevel
            double compensationLevel = Math
                    .min(overtimeCounter / OVERTIME_INCREMENT, OVERTIME_COMPENSATIONS.length - 1);

            overtimeSalary += quarterHour * (REGULAR_WAGE * compensationMultiplier);

            //reduce regular wage to get just the compensation
            overtimeCompensation +=
                    quarterHour * (REGULAR_WAGE * compensationMultiplier - REGULAR_WAGE);

            //compensationLevel is whole, i.e. 0, 1, 2....n => increase compensation
            if (compensationLevel == (int) compensationLevel) {
                compensationMultiplier = OVERTIME_COMPENSATIONS[(int) compensationLevel];
            }
            overtimeHoursT = overtimeHoursT.minusMinutes(15);
        }

        salary.setTotalUncompensatedSalary(new BigDecimal(allHours * REGULAR_WAGE));
        salary.setRegularSalary(new BigDecimal(regularHours * REGULAR_WAGE));
        salary.setEveningSalary(new BigDecimal(eveningHours * (REGULAR_WAGE + EVENING_COMPENSATION)));
        salary.setEveningCompensation(new BigDecimal(eveningHours * EVENING_COMPENSATION));
        salary.setOvertimeSalary(new BigDecimal(overtimeSalary));
        salary.setOvertimeCompensation(new BigDecimal(overtimeCompensation));
        return salary;
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public void parseData(String file) throws IOException, URISyntaxException {
        this.fileName = file;
        parseShiftsAndPersons();
        generateWorkDayData();
        calculateSalaries();
    }

    public List<Person> getPersons() {
        return this.persons;
    }

    public List<WorkShift> getShifts() {
        return this.shifts;
    }

    public List<WorkDay> getWorkdays() {
        return this.workDays;
    }

    public List<Salary> getSalaries() {
        return salaries;
    }
}
