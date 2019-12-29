package com.SparkyTS.springRest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SparkyTS.springRest.dao.UserDetailsDAO;
import com.SparkyTS.springRest.entity.UserDetails;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="http://localhost:4200")
public class UserDetailsController {

	@Autowired
	UserDetailsDAO userDetailsDAO;
	
	@GetMapping("/userDetails")
	public UserDetails getUserDetails() throws Exception {
		UserDetails userDetails= userDetailsDAO.getUserDetails();
		if(userDetails!=null)
			return userDetails;
		else 
			throw new Exception("User is not logged in");
	}
}

