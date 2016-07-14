package com.marantle.nutcracker.bootstrap;

import com.marantle.nutcracker.parser.DataParser;
import com.marantle.nutcracker.repository.NutRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;

@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private Logger log = Logger.getLogger(DataLoader.class);

    @Autowired
    private DataParser dataParser;

    @Autowired
    private NutRepo repo;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        try {
            dataParser.parseData("HourList201403.csv");
        } catch (IOException | URISyntaxException e) {
            log.fatal("Could not initialize data.", e);
            System.exit(0);
        }
        repo.setPersons(dataParser.getPersons());
        repo.setWorkShifts(dataParser.getShifts());
        repo.setWorkDays(dataParser.getWorkdays());
        repo.setSalaries(dataParser.getSalaries());

    }
}