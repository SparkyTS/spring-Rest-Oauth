package com.SparkyTS.springRest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SparkyTS.springRest.dao.EmployeeDAO;
import com.SparkyTS.springRest.entity.Employee;
import com.SparkyTS.springRest.entity.Response;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="http://localhost:4200")
@EnableResourceServer
public class EmployeeController {
	
	@Autowired
	private EmployeeDAO employeeDAO;

	
	

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_EMPLOYEE')")
	@GetMapping("/employees")
	public Response getEmployees(){
		List<Employee> employees = employeeDAO.findAll();
		return new Response(HttpStatus.FOUND.value(), "List of all employees", employees);
	}
	
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_EMPLOYEE')")
	@GetMapping("/employees/{employeeId}")
	public Response getEmployee(@PathVariable int employeeId) {
		
		Employee employee = employeeDAO.find(employeeId);
		
		//if(employee==null)
			//throw new EmployeeNotFoundExeception("Please Enter Valid Employee Id");
		
		return new Response(HttpStatus.FOUND.value(), "Requested Employee: ", employee);
	}
	
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
	@PostMapping("/employees")
	public Response addEmployee(@RequestBody Employee employee) {
		
		employee.setId(0);
		
		return new Response(HttpStatus.FOUND.value(), "Added New Employee: ", employeeDAO.add(employee));
	}
	
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
	@PutMapping("/employees")
	public Response updateEmployee(@RequestBody Employee employee) {
		return new Response(HttpStatus.FOUND.value(), "Updated Employee : ", employeeDAO.add(employee));
	}
	
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@DeleteMapping("/employees/{employeeId}")
	public Response deleteEmployee(@PathVariable int employeeId) {
		Employee employee = employeeDAO.find(employeeId);
//		if(employee==null)
//			throw new EmployeeNotFoundExeception("Please Enter Valid Employee Id");
		employeeDAO.delete(employeeId);
		return new Response(HttpStatus.FOUND.value(), "Deleted Employee : ", employee);
	}
}
