package com.marantle.nutcracker.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * holds a single persons details
 */
public class Person {

	int personId;
	String personName;

	public int getPersonId() {
		return personId;
	}
	public String getPersonName() {
		return personName;
	}

	public Person(int personId, String personName) {
		this.personId = personId;
		this.personName = personName;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Person person = (Person) o;
		return personId == person.personId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(personId);
	}

	@Override
	public String toString() {
		return "Person{" +
				"personId=" + personId +
				", personName='" + personName + '\'' +
				'}';
	}
}
