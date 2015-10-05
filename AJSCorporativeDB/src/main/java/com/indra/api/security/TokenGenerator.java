package com.indra.api.security;

import java.util.UUID;

/**
 * 
 * @author arommartinez
 *
 */

public class TokenGenerator {
	
	public String createToken(){
		String token = UUID.randomUUID().toString().toUpperCase();
		return token;
	}

}
