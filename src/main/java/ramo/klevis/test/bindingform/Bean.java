package ramo.klevis.test.bindingform;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import ramo.klevis.vaadinaddon.annotations.ToTable;

public class Bean {

	// @NotNUll for validation

	@Override
	public String toString() {
		return "Bean [name=" + name + ", surname=" + surname + ", age=" + age
				+ "]";
	}

	
	@ToTable(columnName = "Name ")//for table later ignore for the form example
	@NotNull(message = "Please insert name field")
	private String name;
	
	@ToTable(columnName = "Surname")//for table later ignore for the form example
	@NotNull(message = "Please insert surname field")
	private String surname;
	
	@ToTable(columnName = "Age")//for table later ignore for the form example
	@Digits(message = "Not a number or out of bound", fraction = 4, integer = 4)
	private int age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
