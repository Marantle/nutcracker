package com.marantle.nutcracker.controller;

import com.marantle.nutcracker.model.Salary;
import com.marantle.nutcracker.model.Person;
import com.marantle.nutcracker.model.WorkDay;
import com.marantle.nutcracker.model.WorkShift;
import com.marantle.nutcracker.repository.NutRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * For testing and viewing raw json data
 */
@RestController
public class GeneralRestController {

    @Autowired
    NutRepo repo;

    @RequestMapping("/salaries/raw")
    public List<Salary> salaries() {
        return repo.listSalaries();
    }

    @RequestMapping("/salaries/monthly/raw")
    public List<Salary> monthlySalaries() {
        return repo.listMonthlySalaries();
    }

    @RequestMapping("/workshifts/raw")
    public List<WorkShift> shifts() {
        return repo.listWorkShifts();
    }

    @RequestMapping("/workdays/raw")
    public List<WorkDay> workdays() {
        return repo.listWorkDays();
    }

    @RequestMapping("/persons/raw")
    public List<Person> persons() {
        return repo.listPersons();
    }
}
