package com.indra.api.security;

import java.util.HashMap;

import com.indra.api.user.User;

/**
 * 
 * @author arommartinez
 *
 */


public class UserCache {
	private static UserCache INSTANCE = null;
	
	private UserCache() {}
	
	HashMap<String, User> cache = new HashMap<String, User>();
	
	
	public static UserCache getInstance() {
		if(INSTANCE == null) INSTANCE = new UserCache();
		return INSTANCE;
	}
	
	public User get(String token) {
		return cache.get(token);
	}

	public void put(String token, User user){
		cache.put(token, user);
	}
	
	public void delete(String token){
		cache.remove(token);
	}
}
