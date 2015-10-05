package com.indra.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.indra.api.user.User;

/**
 * 
 * @author arommartinez
 *
 */
@Service
public class AuthService {
	
	
	@Autowired
	protected AuthProvider provider;
	
	public String login(String email, String password) {
		User user = provider.authUser(email, password);
		if(user != null) {
		TokenGenerator tg = new TokenGenerator();
		String token = tg.createToken();
		UserCache.getInstance().put(token, user);
		return token;
		}
		else return null;
	}
	
	public void logout(String token){
		UserCache.getInstance().delete(token);
	}
	
	public User getTokenUser(String token) {
		return UserCache.getInstance().get(token);
	}
}
