package com.bridgelabz.Employee_Payroll;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollJDBC {
	private PreparedStatement employeePayrollDataStatement;
	private static EmployeePayrollJDBC employeePayrollJDBC;

	private EmployeePayrollJDBC() {
		
	}
	
	public static EmployeePayrollJDBC getInstance() {
		if(employeePayrollJDBC == null)
			employeePayrollJDBC = new EmployeePayrollJDBC();
		return employeePayrollJDBC;
	}
	
	private Connection getConnection() throws SQLException {
		
		String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service";
		String userName = "root";
		String password = "Chopda123456";
		Connection connection;
		
		System.out.println("Connecting to the database : "+jdbcURL);
		connection = DriverManager.getConnection(jdbcURL, userName, password);
		System.out.println("Connection is Succcessfully Established!! "+connection);
		
		return connection;
	}
	private List<EmployeePayroll> getEmployeePayrollData(ResultSet resultSet) {
		
		List<EmployeePayroll> employeePayrollList = new ArrayList<>();
		
		try {
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				double basicSalary = resultSet.getDouble("salary");
				LocalDate startDate = resultSet.getDate("start").toLocalDate();
				employeePayrollList.add(new EmployeePayroll(id, name, basicSalary, startDate));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return employeePayrollList;
		
	}
	public List<EmployeePayroll> getEmployeePayrollData(String name) {
		
		List<EmployeePayroll> employeePayrollList = null;
		if(this.employeePayrollDataStatement == null)
			this.prepareStatementForEmployeeData();
		try {
			employeePayrollDataStatement.setString(1,name);
			ResultSet resultSet = employeePayrollDataStatement.executeQuery();
			employeePayrollList = this.getEmployeePayrollData(resultSet);	
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return employeePayrollList;
	}
	public List<EmployeePayroll> readData(){
		
		String sqlStatement = "SELECT emp_id, emp_name, basic_pay, start FROM employee JOIN payroll ON employee.payroll_id = payroll.payroll_id;";
		List<EmployeePayroll> employeePayrollList = new ArrayList<>();
				
		try (Connection connection = getConnection();){
			java.sql.Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sqlStatement);
			
			while(resultSet.next()) {
				int id = resultSet.getInt("emp_id");
				String name = resultSet.getString("emp_name");
				double basicSalary = resultSet.getDouble("basic_pay");
				LocalDate startDate = resultSet.getDate("start").toLocalDate();
				employeePayrollList.add(new EmployeePayroll(id, name, basicSalary, startDate));
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return employeePayrollList;
	}
	public int updateEmployeeData(String name, double salary) {
		
		return this.updateEmployeeDataUsingStatement(name,salary);
	}

	private int updateEmployeeDataUsingStatement(String name, double salary) {
		
		String sqlStatement = String.format("UPDATE payroll ,employee SET net_pay = %2f WHERE employee.payroll_id = payroll.payroll_id AND emp_name = '%s';", salary, name);
		
		try (Connection connection = getConnection()){
			java.sql.Statement statement = connection.createStatement();
			return statement.executeUpdate(sqlStatement);
		}
		catch(SQLException e){
			e.printStackTrace();
		}		
		return 0;
	}
	private void prepareStatementForEmployeeData() {
		
		try {
			Connection connection = this.getConnection();
			String sqlStatement = "SELECT * FROM employee,payroll WHERE employee.payroll_id = payroll.payroll.id AND name = ?;";
			employeePayrollDataStatement = connection.prepareStatement(sqlStatement);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}