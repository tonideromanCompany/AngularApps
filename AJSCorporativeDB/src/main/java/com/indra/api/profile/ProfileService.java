package com.indra.api.profile;

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
public class ProfileService {
	
	@Autowired
	 protected ProfileRepository repository;
	 
	 public void createProfile(Profile profile) {
	 repository.createProfile(profile);
	 }
	 
	 public void updateProfile(Profile profile) {
		 repository.updateProfile(profile);
	 }
	 
	 public void deleteProfile(Profile profile) {
		 repository.deleteProfile(profile);
	 }
	 
	 public List<Profile> getProfiles() {
		 return repository.getProfiles();
	 }
	 
	 public List<Profile> getProfilebyUser(int iduser) {
	 return repository.getProfileByUser(iduser);
	 }

}
