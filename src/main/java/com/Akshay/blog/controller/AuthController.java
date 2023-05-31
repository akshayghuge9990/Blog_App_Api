package com.Akshay.blog.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Akshay.blog.exception.ApiException;
import com.Akshay.blog.payloads.JwtAuthRequest;
import com.Akshay.blog.payloads.JwtAuthResponse;
import com.Akshay.blog.payloads.UserDto;
import com.Akshay.blog.security.JwtTokenHelper;
import com.Akshay.blog.service.UserServiceI;

@RestController
@RequestMapping("/api")
public class AuthController {
	
	private static Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserServiceI userServiceI;
	
	/*
	 * @author Akshay
	 * @apiNote this is api  controller of createToken
	 * @param request
	 * @param 
	 * @return JwtAuthResponse
	 */

	@PostMapping("/auth/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {

		logger.info("Start the api method createToken in AuthController : {}",request);
		this.authenticate(request.getUsername(), request.getPassword());

		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());

		String token = this.jwtTokenHelper.generateToken(userDetails);

	
		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);

		logger.info("Complete the api method createToken in AuthController : {}",request);
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);

	}
	
	/*
	 * @author Akshay
	 * @apiNote this is api  controller of authenticate
	 * @param username
	 * @param password
	 * @return 
	 */


	private void authenticate(String username, String password) throws Exception {

		logger.info("Start the api method authenticate in AuthController : {}",username,password);
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);

		try {

			this.authenticationManager.authenticate(authenticationToken);

		} catch (BadCredentialsException e) {
			

			System.out.println("Invalid Details !! ");
			throw new ApiException("Invalid username and password !!");

		}
		logger.info("Complete the api method authenticate in AuthController : {}",username,password);
	}
	
	
	//register new user api
	@PostMapping("/auth/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto ) {
		
		UserDto registeredUser = this.userServiceI.registerNewUser(userDto);
		
		return new ResponseEntity<UserDto>(registeredUser,HttpStatus.CREATED);
		
	}


}
