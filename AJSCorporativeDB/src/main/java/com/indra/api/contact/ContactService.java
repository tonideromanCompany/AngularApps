package com.indra.api.contact;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author arommartinez
 *
 */

@Transactional
@Service
public class ContactService {
	
	@Autowired
	 protected ContactRepository repository;
	 
	 public void createContact(Contact contact) {
	 repository.createContact(contact);
	 }
	 
	 public void updateContact(Contact contact) {
		 repository.updateContact(contact);
	 }
	 
	 public void deleteContact(Contact contact) {
		 repository.deleteContact(contact);
	 }
	 
	 public List<Contact> getContacts() {
		 return repository.getContacts();
	 }
	 
	 public List<Contact> getContactsbyUser(int iduser) {
	 return repository.getContactsByUser(iduser);
	 }
}
