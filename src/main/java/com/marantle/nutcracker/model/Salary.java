package com.marantle.nutcracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.marantle.nutcracker.util.MyUtilities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;


/**
 * holds a salary breakdown for a given date
 */
public class Salary implements GenericData {

    private int personId;
    @JsonIgnore
    private String personName;
    private LocalDate workDate;
    private BigDecimal regularSalary = new BigDecimal(0);
    private BigDecimal eveningSalary = new BigDecimal(0);
    private BigDecimal eveningCompensation = new BigDecimal(0);
    private BigDecimal overtimeSalary = new BigDecimal(0);
    private BigDecimal overtimeCompensation = new BigDecimal(0);
    private BigDecimal totalUncompensatedSalary = new BigDecimal(0);
    private BigDecimal totalSalary = new BigDecimal(0);

    public Salary() {
    }

    public Salary(Person person, LocalDate date) {
        this.personId = person.getPersonId();
        this.personName = person.getPersonName();
        this.workDate = date;
    }

    public Salary(int personId, LocalDate date) {
        this.personId = personId;
        this.workDate = date;
    }

    public Salary(WorkDay personsDay) {
        this.personId = personsDay.getPersonId();
        this.workDate = personsDay.getWorkDate();
    }


    public void setOvertimeSalary(BigDecimal overtimeSalary) {
        this.overtimeSalary = overtimeSalary;
        updateTotalSalary();
    }


    public void setEveningSalary(BigDecimal eveningSalary) {
        this.eveningSalary = eveningSalary;
        updateTotalSalary();
    }

    public int getPersonId() {
        return personId;
    }

    public LocalDate getWorkDate() {
        return workDate;
    }

    public String getMonthOfYear() {
        return workDate.format(MyUtilities.MONTH_FORMAT);
    }

    public BigDecimal getTotalSalary() {
        return totalSalary;
    }

    public String getTotalSalaryFormatted() {
        return MyUtilities.formatAsDollarString(totalSalary);
    }

    public BigDecimal getTotalUncompensatedSalary() {
        return totalUncompensatedSalary;
    }

    public String getTotalUncompensatedSalaryFormatted() {
        return MyUtilities.formatAsDollarString(totalUncompensatedSalary);
    }


    public BigDecimal getRegularSalary() {
        return regularSalary;
    }

    public String getRegularSalaryFormatted() {
        return MyUtilities.formatAsDollarString(regularSalary);
    }

    public BigDecimal getEveningSalary() {
        return eveningSalary;
    }

    public String getEveningSalaryFormatted() {
        return MyUtilities.formatAsDollarString(eveningSalary);
    }


    public BigDecimal getEveningCompensation() {
        return eveningCompensation;
    }

    public String getEveningCompensationFormatted() {
        return MyUtilities.formatAsDollarString(eveningCompensation);
    }

    public BigDecimal getOvertimeSalary() {
        return overtimeSalary;
    }

    public String getOvertimeSalaryFormatted() {
        return MyUtilities.formatAsDollarString(overtimeSalary);
    }

    public BigDecimal getOvertimeCompensation() {
        return overtimeCompensation;
    }

    public String getOvertimeCompensationFormatted() {
        return MyUtilities.formatAsDollarString(overtimeCompensation);
    }

    public void setTotalUncompensatedSalary(BigDecimal totalUncompensatedSalary) {
        this.totalUncompensatedSalary = totalUncompensatedSalary;
    }

    public void setTotalSalary(BigDecimal totalSalary) {
        this.totalSalary = totalSalary;
    }

    public void setRegularSalary(BigDecimal regularSalary) {
        this.regularSalary = regularSalary;
        updateTotalSalary();
    }


    private void updateTotalSalary() {
        totalSalary = regularSalary.add(eveningSalary).add(overtimeSalary);
    }


    public void setEveningCompensation(BigDecimal eveningCompensation) {
        this.eveningCompensation = eveningCompensation;
    }

    public void setOvertimeCompensation(BigDecimal overtimeCompensation) {
        this.overtimeCompensation = overtimeCompensation;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Salary that = (Salary) o;
        return personId == that.personId && Objects.equals(workDate, that.workDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personId, workDate);
    }

    @Override
    public String toString() {
        return "Salary{" +
                "personId=" + personId +
                ", workDate=" + workDate +
                ", totalSalary=" + totalSalary +
                ", regularSalary=" + regularSalary +
                ", eveningSalary=" + eveningSalary +
                ", eveningCompensation=" + eveningCompensation +
                ", overtimeSalary=" + overtimeSalary +
                ", overtimeCompensation=" + overtimeCompensation +
                '}';
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }
}
