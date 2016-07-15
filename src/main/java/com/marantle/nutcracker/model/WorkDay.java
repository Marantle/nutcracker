package com.marantle.nutcracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * holds a warkday hour breakdown for a given date
 */
public class WorkDay implements GenericData {

    private int personId;
    private LocalDate workDate;
    private LocalTime allHours;
    private LocalTime regularHours;
    private LocalTime eveningHours;
    private LocalTime overtimeHours;

    public WorkDay(int personId, LocalDate workDate) {
        this.personId = personId;
        this.workDate = workDate;
    }
    public WorkDay() {
        allHours = LocalTime.of(0,0);
        regularHours = LocalTime.of(0,0);
        eveningHours = LocalTime.of(0,0);
        overtimeHours = LocalTime.of(0,0);
    }

    public int getPersonId() {
        return personId;
    }
    public void setPersonId(int personId) {

        this.personId = personId;
    }
    public LocalTime getRegularHours() {
        return regularHours;
    }
    public void setRegularHours(LocalTime regularHours) {
        this.regularHours = regularHours;
    }
    public LocalTime getEveningHours() {
        return eveningHours;
    }
    public void setEveningHours(LocalTime eveningHours) {
        this.eveningHours = eveningHours;
    }
    public LocalTime getOvertimeHours() {
        return overtimeHours;
    }
    public void setOvertimeHours(LocalTime overtimeHours) {
        this.overtimeHours = overtimeHours;
    }
    public LocalDate getWorkDate() {
        return workDate;
    }
    public void setWorkDate(LocalDate workDate) {
        this.workDate = workDate;
    }
    public LocalTime getAllHours() {
        return allHours;
    }
    public void setAllHours(LocalTime allHours) {
        this.allHours = allHours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        WorkDay workDay = (WorkDay) o;
        return personId == workDay.personId && Objects.equals(workDate, workDay.workDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personId, workDate);
    }

    @Override
    public String toString() {
        return "WorkDay{" +
                "personId=" + personId +
                ", workDate=" + workDate +
                ", allHours=" + allHours +
                ", regularHours=" + regularHours +
                ", eveningHours=" + eveningHours +
                ", overtimeHours=" + overtimeHours +
                '}';
    }
}
