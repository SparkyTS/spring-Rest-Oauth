package com.SparkyTS.springRest.dao;

import java.util.List;

import com.SparkyTS.springRest.entity.Authority;
import com.SparkyTS.springRest.entity.User;

public interface UserDAO {

	
	List<User> findAll();
	
	User find(int userId);

	User add(User user, Authority authorities) throws Exception;

	void delete(int userId);
}

