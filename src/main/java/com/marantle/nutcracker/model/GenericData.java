package com.marantle.nutcracker.model;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Marko on 14.7.2016.
 */
public interface GenericData {
    public int getPersonId();
    public LocalDate getWorkDate();
}
