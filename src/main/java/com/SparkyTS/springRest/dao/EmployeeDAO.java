package com.SparkyTS.springRest.dao;

import java.util.List;

import com.SparkyTS.springRest.entity.Employee;

public interface EmployeeDAO {
	
	List<Employee> findAll();
	
	Employee find(int empId);

	Employee add(Employee employee);

	void delete(int employeeId);
}
