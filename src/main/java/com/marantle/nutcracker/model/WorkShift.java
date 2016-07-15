package com.marantle.nutcracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;


/**
 * Holds the parsed information as is from the provided csv file
 */
public class WorkShift  implements GenericData {
    private int personId;
    private LocalDate workDate;
    private LocalDateTime start;
    private LocalDateTime end;


    public WorkShift() {}

    public WorkShift(int id, LocalDate date, LocalDateTime start, LocalDateTime end) {
        this.personId = id;
        this.workDate = date;
        this.start = start;
        this.end = end;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public LocalDate getWorkDate() {
        return workDate;
    }

    public void setWorkDate(LocalDate workDate) {
        this.workDate = workDate;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        WorkShift workShift = (WorkShift) o;
        return personId == workShift.personId &&
                Objects.equals(workDate, workShift.workDate) &&
                Objects.equals(start, workShift.start) &&
                Objects.equals(end, workShift.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personId, workDate, start, end);
    }

    @Override
    public String toString() {
        return "WorkShift{" +
                "personId=" + personId +
                ", workDate=" + workDate +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
