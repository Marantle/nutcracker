package com.marantle.nutcracker;

import com.marantle.nutcracker.parser.DataParser;
import com.marantle.nutcracker.repository.NutRepo;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by Marko on 11.7.2016.
 */
public class RunSimpleTest {

    static NutRepo repo = NutRepo.getInstance();

    public static void main(String[] args) throws IOException, URISyntaxException {
        DataParser dataParser = new DataParser();

        dataParser.parseData("hourlist.csv");
        repo.setPersons(dataParser.getPersons());
        repo.setWorkShifts(dataParser.getShifts());
        repo.setWorkDays(dataParser.getWorkdays());
        repo.setSalaries(dataParser.getSalaries());

        repo.listMonthlySalaries().forEach(s -> {
            System.out.printf("%d %s %s %s %n", s.getPersonId(), s.getPersonName(), s.getMonthOfYear(), s.getTotalSalaryFormatted());
        });
    }


}
