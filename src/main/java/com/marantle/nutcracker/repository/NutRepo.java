package com.marantle.nutcracker.repository;

import com.marantle.nutcracker.model.*;
import com.marantle.nutcracker.util.MyUtilities;
import org.springframework.stereotype.Service;

import static com.marantle.nutcracker.repository.DataHolder.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class to expose dataholders data
 */
@Service
public class NutRepo {


    private static NutRepo nutRepo = new NutRepo();


    private NutRepo() {
    }

    public Person findPersonById(int id) {
        Optional<Person> result = listPersons().stream().filter(persons -> persons.getPersonId() == id).findFirst();
        return result.isPresent() ? result.get() : new Person(0, "");
    }


    /**
     * find the WorkDay for a given personid and date
     *
     * @param personId
     * @param date
     * @return found workday or an empty one
     */
    public WorkDay findWorkDay(int personId, LocalDate date) {
        WorkDay rtnDay = null;
        Optional<WorkDay> result = workDays.stream()
                .filter(day -> day.getPersonId() == personId && day.getWorkDate().equals(date)).findFirst();

        return result.isPresent() ? result.get() : new WorkDay();
    }

    public Salary findSalary(int id, LocalDate date) {
        Salary rtnDay = null;

        Optional<Salary> result = salaries.stream()
                .filter(day ->
                        day.getPersonId() == id &&
                                (day.getWorkDate().equals(date) || Objects.isNull(date)))
                .findFirst();
        return result.isPresent() ? result.get() : new Salary();
    }

    /**
     * empty all data
     */
    public void clear() {
        persons.clear();
        shifts.clear();
        salaries.clear();
        workDays.clear();
    }

    public WorkDay getPersonsWorkDay(int personId, LocalDate date) {
        return listWorkDays()
                .stream()
                .filter(day -> day.getPersonId() == personId)
                .findFirst()
                .get();
    }

    public List<WorkDay> getPersonsWorkDays(int personId) {
        return listWorkDays()
                .stream()
                .filter(day -> day.getPersonId() == personId)
                .collect(Collectors.toList());
    }

    public Salary getPersonsSalary(int personId, LocalDate date) {
        return listSalaries()
                .stream()
                .filter(salary ->
                        salary.getPersonId() == personId &&
                                salary.getWorkDate().equals(date))
                .findFirst()
                .get();
    }

    public List<Salary> getPersonsSalaries(int personId) {
        return listSalaries()
                .stream()
                .filter(salary ->
                        salary.getPersonId() == personId)
                .collect(Collectors.toList());
    }

    public List<WorkShift> getPersonsWorkShifts(int personId) {
        return listWorkShifts()
                .stream()
                .filter(day -> day.getPersonId() == personId)
                .collect(Collectors.toList());
    }

    public List<WorkShift> getPersonsWorkShifts(int personId, LocalDate date) {
        return listWorkShifts()
                .stream()
                .filter(day -> day.getPersonId() == personId &&
                        day.getWorkDate().equals(date))
                .collect(Collectors.toList());
    }

    public void setWorkShifts(List<WorkShift> shifts) {
        DataHolder.shifts.clear();
        DataHolder.shifts.addAll(shifts);
    }

    public void setWorkDays(List<WorkDay> workDays) {
        DataHolder.workDays.clear();
        DataHolder.workDays.addAll(workDays);
    }

    public void setSalaries(List<Salary> salaries) {
        DataHolder.salaries.clear();
        DataHolder.salaries.addAll(salaries);
    }

    public void setPersons(List<Person> persons) {
        DataHolder.persons.clear();
        DataHolder.persons.addAll(persons);
    }

    public static NutRepo getInstance() {
        return nutRepo;
    }

    public List<WorkDay> listWorkDays() {
        ArrayList<WorkDay> list = new ArrayList<WorkDay>(workDays);
        list.sort(MyUtilities.getDataSorter());
        return list;
    }

    public List<Salary> listSalaries() {
        ArrayList<Salary> list = new ArrayList<Salary>(salaries);
        list.sort(MyUtilities.getDataSorter());
        return list;
    }

    public List<WorkShift> listWorkShifts() {
        ArrayList<WorkShift> list = new ArrayList<WorkShift>(shifts);
        list.sort(MyUtilities.getDataSorter());
        return list;
    }

    public List<Salary> listMonthlySalaries() {
        //get distinct months by creating list of dates with same the day and setting them to set which doesnt allow duplicates
        Set<LocalDate> months = getMonths();
        List<Salary> monthlySalaries = new ArrayList<>();
        //loop each persons each months each salary and reduce them
        persons.forEach(person -> {
            months.forEach(month -> {
                Salary monthlySalary = salaries.stream().filter(slry -> {
                    boolean check = slry.getWorkDate().getYear() == month.getYear();
                    check = check && slry.getWorkDate().getMonth() == month.getMonth();
                    check = check && slry.getPersonId() == person.getPersonId();
                    return check;
                }).reduce(new Salary(person, month), (s1, s2) -> {
                    //reduce all salaries in this stream into a single salary by summing them
                    s1.setEveningSalary(s1.getEveningSalary().add(s2.getEveningSalary()));
                    s1.setOvertimeSalary(s1.getOvertimeSalary().add(s2.getOvertimeSalary()));
                    s1.setRegularSalary(s1.getRegularSalary().add(s2.getRegularSalary()));
                    s1.setTotalUncompensatedSalary(s1.getTotalUncompensatedSalary().add(s2.getTotalUncompensatedSalary()));
                    s1.setEveningCompensation(s1.getEveningCompensation().add(s2.getEveningCompensation()));
                    s1.setOvertimeCompensation(s1.getOvertimeCompensation().add(s2.getOvertimeCompensation()));
                    return s1;
                });
                monthlySalaries.add(monthlySalary);
            });
        });
        //sort the data by month and then user
        monthlySalaries.sort((s1, s2) -> {
            if (s1.getMonthOfYear().equals(s2.getMonthOfYear()))
                return Integer.compare(s1.getPersonId(), s2.getPersonId());
            else
                return s1.getMonthOfYear().compareTo(s2.getMonthOfYear());
        });
        return monthlySalaries;
    }

    private Set<LocalDate> getMonths() {
        return salaries.stream()
                .map(salary -> LocalDate.of(salary.getWorkDate().getYear(), salary.getWorkDate().getMonth(), 1))
                .collect(Collectors.toSet());
    }

    public List<Person> listPersons() {
        ArrayList<Person> list = new ArrayList<Person>(persons);
        return list;
    }


}
