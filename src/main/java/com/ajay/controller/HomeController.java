package com.ajay.controller;

import com.ajay.response.ApiResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class   HomeController {
	
	@GetMapping("")
	public ResponseEntity<ApiResponse> homeController(){


		ApiResponse res=new ApiResponse(
				"welcome to CoinQuest platform, it is  working fine",
				true
		);
		return new ResponseEntity<ApiResponse>(res,HttpStatus.ACCEPTED);
	}

}