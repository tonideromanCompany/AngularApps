package com.indra.api.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.indra.api.image.Image;
import com.indra.api.image.ImageService;
import com.indra.api.image.ImageToSend;
import com.indra.api.profile.Profile;
import com.indra.api.profile.ProfileService;
import com.indra.api.profile.ProfileSignUp;
import com.indra.api.security.Auth;

/**
 * 
 * @author arommartinez
 *
 */

@Controller
public class ProfileApiController {
	
	@Autowired
	protected ProfileService profileservice;
	
	@Autowired
	protected ImageService imageservice;
	
	@Auth
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getProfiles() {
		List<Profile> profilelist = profileservice.getProfiles();
		return new ResponseEntity<List<Profile>>(profilelist, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> setProfile(@RequestBody ProfileSignUp profiletmp) {
		Profile profile = new Profile();
		profile.setIduser(profiletmp.getIduser()); profile.setCity(profiletmp.getCity());
		profile.setCountry(profiletmp.getCountry()); profile.setBirthday(profiletmp.getBirthday());
		profileservice.createProfile(profile);
		return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
	}
	
	@Auth
	@RequestMapping(value = "/profile/user/{id}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getProfile(@PathVariable(value = "id") int iduser) {
		List<Profile> profilelist = profileservice.getProfilebyUser(iduser);
		return new ResponseEntity<Profile>(profilelist.get(0), HttpStatus.OK);
	}
	
	@Auth
	@RequestMapping(value = "/profile/user/{id}/profileimage/{idimage}", method = RequestMethod.POST)
	public ResponseEntity<?> setProfileImage(@PathVariable(value = "id") int iduser, @PathVariable(value = "idimage") int idimage) throws IOException {
		List<Profile> profilelist = profileservice.getProfilebyUser(iduser);
		Profile profile = profilelist.get(0);
		profile.setImage(idimage);
		profileservice.updateProfile(profile);
		return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
	}
	
	@Auth
	@RequestMapping(value = "/profile/user/{id}/image", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getImage(@PathVariable(value = "id") int iduser) {
		List<Image> imagelist = imageservice.getImagesByUser(iduser);
		List<ImageToSend> imagelistFinal = new ArrayList<ImageToSend>();
		for(int i=0; i<imagelist.size();i++){
			ImageToSend image = new ImageToSend();
			image.setId(imagelist.get(i).getId());
			image.setString("profile/user/"+iduser+"/image/"+imagelist.get(i).getId());
			image.setTime(imagelist.get(i).getTime());
			imagelistFinal.add(image);
		}
		return new ResponseEntity<List<ImageToSend>>(imagelistFinal, HttpStatus.OK);
	}
	
	@Consumes("multipart/form-data")
	@RequestMapping(value = "/profile/user/{id}/image", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> setImage(@PathVariable(value = "id") int iduser, @RequestParam("files[]") MultipartFile file) throws IOException {
		Image imageFull = new Image();
		imageFull.setIduser(iduser);
		byte[] bytes = file.getBytes();
		System.out.println(bytes);
		imageFull.setImage(bytes);
		imageservice.createImage(imageFull);
		//SAVE IMAGE MINIMIZED
		return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/profile/user/{id}/image/{idimage}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getImageID(@PathVariable(value = "id") int iduser, @PathVariable(value = "idimage") int idimage) {
		final HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.IMAGE_PNG);
		List<Image> imagelist = imageservice.getImagesByUserById(iduser, idimage);
		return new ResponseEntity<byte[]>(imagelist.get(0).getImage(), headers, HttpStatus.OK);
	}
	
	@Auth
	@RequestMapping(value="/profile/user/{id}/image/{idimage}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<?> deleteImage(@PathVariable(value = "id") int iduser, @PathVariable(value = "idimage") int idimage) {
		Image image = imageservice.getImagesByUserById(iduser, idimage).get(0);
		imageservice.deleteImage(image);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	/****************************************/
	/*				FUNCTIONS				*/
	/****************************************/
	
}
