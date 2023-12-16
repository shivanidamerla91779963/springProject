package com.inn.cafe.rest;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path="/dashboard")
public interface DashBoardRest 
{
	@GetMapping(path="/details")
	ResponseEntity<Map<String,Object>> getCount();
}
