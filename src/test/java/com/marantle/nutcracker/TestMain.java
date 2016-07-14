package com.marantle.nutcracker;

import com.marantle.nutcracker.model.WorkDaySalary;
import com.marantle.nutcracker.parser.DataParser;
import com.marantle.nutcracker.repository.NutRepo;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by Marko on 11.7.2016.
 */
public class TestMain {

    static NutRepo repo = NutRepo.getInstance();

    public static void main(String[] args) throws IOException, URISyntaxException {
        DataParser dataParser = new DataParser();

        dataParser.parseData("HourList201403.csv");
        repo.setPersons(dataParser.getPersons());
        repo.setWorkShifts(dataParser.getShifts());
        repo.setWorkDays(dataParser.getWorkdays());
        repo.setSalaries(dataParser.getSalaries());

        repo.listPersons().forEach(person -> {
            System.out.printf("%s has %d salary days, as follows: %n", person.getPersonName(), repo.getPersonsSalaries(person.getPersonId()).size());
            repo.getPersonsSalaries(person.getPersonId()).stream().sorted((o1, o2) -> (o1.getWorkDate().compareTo(o2.getWorkDate()))).forEach(salary -> {
                System.out.println(repo.findWorkDay(salary.getPersonId(), salary.getWorkDate()));
                System.out.printf("\t %s %s %n", salary.getWorkDate().toString(),salary.toString());
            });
        });
        repo.listPersons().forEach(person -> {
            List<WorkDaySalary> salaries = repo.getPersonsSalaries(person.getPersonId());
            double sum = salaries.stream().mapToDouble(p -> p.getTotalSalary()).sum();
            System.out.println(person);
            System.out.println(sum);
        });
    }



}
