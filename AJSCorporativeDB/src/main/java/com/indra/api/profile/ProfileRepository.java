package com.indra.api.profile;

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
public class ProfileRepository {
	
	@Autowired
	protected SessionFactory sessionFactory;
	 
	public void createProfile(Profile profile) {
	sessionFactory.getCurrentSession().save(profile);
	}
	 
	public void updateProfile(Profile profile) {
		sessionFactory.getCurrentSession().saveOrUpdate(profile);
	}
	 
	public void deleteProfile(Profile profile) {
		sessionFactory.getCurrentSession().delete(profile);
	}
	 
	//QUERY LIST FUNCTIONS
	 
	@SuppressWarnings("unchecked")
	public List<Profile> getProfiles() {
	return sessionFactory.getCurrentSession()
	 .createQuery("FROM Profile p")
	 .list();
	}
	 
	@SuppressWarnings("unchecked")
	public List<Profile> getProfileByUser(int iduser) {
	return sessionFactory.getCurrentSession()
	 .createQuery("FROM Profile p WHERE iduser=:ID")
	 .setInteger("ID", iduser)
	 .list();
	}
}
