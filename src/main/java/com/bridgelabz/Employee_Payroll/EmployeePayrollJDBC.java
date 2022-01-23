package com.bridgelabz.Employee_Payroll;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class EmployeePayrollJDBC {
static final String URL = "jdbc:mysql://localhost:3306/payroll_service";
	
	static final String USER_NAME = "root";
	static final String PASSWORD = "Chopda123456";
	
	public static void main(String[] args) throws ClassNotFoundException {
		Connection connection;
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		System.out.println("Driver Loaded.");
		
		listDrivers();
		
		try {
			//step-2:- establish a connection with data base.
			System.out.println("Connecting to database :- " + URL);
			connection = DriverManager.getConnection(URL , USER_NAME , PASSWORD);
			System.out.println("Connection is successfull " + connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static void listDrivers() {
	Enumeration<Driver> driverList = DriverManager.getDrivers();
	while(driverList.hasMoreElements()) {
		Driver driverClass = driverList.nextElement();
		System.out.println(" " + driverClass.getClass().getName());
	}
}
}