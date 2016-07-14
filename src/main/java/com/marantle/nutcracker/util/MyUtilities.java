package com.marantle.nutcracker.util;

import com.marantle.nutcracker.model.GenericData;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

/**
 * Created by mlpp on 12.7.2016.
 */
public class MyUtilities {

    public static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("d.M.yyyy");
    public static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("H:m");
    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#0.00");
    {DECIMAL_FORMAT.setRoundingMode(RoundingMode.HALF_UP);}

    public static String formatAsDollarString(double decimalValue){
        String formattedValue = String.format("$%s", DECIMAL_FORMAT.format(decimalValue));
        return formattedValue;
    }

    /**
     * Comparator to sort data by personid and workdate, applies to WorkDay, WorkDaySalary, WorkShift
     * or any other that implements the GenericData interface
     * @return comparator
     */
    public static Comparator<GenericData> getDataSorter() {
        return new Comparator<GenericData>() {
            @Override
            public int compare(GenericData ws1, GenericData ws2) {
                if (ws1.getPersonId() == ws2.getPersonId()) {
                    return ws1.getWorkDate().compareTo(ws2.getWorkDate());
                } else {
                    return (Integer.compare(ws1.getPersonId(), ws2.getPersonId()));
                }
            }
        };

    }

}

