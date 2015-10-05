package com.indra.api.chat;

import org.springframework.stereotype.Service;

import com.indra.api.user.User;

/**
 * 
 * @author arommartinez
 *
 */

@Service
public class ConnectionService {
	
	public User getConnections(int id) {
		return ConnectionCache.getInstance().get(id);
	}
	
	public void setConnected(User user, int id) {
		ConnectionCache.getInstance().put(id, user);
	}
	
	public void setDisconnected(int id) {
		ConnectionCache.getInstance().delete(id);
	}
}
