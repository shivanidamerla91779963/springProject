//UserRest is like UserController(abstraction)
package com.inn.cafe.rest;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inn.cafe.wrapper.UserWrapper;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path="/user")
public interface UserRest 
{
	//@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(path="/signup")
	public ResponseEntity<String> signUp(@RequestBody(required=true) Map<String,String> requestMap);
	
	//@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(path="/login")
	public ResponseEntity<String> login(@RequestBody(required=true) Map<String,String> requestMap);
	
	//@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping(path="/get")
	public ResponseEntity<List<UserWrapper>> getAllUser();
	
	//@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(path="/update")
	public ResponseEntity<String> update(@RequestBody(required=true) Map<String,String> requestMap);
	
	//@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping(path="/checkToken")
	public ResponseEntity<String> checkToken();
	
	//@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(path="/changePassword")
	public ResponseEntity<String> changePassword(@RequestBody Map<String,String> requestMap);
	
	//@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(path="/forgotPassword")
	public ResponseEntity<String> forgotPassword(@RequestBody Map<String,String> requestMap);
	
}

