package com.indra.api.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.indra.api.user.User;
import com.indra.api.user.UserService;

/**
 * 
 * @author arommartinez
 *
 */
@Component
public class AuthProvider {
	
	@Autowired
	protected UserService userservice;
	
	public User authUser(String email, String password){
		List<User> userlist = userservice.getLogin(email, password);
		if(userlist.size()==0)
			return null;
		else
			return userlist.get(0);
	}

}
