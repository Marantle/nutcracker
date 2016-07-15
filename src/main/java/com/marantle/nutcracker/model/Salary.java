package com.marantle.nutcracker.model;

import com.marantle.nutcracker.util.MyUtilities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Created by mlpp on 13.7.2016.
 */
public class Salary implements GenericData {

	private int personId;
	private LocalDate workDate;
	private BigDecimal regularSalary;
	private BigDecimal eveningSalary = new BigDecimal(0);
	private BigDecimal eveningCompensation = new BigDecimal(0);
	private BigDecimal overtimeSalary = new BigDecimal(0);
	private BigDecimal overtimeCompensation = new BigDecimal(0);
	private BigDecimal totalUncompensatedSalary = new BigDecimal(0);
	private BigDecimal totalSalary = new BigDecimal(0);

	public Salary() {}


	public Salary(int personId, LocalDate date) {
		this.personId = personId;
		this.workDate = date;
	}

	public Salary(WorkDay personsDay) {
		this.personId = personsDay.getPersonId();
		this.workDate = personsDay.getWorkDate();
	}

	public BigDecimal getOvertimeSalary() {
		return overtimeSalary;
	}

	public void setOvertimeSalary(BigDecimal overtimeSalary) {
		this.overtimeSalary = overtimeSalary;
		updateTotalSalary();
	}

	public String getOvertimeSalaryFormatted() {
		return MyUtilities.formatAsDollarString(overtimeSalary);
	}

	public BigDecimal getEveningSalary() {
		return eveningSalary;
	}

	public void setEveningSalary(BigDecimal eveningSalary) {
		this.eveningSalary = eveningSalary;
		updateTotalSalary();
	}

	public String getEveningSalaryFormatted() {
		return MyUtilities.formatAsDollarString(eveningSalary);
	}

	public BigDecimal getTotalSalary() {
		return totalSalary;
	}

	public void setTotalSalary(BigDecimal totalSalary) {
		this.totalSalary = totalSalary;
	}

	public String getTotalSalaryFormatted() {
		return MyUtilities.formatAsDollarString(totalSalary);
	}

	public LocalDate getWorkDate() {
		return workDate;
	}

	public BigDecimal getRegularSalary() {
		return regularSalary;
	}

	public void setRegularSalary(BigDecimal regularSalary) {
		this.regularSalary = regularSalary;
		updateTotalSalary();
	}

	public String getRegularSalaryFormatted() {
		return MyUtilities.formatAsDollarString(regularSalary);
	}
	private void updateTotalSalary() {
		totalSalary = regularSalary.add(eveningSalary).add(overtimeSalary);
	}
	public int getPersonId() {
		return personId;
	}
	public BigDecimal getEveningCompensation() {
		return eveningCompensation;
	}

	public void setEveningCompensation(BigDecimal eveningCompensation) {
		this.eveningCompensation = eveningCompensation;
	}

	public String getEveningCompensationFormatted() {
		return MyUtilities.formatAsDollarString(eveningCompensation);
	}

	public BigDecimal getOvertimeCompensation() {
		return overtimeCompensation;
	}

	public void setOvertimeCompensation(BigDecimal overtimeCompensation) {
		this.overtimeCompensation = overtimeCompensation;
	}

	public String getOvertimeCompensationFormatted() {
		return MyUtilities.formatAsDollarString(overtimeCompensation);
	}

	public BigDecimal getTotalUncompensatedSalary() {
		return totalUncompensatedSalary;
	}

	public void setTotalUncompensatedSalary(BigDecimal totalUncompensatedSalary) {
		this.totalUncompensatedSalary = totalUncompensatedSalary;
	}

	public String getTotalUncompensatedSalaryFormatted() {
		return MyUtilities.formatAsDollarString(overtimeCompensation);
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
}
