package com.marantle.nutcracker.util;

import com.marantle.nutcracker.model.GenericData;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.function.Predicate;

/**
 * Created by mlpp on 12.7.2016.
 */
public class MyUtilities {

    public static final DateTimeFormatter CSV_DATE_FORMAT = DateTimeFormatter.ofPattern("d.M.yyyy");
    public static final DateTimeFormatter CSV_TIME_FORMAT = DateTimeFormatter.ofPattern("H:m");
    public static final DateTimeFormatter MONTH_FORMAT = DateTimeFormatter.ofPattern("yyyy/MM");
    //regular hourly salary
    public static final double REGULAR_WAGE = 3.75;
    //Salary incrase in the evening
    public static final double EVENING_COMPENSATION = 1.15;
    //Increment after which the compensation is increased
    public static final  int OVERTIME_INCREMENT = 2;
    //Overtime compensations, OVERTIME_INCREMMENT determines when the compensation will be increased, 2 -> 0,2,4
    public static final  double[] OVERTIME_COMPENSATIONS = {1.25, 1.5, 2};
    public static final  LocalTime EVENING_START = LocalTime.parse("18:01", CSV_TIME_FORMAT);
    public static final  LocalTime EVENING_END = LocalTime.parse("06:01", CSV_TIME_FORMAT);
    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#0.00");
    {DECIMAL_FORMAT.setRoundingMode(RoundingMode.HALF_UP);}

    public static String formatAsDollarString(BigDecimal decimalValue){
        String formattedValue = String.format("$%s", DECIMAL_FORMAT.format(decimalValue));
        return formattedValue;
    }

    /**
     * Comparator to sort data by personid and workdate, applies to WorkDay, Salary, WorkShift
     * or any other that implements the GenericData interface
     * @return comparator
     */
    public static Comparator<GenericData> getDataSorter() {
        return (ws1, ws2) -> {
            if (ws1.getPersonId() == ws2.getPersonId()) {
                return ws1.getWorkDate().compareTo(ws2.getWorkDate());
            } else {
                return (Integer.compare(ws1.getPersonId(), ws2.getPersonId()));
            }
        };

    }

}

