package com.marantle.nutcracker;

import com.marantle.nutcracker.model.Salary;
import com.marantle.nutcracker.parser.DataParser;
import com.marantle.nutcracker.repository.NutRepo;
import com.marantle.nutcracker.util.MyUtilities;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Stream;

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
            Stream<Salary> sortedStream = repo.getPersonsSalaries(person.getPersonId())
                    .stream().sorted(MyUtilities.getDataSorter());
            sortedStream.forEach(salary -> {
//                System.out.println(repo.findWorkDay(salary.getPersonId(), salary.getWorkDate()));
                System.out.printf("%f %n", salary.getTotalSalary());
            });
        });

        repo.findMonthlySalaries().forEach(System.err::println);
        //print total salaries
        repo.listPersons().forEach(person -> {
            List<Salary> salaries = repo.getPersonsSalaries(person.getPersonId());
            double sum = salaries.stream().mapToDouble(p -> p.getTotalSalary().doubleValue()).sum();
            System.out.println(person);
            System.out.println(MyUtilities.formatAsDollarString(new BigDecimal(sum)));
        });
    }


}
