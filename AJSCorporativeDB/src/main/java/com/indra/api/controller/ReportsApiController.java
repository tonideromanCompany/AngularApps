package com.indra.api.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.indra.api.profile.Profile;
import com.indra.api.profile.ProfileService;
import com.indra.api.reports.UserComplete;
import com.indra.api.user.User;
import com.indra.api.user.UserService;

/**
 * 
 * @author arommartinez
 *
 */

@Controller
@RequestMapping("/report/")
public class ReportsApiController {
	
	@Autowired
	protected UserService userservice;
	
	@Autowired
	protected ProfileService profileservice;
	
	  @RequestMapping(value = "users/pdf", method = RequestMethod.POST)
	    public @ResponseBody ResponseEntity<?> setUsersReport(@RequestBody String filename) throws JRException, FileNotFoundException{
		  List<User> userlist = userservice.getUsers();
		  Map<String,Object> parameterMap = new HashMap<String,Object>();
		  JRDataSource JRdataSource = new JRBeanCollectionDataSource(userlist);
		  JasperReport report = JasperCompileManager.compileReport("C:/user-report.jrxml");
		  JasperPrint print = JasperFillManager.fillReport(report, parameterMap, JRdataSource);

		  OutputStream output = new FileOutputStream(new File("C:/"+filename+".pdf")); 
		  JasperExportManager.exportReportToPdfStream(print, output); 
		  
		  return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
	 
	    }
	  
	  @RequestMapping(value = "user/{email}/pdf", method = RequestMethod.POST)
	    public @ResponseBody ResponseEntity<?> setUserReport(@PathVariable(value = "email") String email, @RequestBody String filename) throws JRException, FileNotFoundException{
		  List<User> userlist = userservice.getUsersByEmail(email);
		  List<Profile> profilelist = profileservice.getProfilebyUser(userlist.get(0).getId());
		  List<UserComplete> usercomplete = setUserComplete(userlist.get(0),profilelist.get(0));
		  Map<String,Object> parameterMap = new HashMap<String,Object>();
		  JRDataSource JRdataSource = new JRBeanCollectionDataSource(usercomplete);
		  JasperReport report = JasperCompileManager.compileReport("C:/usercomplete-report.jrxml");
		  JasperPrint print = JasperFillManager.fillReport(report, parameterMap, JRdataSource);

		  OutputStream output = new FileOutputStream(new File("C:/"+filename+".pdf")); 
		  JasperExportManager.exportReportToPdfStream(print, output); 
		  
		  return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
	    }
	  
	  public List<UserComplete> setUserComplete(User user, Profile profile) {
		  UserComplete us = new UserComplete();
		  us.setId(user.getId()); us.setName(user.getName()); us.setSurname(user.getSurname());
		  us.setEmail(user.getEmail()); us.setPassword(user.getPassword()); us.setCity(profile.getCity());
		  us.setCountry(profile.getCountry()); us.setBirthday(profile.getBirthday());
		  List<UserComplete> uslist = new ArrayList<UserComplete>();
		  uslist.add(us);
		  return uslist;
	  }
}
