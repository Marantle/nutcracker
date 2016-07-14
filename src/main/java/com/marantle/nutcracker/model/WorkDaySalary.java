package com.marantle.nutcracker.model;

import com.marantle.nutcracker.util.MyUtilities;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Created by mlpp on 13.7.2016.
 */
public class WorkDaySalary  implements GenericData {

	private int personId;
	private LocalDate workDate;
	private double regularSalary;
	private double eveningSalary;
	private double eveningCompensation;
	private double overtimeSalary;
	private double overtimeCompensation;
	private double totalUncompensatedSalary;
	private double totalSalary;


	public WorkDaySalary() {}

	public WorkDaySalary(WorkDay personsDay) {
		this.personId = personsDay.getPersonId();
		this.workDate = personsDay.getWorkDate();
	}


	public void setRegularSalary(double regularSalary) {
		this.regularSalary = regularSalary;
		updateTotalSalary();
	}

	public void setEveningSalary(double eveningSalary) {
		this.eveningSalary = eveningSalary;
		updateTotalSalary();
	}


	public void setOvertimeSalary(double overtimeSalary) {
		this.overtimeSalary = overtimeSalary;
		updateTotalSalary();
	}

	public double getOvertimeSalary() {
		return overtimeSalary;
	}
	public String getOvertimeSalaryFormatted() {
		return MyUtilities.formatAsDollarString(overtimeSalary);
	}
	public double getEveningSalary() {
		return eveningSalary;
	}
	public String getEveningSalaryFormatted() {
		return MyUtilities.formatAsDollarString(eveningSalary);
	}
	public double getTotalSalary() {
		return totalSalary;
	}
	public String getTotalSalaryFormatted() {
		return MyUtilities.formatAsDollarString(totalSalary);
	}
	public LocalDate getWorkDate() {
		return workDate;
	}
	public double getRegularSalary() {
		return regularSalary;
	}
	public String getRegularSalaryFormatted() {
		return MyUtilities.formatAsDollarString(regularSalary);
	}
	private void updateTotalSalary() {
		totalSalary = regularSalary + eveningSalary + overtimeSalary;
	}
	public int getPersonId() {
		return personId;
	}
	public double getEveningCompensation() {
		return eveningCompensation;
	}
	public String getEveningCompensationFormatted() {
		return MyUtilities.formatAsDollarString(eveningCompensation);
	}
	public void setEveningCompensation(double eveningCompensation) {
		this.eveningCompensation = eveningCompensation;
	}
	public double getOvertimeCompensation() {
		return overtimeCompensation;
	}
	public String getOvertimeCompensationFormatted() {
		return MyUtilities.formatAsDollarString(overtimeCompensation);
	}
	public void setOvertimeCompensation(double overtimeCompensation) {
		this.overtimeCompensation = overtimeCompensation;
	}
	public double getTotalUncompensatedSalary() {
		return totalUncompensatedSalary;
	}
	public String getTotalUncompensatedSalaryFormatted() {
		return MyUtilities.formatAsDollarString(overtimeCompensation);
	}
	public void setTotalUncompensatedSalary(double totalUncompensatedSalary) {
		this.totalUncompensatedSalary = totalUncompensatedSalary;
	}



	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		WorkDaySalary that = (WorkDaySalary) o;
		return personId == that.personId && Objects.equals(workDate, that.workDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(personId, workDate);
	}

	@Override
	public String toString() {
		return "WorkDaySalary{" +
				"personId=" + personId +
				", regularSalary=" + regularSalary +
				", eveningSalary=" + eveningSalary +
				", eveningCompensation=" + eveningCompensation +
				", overtimeSalary=" + overtimeSalary +
				", overtimeCompensation=" + overtimeCompensation +
				", totalSalary=" + totalSalary +
				", workDate=" + workDate +
				'}';
	}
}
