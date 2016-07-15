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

        repo.listMonthlySalaries().forEach(s -> {
            System.out.printf("%d %s %s %s %n", s.getPersonId(), s.getPersonName(), s.getMonthOfYear(), s.getTotalSalaryFormatted());
        });
    }


}
