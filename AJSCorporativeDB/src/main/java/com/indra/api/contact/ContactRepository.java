package com.indra.api.contact;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author arommartinez
 *
 */

@Repository
public class ContactRepository {
	
	 @Autowired
	 protected SessionFactory sessionFactory;
	 
	 public void createContact(Contact contact) {
	 sessionFactory.getCurrentSession().save(contact);
	 }
	 
	 public void updateContact(Contact contact) {
		 sessionFactory.getCurrentSession().saveOrUpdate(contact);
	 }
	 
	 public void deleteContact(Contact contact) {
		 sessionFactory.getCurrentSession().delete(contact);
	 }
	 
	 //QUERY LIST FUNCTIONS
	 
	 @SuppressWarnings("unchecked")
	 public List<Contact> getContacts() {
	 return sessionFactory.getCurrentSession()
	  .createQuery("FROM Contact c")
	  .list();
	 }
	 
	 @SuppressWarnings("unchecked")
	 public List<Contact> getContactsByUser(int iduser) {
	 return sessionFactory.getCurrentSession()
	  .createQuery("FROM Contact c WHERE iduser=:ID")
	  .setInteger("ID", iduser)
	  .list();
	 }

}
