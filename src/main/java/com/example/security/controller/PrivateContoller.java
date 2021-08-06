package com.example.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("private")
public class PrivateContoller {

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public String getPrivateHello(){
		return "private hello";
	}

}
