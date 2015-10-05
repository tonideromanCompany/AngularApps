package com.indra.api.chat;

import java.util.HashMap;

import com.indra.api.user.User;

/**
 * 
 * @author arommartinez
 *
 */

public class ConnectionCache {
	
private static ConnectionCache INSTANCE = null;
	
	private ConnectionCache() {}
	
	HashMap<Integer, User> cache = new HashMap<Integer, User>();
	
	
	public static ConnectionCache getInstance() {
		if(INSTANCE == null) INSTANCE = new ConnectionCache();
		return INSTANCE;
	}
	
	public User get(Integer id) {
		return cache.get(id);
	}

	public void put(Integer id, User user){
		cache.put(id, user);
	}
	
	public void delete(Integer id){
		cache.remove(id);
	}
}
