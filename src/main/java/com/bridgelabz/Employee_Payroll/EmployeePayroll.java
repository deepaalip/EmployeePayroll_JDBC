package com.bridgelabz.Employee_Payroll;

import java.time.LocalDate;

public class EmployeePayroll {
	int id;
	String name;
	double salary;
	public LocalDate startDate;
	
	public EmployeePayroll(int id, String name, double salary,LocalDate date) {
		super();
		this.id = id;
		this.name = name;
		this.salary = salary;
		this.startDate = date;
	}


	@Override
	public String toString() {
		return "EmployeePayRollData [id=" + id + ", name=" + name + ", salary=" + salary + "]";
	}
}
