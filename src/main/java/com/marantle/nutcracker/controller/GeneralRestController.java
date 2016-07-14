package com.marantle.nutcracker.controller;

import com.marantle.nutcracker.model.Person;
import com.marantle.nutcracker.model.WorkDay;
import com.marantle.nutcracker.model.WorkDaySalary;
import com.marantle.nutcracker.model.WorkShift;
import com.marantle.nutcracker.repository.NutRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Marko on 13.7.2016.
 */
@RestController
public class GeneralRestController {

    @Autowired
    NutRepo repo;

    @RequestMapping("/salary/all")
    public List<WorkDaySalary> salaries() {
        return repo.listSalaries();
    }

    @RequestMapping("/workshift/all")
    public List<WorkShift> shifts() {
        return repo.listWorkShifts();
    }

    @RequestMapping("/workday/all")
    public List<WorkDay> workdays() {
        return repo.listWorkDays();
    }

    @RequestMapping("/person/all")
    public List<Person> persons() {
        return repo.listPersons();
    }
}
