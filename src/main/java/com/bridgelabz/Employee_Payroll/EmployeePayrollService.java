package com.bridgelabz.Employee_Payroll;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeePayrollService {
	
	private List<EmployeePayroll> employeePayrollList;
	private EmployeePayrollJDBC employeePayrollDBService;

	public enum IOService {
		CONSOLE_IO, FILE_IO, DB_IO, REST_IO
	}
	
	public EmployeePayrollService() {
		employeePayrollDBService =  EmployeePayrollJDBC.getInstance();
	}
	
	public EmployeePayrollService(List<EmployeePayroll> employeePayrollList) {
		this();
		this.employeePayrollList = employeePayrollList;
	}
	
	public void printData(IOService fileIo) {

		if(fileIo.equals(IOService.FILE_IO)) new EmployeePayrollFileIOService().printData();
	}

	public long countEntries(IOService fileIo) {
		if(fileIo.equals(IOService.FILE_IO)) 
			return new EmployeePayrollFileIOService().countEntries();
		return 0;
	}
	
	void readEmployeePayrollData(Scanner consoleInputReader){
		
		System.out.println("Enter the Employee Id : ");
		int id = consoleInputReader.nextInt();
		System.out.println("Enter the Employee Name : ");
		String name = consoleInputReader.next();
		System.out.println("Enter the Employee Salary : ");
		double salary = consoleInputReader.nextDouble();
		
		employeePayrollList.add(new EmployeePayroll(id, name, salary));
	}
	
	private EmployeePayroll getEmployeePayrollData(String name) {
		
		return this.employeePayrollList.stream()
				.filter(EmployeePayrollDataItem -> EmployeePayrollDataItem.name.equals(name))
				.findFirst()
				.orElse(null);
	}
	
	public long readDataFromFile(IOService fileIo) {
		
		List<String> employeePayrollFromFile = new ArrayList<String>();
		if(fileIo.equals(IOService.FILE_IO)) {
			System.out.println("Employee Details from payroll-file.txt");
			employeePayrollFromFile = new EmployeePayrollFileIOService().readDataFromFile();
			
		}
		return employeePayrollFromFile.size();
	}
	
	public List<EmployeePayroll>readEmployeePayrollData(IOService ioService) {
		
		if(ioService.equals(IOService.DB_IO))
			this.employeePayrollList = employeePayrollDBService.readData();
		return this.employeePayrollList;
		
	}	
	
	public void writeEmployeePayrollData(IOService ioService) {
		if(ioService.equals(IOService.CONSOLE_IO))
			System.out.println("\nWriting Employee Payroll Roster to Console\n" + employeePayrollList);
		
		else if(ioService.equals(IOService.FILE_IO))
			new EmployeePayrollFileIOService().writeData(employeePayrollList);
	}	
	
	public void updateEmployeeSalary(String name, double salary) {
		
		int result = employeePayrollDBService.updateEmployeeData(name,salary);
		if(result == 0) 
			return;
		
		EmployeePayroll employeePayrollData = this.getEmployeePayrollData(name);
		if(employeePayrollData != null)
			employeePayrollData.salary = salary;
		
	}
	
	public boolean checkEmployeePayrollInSyncWithDB(String name) {
		
		List<EmployeePayroll> employeePayrollDataList = employeePayrollDBService.getEmployeePayrollData(name);
		return employeePayrollDataList.get(0).equals(getEmployeePayrollData(name));
	}
	
	public List<EmployeePayroll> getEmployeeDetailsBasedOnStartDate(IOService ioService, String startDate) {
		if(ioService.equals(IOService.DB_IO))
			this.employeePayrollList = employeePayrollDBService.getEmployeeDetailsBasedOnStartDateUsingStatement(startDate);
		return this.employeePayrollList;
	}

    public List<EmployeePayroll> getEmployeeDetailsBasedOnStartDateUsingPreparedStatement(IOService ioService, String startDate) {
		
		if(ioService.equals(IOService.DB_IO))
			this.employeePayrollList = employeePayrollDBService.getEmployeeDetailsBasedOnStartDateUsingPreparedStatement(startDate);
		return this.employeePayrollList;
	}
	public List<EmployeePayroll> getEmployeeDetailsBasedOnName(IOService ioService, String name) {
		if(ioService.equals(IOService.DB_IO))
			this.employeePayrollList = employeePayrollDBService.getEmployeeDetailsBasedOnNameUsingStatement(name);
		return this.employeePayrollList;
	}
}
