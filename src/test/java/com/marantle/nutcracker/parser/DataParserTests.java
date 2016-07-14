package com.marantle.nutcracker.parser;

import com.marantle.nutcracker.model.Person;
import com.marantle.nutcracker.model.WorkDay;
import com.marantle.nutcracker.model.WorkDaySalary;
import com.marantle.nutcracker.model.WorkShift;
import com.marantle.nutcracker.repository.NutRepo;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class DataParserTests {

    //content of testhours.csv for reference
    private static String data;

    private static DataParser dataParser = new DataParser();

    private static NutRepo repo = NutRepo.getInstance();

    @BeforeClass
    public static void loadAndPrintFile() throws URISyntaxException, IOException {
        URI uri = DataParserTests.class.getClassLoader().getResource("testhours.csv").toURI();
        data = new String(Files.readAllBytes(Paths.get(uri)), Charset.forName("UTF-8"));
        System.out.printf("Testing data: %n %s %n", data);
        
        dataParser.parseData("testhours.csv");
        repo.clear();
        repo.setPersons(dataParser.getPersons());
        repo.setWorkShifts(dataParser.getShifts());
        repo.setWorkDays(dataParser.getWorkdays());
        repo.setSalaries(dataParser.getSalaries());
    }

    @Test
    public void parsedDataSizeShouldMatchRawData() throws FileNotFoundException {
        repo.listWorkShifts().forEach(System.out::println);
        assertEquals("There should be 8 shifts", 8, repo.listWorkShifts().size());
        assertEquals("5 persons with 1 workday each", 5, repo.listWorkDays().size());
    }

    @Test
    public void person1DataShouldMatchExpected() {
        int personId = 1;
        LocalDate dateToCheck = LocalDate.of(2015, 12, 13);
        Person person = repo.findPersonById(personId);
        WorkDay workDay = repo.getPersonsWorkDay(personId, dateToCheck);
        WorkDaySalary workDaySalary = getWorkDaySalaryForPerson(personId, dateToCheck);
        //just get one since there should only be one
        WorkShift shift = repo.getPersonsWorkShifts(personId, dateToCheck).get(0);

        String expectedName = "Marko Tester";
        String actualName = person.getPersonName();
        assertEquals("Name should match data", expectedName, actualName);

        //Regular hours and salary
        LocalTime expectedRegularHours = LocalTime.of(2, 0);
        LocalTime actualRegularHours = workDay.getRegularHours();
        assertEquals("16:00 to 18:00 hours of regular work => 2", expectedRegularHours,
                actualRegularHours);
        double actualRegularSalary = workDaySalary.getRegularSalary();
        double expectedRegularSalary = 7.5; //2:00 of regulartime
        assertEquals("2 times regular salary: 2 * 3.75 = 7.5", expectedRegularSalary,
                actualRegularSalary, 0.01);

        //Evening hours and salary
        LocalTime expectedEveningHours = LocalTime.of(6, 0);
        LocalTime actualEveningHours = workDay.getEveningHours();
        assertEquals("18:00 to 24:00 of evening work => 6", expectedEveningHours,
                actualEveningHours);
        double actualEveningSalary = workDaySalary.getEveningSalary();
        double expectedEveningSalary = 29.4; //6:00 of eveningtime
        assertEquals("6 times evening salary: 6 * (3.75 + 1.15) = 29.4 ", expectedEveningSalary,
                actualEveningSalary, 0.01);

        //Overtime hours and salary
        LocalTime expectedOvertimeHours = LocalTime.of(3, 15);
        LocalTime actualOvertimeHours = workDay.getOvertimeHours();
        assertEquals("00:00 to 03:15 of overtime due to passing 8 hours => 3:15",
                expectedOvertimeHours, actualOvertimeHours);
        double actualOvertimeSalary = workDaySalary.getOvertimeSalary();
        double expectedOvertimeSalary = 16.41; //3:15 of overtime
        assertEquals("Overtime calculation: 2 * (3.75 * 1.25) + 1.25 * (3.75 * 1.5) = 16.40625",
                expectedOvertimeSalary, actualOvertimeSalary, 0.01);

        //Total hours and salary
        LocalTime expectedAllHours = LocalTime.of(11, 15);
        LocalTime actualAllHours = workDay.getAllHours();
        assertEquals("16:00 to 3:15 hours of work => 11:15", expectedAllHours, actualAllHours);
        double actualTotalSalary = workDaySalary.getTotalSalary();
        double expectedTotalSalary = 53.31;
        assertEquals("Combined salary: (7.5 + 29.4 + 16.40625 = 53.30625", expectedTotalSalary,
                actualTotalSalary, 0.01);


    }

    private WorkDaySalary getWorkDaySalaryForPerson(int personId, LocalDate dateToCheck) {
        return repo.findWorkDaySalary(personId, dateToCheck);
    }

    @Test
    public void person5DataShouldMatchExpected() {
        int personId = 5;
        Person person = repo.findPersonById(personId);
        LocalDate dateToCheck = LocalDate.of(2015, 12, 18);
        WorkDay workDay = repo.getPersonsWorkDay(personId, dateToCheck);
        WorkDaySalary workDaySalary = getWorkDaySalaryForPerson(personId, dateToCheck);
        //just get one since there should only be one
        WorkShift shift = repo.getPersonsWorkShifts(personId, dateToCheck).get(0);

        String expectedName = "Mary Testing";
        String actualName = person.getPersonName();
        assertEquals("Name should match data", expectedName, actualName);

        //Regular hours and salary
        LocalTime expectedRegularHours = LocalTime.of(1, 0);
        LocalTime actualRegularHours = workDay.getRegularHours();
        assertEquals("6:00 to 7:00 hours of regular work => 1", expectedRegularHours,
                actualRegularHours);
        double actualRegularSalary = workDaySalary.getRegularSalary();
        double expectedRegularSalary = 3.75; //1:00 of regulartime
        assertEquals("1 times regular salary: 1 * 3.75 = 3.75", expectedRegularSalary,
                actualRegularSalary, 0.01);

        //Evening hours and salary
        LocalTime expectedEveningHours = LocalTime.of(7, 0);
        LocalTime actualEveningHours = workDay.getEveningHours();
        assertEquals("23:00 to 06:00 of evening work => 7", expectedEveningHours,
                actualEveningHours);
        double actualEveningSalary = workDaySalary.getEveningSalary();
        double expectedEveningSalary = 34.3; //7:00 of eveningtime
        assertEquals("6 times evening salary: 7 * (3.75 + 1.15) = 34.5 ", expectedEveningSalary,
                actualEveningSalary, 0.01);

        //Overtime hours and salary
        LocalTime expectedOvertimeHours = LocalTime.of(2, 45);
        LocalTime actualOvertimeHours = workDay.getOvertimeHours();
        assertEquals("7:00 to 9:45 of overtime due to passing 8 hours => 2", expectedOvertimeHours,
                actualOvertimeHours);
        double actualOvertimeSalary = workDaySalary.getOvertimeSalary();
        double expectedOvertimeSalary = 13.59; //3:15 of overtime
        assertEquals("Overtime calculation: 2 * (3.75 * 1.25) + 0.75 * (3.75 * 1.5) = 13.59375",
                expectedOvertimeSalary, actualOvertimeSalary, 0.01);

        //Total hours and salary
        LocalTime expectedAllHours = LocalTime.of(10, 45);
        LocalTime actualAllHours = workDay.getAllHours();
        assertEquals("23:00 to 9:45 hours of work => 10:45", expectedAllHours, actualAllHours);
        double actualTotalSalary = workDaySalary.getTotalSalary();
        double expectedTotalSalary = 51.64;
        assertEquals("Combined salary: (3.75 + 34.3 + 13.59375 = 51.64375", expectedTotalSalary,
                actualTotalSalary, 0.01);

    }

    @Test
    public void person2DataShouldMatchExpected() {
        int personId = 2;
        LocalDate dateToCheck = LocalDate.of(2015, 12, 14);
        Person person = repo.findPersonById(personId);
        WorkDay workDay = repo.getPersonsWorkDay(personId, dateToCheck);
        WorkDaySalary workDaySalary = getWorkDaySalaryForPerson(personId, dateToCheck);
        //just get one since there should only be one
        WorkShift shift = repo.getPersonsWorkShifts(personId, dateToCheck).get(0);

        String expectedName = "Tobias Testonimo";
        String actualName = person.getPersonName();
        assertEquals("Name should match data", expectedName, actualName);

        //Regular hours and salary
        LocalTime expectedRegularHours = LocalTime.of(6, 0);
        LocalTime actualRegularHours = workDay.getRegularHours();
        assertEquals("4:00 to 6:00 is evening so 6:00 to 12:00 hours of regular work => 6",
                expectedRegularHours, actualRegularHours);
        double actualRegularSalary = workDaySalary.getRegularSalary();
        double expectedRegularSalary = 22.5; //1:00 of regulartime
        assertEquals("6 times regular salary: 6 * 3.75 = 22.5", expectedRegularSalary,
                actualRegularSalary, 0.01);

        //Evening hours and salary
        LocalTime expectedEveningHours = LocalTime.of(2, 0);
        LocalTime actualEveningHours = workDay.getEveningHours();
        assertEquals("4:00 to 6:00 is evening => 2", expectedEveningHours, actualEveningHours);
        double actualEveningSalary = workDaySalary.getEveningSalary();
        double expectedEveningSalary = 9.8; //7:00 of eveningtime
        assertEquals("2 times evening salary: 2 * (3.75 + 1.15) = 9.8", expectedEveningSalary,
                actualEveningSalary, 0.01);

        //Overtime hours and salary
        LocalTime expectedOvertimeHours = LocalTime.of(0, 30);
        LocalTime actualOvertimeHours = workDay.getOvertimeHours();
        assertEquals("12:00 to 12:30 hours of overtime => 0:30", expectedOvertimeHours,
                actualOvertimeHours);
        double actualOvertimeSalary = workDaySalary.getOvertimeSalary();
        double expectedOvertimeSalary = 2.34; //3:15 of overtime
        assertEquals("0:30 of overtime: 0.5 * (3.75 * 1.25) = 2.34375", expectedOvertimeSalary,
                actualOvertimeSalary, 0.01);

        //Total hours and salary
        LocalTime expectedAllHours = LocalTime.of(8, 30);
        LocalTime actualAllHours = workDay.getAllHours();
        assertEquals("4:00 to 12:30 hours of work => 8:30", expectedAllHours, actualAllHours);
        double actualTotalSalary = workDaySalary.getTotalSalary();
        double expectedTotalSalary = 34.64;
        assertEquals("Combined salary: (22.5 + 0.8 + 2.34375 = 34,64375\n", expectedTotalSalary,
                actualTotalSalary, 0.01);
    }

    @Test
    public void person3DataShouldMatchExpected() {
        int personId = 3;
        LocalDate dateToCheck = LocalDate.of(2015, 12, 15);
        Person person = repo.findPersonById(personId);
        WorkDay workDay = repo.getPersonsWorkDay(personId, dateToCheck);
        WorkDaySalary workDaySalary = getWorkDaySalaryForPerson(personId, dateToCheck);
        List<WorkShift> personShifts = repo.getPersonsWorkShifts(personId, dateToCheck);

        int expectedShiftCount = 2;
        int actualShiftCount = personShifts.size();
        assertEquals(expectedShiftCount, actualShiftCount);

        String expectedName = "Jack Testeroo";
        personShifts.forEach(shift -> {
            String actualName = person.getPersonName();
            assertEquals(expectedName, actualName);
        });

        //Regular hours and salary
        LocalTime expectedRegularHours = LocalTime.of(6, 0);
        LocalTime actualRegularHours = workDay.getRegularHours();
        assertEquals("4:00 to 15:00, 2 hours of evening, 6 of regular before overtime -> 6",
                expectedRegularHours, actualRegularHours);
        double actualRegularSalary = workDaySalary.getRegularSalary();
        double expectedRegularSalary = 22.5; //6:00 of regulartime
        assertEquals("1 times regular salary: 6 * 3.75 = 22.5", expectedRegularSalary,
                actualRegularSalary, 0.01);

        //Evening hours and salary
        LocalTime expectedEveningHours = LocalTime.of(2, 0);
        LocalTime actualEveningHours = workDay.getEveningHours();
        assertEquals("4:00 to 6:00 => 2", expectedEveningHours, actualEveningHours);
        double actualEveningSalary = workDaySalary.getEveningSalary();
        double expectedEveningSalary = 9.8; //2:00 of eveningtime
        assertEquals("6 times evening salary: 2 * (3.75 + 1.15) = 9.8", expectedEveningSalary,
                actualEveningSalary, 0.01);

        //Overtime hours and salary
        LocalTime expectedOvertimeHours = LocalTime.of(7, 0);
        LocalTime actualOvertimeHours = workDay.getOvertimeHours();
        assertEquals("3 hours from 4:00 to 15:00 and 4 hours from 17:00 to 21:00 => 7",
                expectedOvertimeHours, actualOvertimeHours);
        double actualOvertimeSalary = workDaySalary.getOvertimeSalary();
        double expectedOvertimeSalary = 43.13; //7:00 of overtime
        assertEquals("Overtime calculation:"
                        + " 2 * (3.75 * 1.25) + 2 * (3.75 * 1.5) + 2 * (3.75 * 2) = 43.125",
                expectedOvertimeSalary, actualOvertimeSalary, 0.01);

        //Total hours and salary
        LocalTime expectedAllHours = LocalTime.of(15, 0);
        LocalTime actualAllHours = workDay.getAllHours();
        assertEquals("4:00 to 15:00 + 17:00 to 21:00 hours of work => 15", expectedAllHours,
                actualAllHours);
        double actualTotalSalary = workDaySalary.getTotalSalary();
        double expectedTotalSalary = 75.43;
        assertEquals("Combined salary: (22.5 + 9.8 + 43.125 = 75.425", expectedTotalSalary,
                actualTotalSalary, 0.01);
    }

    @Test
    public void person4DataShouldMatchExpected() {
        int personId = 4;
        Person person = repo.findPersonById(personId);
        LocalDate dateToCheck = LocalDate.of(2015, 12, 16);
        WorkDay workDay = repo.getPersonsWorkDay(personId, dateToCheck);
        WorkDaySalary workDaySalary = getWorkDaySalaryForPerson(personId, dateToCheck);
        List<WorkShift> personShifts = repo.getPersonsWorkShifts(personId, dateToCheck);

        int expectedShiftCount = 3;
        int actualShiftCount = personShifts.size();
        assertEquals(expectedShiftCount, actualShiftCount);

        String expectedName = "Jenny Tested";
        personShifts.forEach(shift -> {
            String actualName = person.getPersonName();
            assertEquals(expectedName, actualName);
        });

        //Regular hours and salary
        LocalTime expectedRegularHours = LocalTime.of(5, 0);
        LocalTime actualRegularHours = workDay.getRegularHours();
        assertEquals("1:00 to 3:00 is evening, so only 7:00 to 10:00 and 16:00 to 18:00 => 5",
                expectedRegularHours, actualRegularHours);
        double actualRegularSalary = workDaySalary.getRegularSalary();
        double expectedRegularSalary = 18.75; //5:00 of regulartime
        assertEquals("5 times regular salary: 5 * 3.75 =18.75", expectedRegularSalary,
                actualRegularSalary, 0.01);

        //Evening hours and salary
        LocalTime expectedEveningHours = LocalTime.of(3, 0);
        LocalTime actualEveningHours = workDay.getEveningHours();
        assertEquals("1:00 to 3:00 is evening, also 18:00 to 19:00 => 3", expectedEveningHours,
                actualEveningHours);
        double actualEveningSalary = workDaySalary.getEveningSalary();
        double expectedEveningSalary = 14.7; //3:00 of eveningtime
        assertEquals("6 times evening salary: 3 * (3.75 + 1.15) = 14.7", expectedEveningSalary,
                actualEveningSalary, 0.01);

        //Overtime hours and salary
        LocalTime expectedOvertimeHours = LocalTime.of(0, 0);
        LocalTime actualOvertimeHours = workDay.getOvertimeHours();
        assertEquals("Total day length is 8 => 0", expectedOvertimeHours, actualOvertimeHours);
        double actualOvertimeSalary = workDaySalary.getOvertimeSalary();
        double expectedOvertimeSalary = 0; //0:00 of overtime
        assertEquals("Overtime calculation: 0 = 0",
                expectedOvertimeSalary, actualOvertimeSalary, 0.01);

        //Total hours and salary
        LocalTime expectedAllHours = LocalTime.of(8, 0);
        LocalTime actualAllHours = workDay.getAllHours();
        assertEquals("1:00 to 3:00 + 7:00 to 10:00 + 16:00 to 19:00 hours of work => 8",
                expectedAllHours, actualAllHours);
        double actualTotalSalary = workDaySalary.getTotalSalary();
        double expectedTotalSalary = 33.45;
        assertEquals("Combined salary: (18.75 + 14.7 + 0 = 33.45", expectedTotalSalary,
                actualTotalSalary, 0.01);
    }
}
