package com.indra.api.user;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.indra.api.user.User;

/**
 * 
 * @author arommartinez
 *
 */

@Repository
public class UserRepository {
	
	 @Autowired
	 protected SessionFactory sessionFactory;
	 
	 public void createUser(User user) {
	 sessionFactory.getCurrentSession().save(user);
	 }
	 
	 public void updateUser(User user) {
		 sessionFactory.getCurrentSession().saveOrUpdate(user);
	 }
	 
	 public void deleteUser(User user) {
		 sessionFactory.getCurrentSession().delete(user);
	 }
	 
	 //QUERY LIST FUNCTIONS
	 
	 @SuppressWarnings("unchecked")
	 public List<User> getUsers() {
	 return sessionFactory.getCurrentSession()
	  .createQuery("FROM User u")
	  .list();
	 }
	 
	 @SuppressWarnings("unchecked")
	 public List<User> getUsersSearch(String term) {
	 return sessionFactory.getCurrentSession()
	  .createQuery("FROM User u WHERE name LIKE :T OR surname LIKE :T")
	  .setString("T", "%"+term+"%")
	  .list();
	 }
	 
	 @SuppressWarnings("unchecked")
	 public List<User> getUsersById(int iduser) {
	 return sessionFactory.getCurrentSession()
	  .createQuery("FROM User u WHERE id=:ID")
	  .setInteger("ID", iduser)
	  .list();
	 }
	 
	 @SuppressWarnings("unchecked")
	 public List<User> getLogin(String email, String password) {
		 return sessionFactory.getCurrentSession()
		 .createQuery("FROM User WHERE email=:N AND password=:P")
		 .setString("N", email)
		 .setString("P", password)
		 .list();
	 }
	 
	 @SuppressWarnings("unchecked")
	 public List<User> getUsersByEmail(String email) {
	 return sessionFactory.getCurrentSession()
	  .createQuery("FROM User u WHERE email=:E")
	  .setString("E", email)
	  .list();
	 }
	 
}
