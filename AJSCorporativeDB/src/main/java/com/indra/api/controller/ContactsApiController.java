package com.indra.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.indra.api.contact.Contact;
import com.indra.api.contact.ContactService;
import com.indra.api.security.Auth;
import com.indra.api.user.User;
import com.indra.api.user.UserService;

/**
 * 
 * @author arommartinez
 *
 */

@Controller
public class ContactsApiController {
	
	@Autowired
	protected ContactService contactservice;
	
	@Autowired
	protected UserService userservice;
	
	@Auth
	@RequestMapping(value = "/contacts/{id}", method = RequestMethod.GET)
	public @ResponseBody List<User> getContacts(@PathVariable(value="id") int id){
		List<Contact> contactslist = contactservice.getContactsbyUser(id);
		List<User> userlist = new ArrayList<User>();
		for(int i=0; i<contactslist.size();i++){
			List<User> tmplist = userservice.getUsersById(contactslist.get(i).getIdcontact());
			userlist.add(tmplist.get(0));
		}
		return userlist;
	}

}
