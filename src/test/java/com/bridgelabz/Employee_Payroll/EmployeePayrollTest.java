package com.bridgelabz.Employee_Payroll;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.bridgelabz.Employee_Payroll.EmployeePayrollService.IOService;

public class EmployeePayrollTest {
	
	 @Test
		public void given3Employees_WhenWrittenToFile_ShouldMatchEmployeeEntries()
		{
			EmployeePayroll[] arrayOfEmployees = {
					new EmployeePayroll(1, "Ratan Tata", 100000.0, LocalDate.now()),
					new EmployeePayroll(2, "Bill Gates", 200000.0, LocalDate.now()),
					new EmployeePayroll(3, "Elon Musk", 300000.0, LocalDate.now())
			};
			EmployeePayrollService employeePayrollService;
			employeePayrollService = new EmployeePayrollService(Arrays.asList(arrayOfEmployees));
			employeePayrollService.writeEmployeePayrollData(IOService.FILE_IO);
			
			employeePayrollService.printData(IOService.FILE_IO);
			long entries = employeePayrollService.countEntries(IOService.FILE_IO);
			Assert.assertEquals(3, entries);
			
		}
		@Test
		public void givenFile_WhenRead_ShouldReturnNumberOfEntries() {
			EmployeePayrollService employeePayrollService = new EmployeePayrollService();
			long entries = employeePayrollService.readDataFromFile(IOService.FILE_IO);
			Assert.assertEquals(3, entries);
		}
		@Test
		public void givenEmployeePayrollInDB_WhenRetrieved_ShouldMatchEmployeeCount(){
			
			EmployeePayrollService employeePayrollService = new EmployeePayrollService();
			List<EmployeePayroll> employeePayrollData = employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
			System.out.println(employeePayrollData.size());
			Assert.assertEquals(4, employeePayrollData.size());
		}
		@Test 
		public void givenNewSalaryForEmployee_WhenUpdated_ShouldSyncWithDB() {
			
			EmployeePayrollService employeePayrollService = new EmployeePayrollService();
			List<EmployeePayroll> employeePayrollData = employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
			employeePayrollService.updateEmployeeSalary("Bill", 7000000.00);
			
			boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Bill");
			Assert.assertTrue(result);
			
		}
}
