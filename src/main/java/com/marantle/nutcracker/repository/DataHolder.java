package com.marantle.nutcracker.repository;

import com.marantle.nutcracker.model.Person;
import com.marantle.nutcracker.model.WorkDay;
import com.marantle.nutcracker.model.WorkDaySalary;
import com.marantle.nutcracker.model.WorkShift;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

/**
 * static class to hold the parsed hour data, set to package private so only NutRepo can access it
 */
@Repository
class DataHolder {

    static Set<Person> persons = new HashSet<>();
    static Set<WorkShift> shifts = new HashSet<>();
    static Set<WorkDay> workDays = new HashSet<>();
    static Set<WorkDaySalary> salaries = new HashSet<>();
}
