package com.indra.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.indra.api.security.AuthService;
import com.indra.api.security.UserCache;
import com.indra.api.user.User;
import com.indra.api.user.UserService;

/**
 * 
 * @author arommartinez
 *
 */

@Controller
public class AuthApiController {
	
	@Autowired
	AuthService authservice;
	
	@Autowired
	protected UserService userservice;
	
	@RequestMapping(value = "/log", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getLogin(@RequestParam(value="email") String email, @RequestParam(value="password") String password){
		String token = authservice.login(email, password);
		
		if(null == token)
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		else {
			final HttpHeaders headers = new HttpHeaders();
		    headers.set("Token", token);
		    Integer id = UserCache.getInstance().get(token).getId();
		    User currentuser = userservice.getUsersById(id).get(0);
			return new ResponseEntity<User> (currentuser, headers, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getLogout() {
		//Delete token of this user
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

}
