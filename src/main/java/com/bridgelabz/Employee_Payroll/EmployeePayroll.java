package com.bridgelabz.Employee_Payroll;

import java.time.LocalDate;

public class EmployeePayroll {
	int id;
	String name;
	double salary;
	public LocalDate startDate;
	
	public EmployeePayroll(int id, String name, double salary) {
		super();
		this.id = id;
		this.name = name;
		this.salary = salary;
		
	}
	public EmployeePayroll(Integer id, String name, Double salary, LocalDate startDate) {
		this(id,name,salary);
		this.startDate = startDate;
	}
	

	@Override
	public String toString() {
		return "EmployeePayRollData [id=" + id + ", name=" + name + ", salary=" + salary + "]";
	}
	@Override
	public boolean equals(Object object) {
		if(this == object)
			return true;
		if(object == null || getClass() != object.getClass())
			return false;
		EmployeePayroll that = (EmployeePayroll) object;
		return id == that.id && Double.compare(that.salary,  salary) == 0 && name.equals(that.name);
	}
}
