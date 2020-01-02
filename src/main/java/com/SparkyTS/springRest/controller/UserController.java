package com.SparkyTS.springRest.controller;

import java.util.List;

import com.SparkyTS.springRest.dao.ForgotPasswordDAO;
import com.SparkyTS.springRest.entity.ForgotPasswordReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SparkyTS.springRest.dao.UserDAO;
import com.SparkyTS.springRest.entity.Authority;
import com.SparkyTS.springRest.entity.RequestObject;
import com.SparkyTS.springRest.entity.User;

@RestController
@EnableResourceServer
@RequestMapping("/api")
@CrossOrigin(origins="http://localhost:4200")
public class UserController {
	
	@Autowired
	private UserDAO userDAO;

	@Autowired
	private ForgotPasswordDAO forgotPasswordDAO;
	
	@GetMapping("/users")
	public List<User> getUsers(){
		List<User> users = userDAO.findAll();
		return users;
	}
	
	@GetMapping("/users/{id}")
	public User getUser(@PathVariable int id){
		return userDAO.find(id);
	}
	
	@GetMapping("/users/forgotPassword")
	public ResponseEntity<String> handleForgotPasswordRequest(@RequestParam String email) {
		 final String SUCCESS = "{\"message\":\"reset link has been sent successfully\",\"type\":\"success\"}";
		 final String FAIL = "{\"message\":\"email doesn't exist\",\"type\":\"error\"}";
		 
		System.out.println("Received Password reset request for " + email);

		boolean verified = forgotPasswordDAO.verifyUser(email);
		boolean success = false;

		if(verified)
			success = forgotPasswordDAO.GenerateAndSendToken(email);

		return verified&&success ? new ResponseEntity<String>(SUCCESS, HttpStatus.OK): new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/users/resetPassword")
	public ResponseEntity<String> handleResetPasswordRequest(@RequestBody ForgotPasswordReq obj){
		final String SUCCESS = "{\"message\":\"Password has been reset successfully\",\"type\":\"success\"}";
		final String FAIL = "{\"message\":\"error in resetting password\",\"type\":\"error\"}";
		System.out.println("Hello : ");
		String password = obj.getNewPassword();
		String token = obj.getToken();
		try{
			forgotPasswordDAO.setNewPassword(token,password);
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/users")
	public User addUser(@RequestBody RequestObject obj) throws Exception {
		System.out.println("Received for adding new user: " + obj);
		User user = obj.getUser();
		Authority authority = new Authority(obj.getAuthority());
		userDAO.add(user, authority);
		return user;
	}
	
	@PutMapping("/users")
	public User updateUser(@RequestBody RequestObject obj) throws Exception {
		System.out.println(obj.getUser() + " " +new Authority(obj.getAuthority()));
		return userDAO.add(obj.getUser(),new Authority(obj.getAuthority()));
	}
	
	@DeleteMapping("/users/{id}")
	public User deleteUser(@PathVariable int id) throws Exception {
		
		User user = userDAO.find(id);
		
		if(user==null)
			throw new Exception("Please Enter Valid User_id");
		
		userDAO.delete(id);
		
		return user;
	}

}
