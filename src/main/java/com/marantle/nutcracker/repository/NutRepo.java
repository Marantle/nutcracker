package com.marantle.nutcracker.repository;

import com.marantle.nutcracker.model.*;
import com.marantle.nutcracker.util.MyUtilities;

import static com.marantle.nutcracker.repository.DataHolder.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class to expose dataholders data
 */
public class NutRepo {

    private static NutRepo nutRepo = new NutRepo();


    private NutRepo(){}

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

    public WorkDaySalary findWorkDaySalary(int id, LocalDate date) {
        WorkDaySalary rtnDay = null;

        Optional<WorkDaySalary> result = salaries.stream()
                .filter(day ->
                        day.getPersonId() == id &&
                                (day.getWorkDate().equals(date) || Objects.isNull(date)))
                .findFirst();
        return result.isPresent() ? result.get() : new WorkDaySalary();
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

    public List<WorkDay> getPersonsWorkDay(int personId, LocalDate date) {
        return listWorkDays()
                .stream()
                .filter(day -> day.getPersonId() == personId)
                .collect(Collectors.toList());
    }

    public List<WorkDay> getPersonsWorkDays(int personId) {
        return listWorkDays()
                .stream()
                .filter(day -> day.getPersonId() == personId)
                .collect(Collectors.toList());
    }

    public WorkDaySalary getPersonsSalary(int personId, LocalDate date) {
        return listSalaries()
                .stream()
                .filter(salary ->
                        salary.getPersonId() == personId &&
                                salary.getWorkDate().equals(date))
                .findFirst()
                .get();
    }

    public List<WorkDaySalary> getPersonsSalaries(int personId) {
        return listSalaries()
                .stream()
                .filter(salary ->
                        salary.getPersonId() == personId)
                .collect(Collectors.toList());
    }

    public List<WorkShift> getPersonsWorkdays(int personId) {
        return listWorkShifts()
                .stream()
                .filter(day -> day.getPersonId() == personId)
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

    public void setSalaries(List<WorkDaySalary> salaries) {
        DataHolder.salaries.clear();
        DataHolder.salaries.addAll(salaries);
    }

    public void setPersons(List<Person> persons) {
        DataHolder.persons.clear();
        DataHolder.persons.addAll(persons);
    }

    public static NutRepo getInstance(){
        return nutRepo;
    }

    public List<WorkDay> listWorkDays() {
        return new ArrayList<WorkDay>(workDays);
    }

    public List<WorkDaySalary> listSalaries() {
        return new ArrayList<WorkDaySalary>(salaries);
    }

    public List<WorkShift> listWorkShifts() {
        ArrayList<WorkShift> list = new ArrayList<WorkShift>(shifts);
        list.sort(MyUtilities.getDataSorter());
        return list;
    }

    public List<Person> listPersons() {
        ArrayList<Person> list = new ArrayList<Person>(persons);
        return list;
    }


}
