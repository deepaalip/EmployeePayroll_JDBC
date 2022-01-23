package com.bridgelabz.Employee_Payroll;


import java.util.ArrayList;
import java.util.Scanner;

import com.bridgelabz.Employee_Payroll.EmployeePayrollService.IOService;


public class EmployeePayrollMain {
	public static void main(String[] args) {
		ArrayList<EmployeePayroll> employeePayrollList = new ArrayList<>();
		
		EmployeePayrollService employPayrollService = new EmployeePayrollService(employeePayrollList);
		
		Scanner ConsoleInputReader = new Scanner(System.in);	
		employPayrollService.readEmployeePayrollData(ConsoleInputReader);
		employPayrollService.writeEmployeeData(IOService.CONSOLE_IO);
	}
}
