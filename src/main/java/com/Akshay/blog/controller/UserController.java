package com.Akshay.blog.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Akshay.blog.config.AppConstatnt;
import com.Akshay.blog.payloads.ApiResponse;
import com.Akshay.blog.payloads.UserDto;
import com.Akshay.blog.service.UserServiceI;

@RestController
@RequestMapping("/api")
public class UserController {
	
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserServiceI userServiceI;
	
	
	// post-create user
	
		/*
		 * @author Akshay
		 * @apiNote this api is  used to  createUser
		 * @param userDto
		 * @param  
		 * @return UserDto
		 */
	@PostMapping("/users")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {

		logger.info("start the api method createUser in UserController : {}",userDto);
		
		UserDto createUserDto = this.userServiceI.createUser(userDto);
		
		logger.info("Complete the api method createUser in UserController : {}",userDto);

		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);

	}
	
	/*
	 * @author Akshay
	 * @apiNote this api is  used to  updateuser
	 * @param userDto
	 * @param  uid
	 * @return UserDto
	 */
	
	// Put- update user
		@PutMapping("/users/{userId}")
		public ResponseEntity<UserDto> updateuser(@Valid @RequestBody UserDto userDto,
				@PathVariable("userId") Integer uid) {

			logger.info("start the api method updateuser in UserController :{}",userDto,uid);
			
			UserDto updateUser = this.userServiceI.updateUser(userDto, uid);
			logger.info("Complete the api method updateuser in UserController: {}",userDto,uid);
			
			return ResponseEntity.ok(updateUser);

		}
	
	
		/*
		 * @author Akshay
		 * @apiNote this api is  used to  deleteUser
		 * @param userId
		 * @param  
		 * @return 
		 */
		
		//ADMIN
		// Delete- delete user
		@PreAuthorize("hasRole('ADMIN')")
		@DeleteMapping("/users/{userId}")
		public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId) {

			logger.info("Start the api method deleteUser in UserController:{}",userId);
			
			this.userServiceI.deleteUser(userId);

			logger.info("Complete the api method deleteUser in UserController:{}",userId);
			return new ResponseEntity<ApiResponse>(new ApiResponse(AppConstatnt.DELETE_USER, true), HttpStatus.OK);

		}
		
		/*
		 * @author Akshay
		 * @apiNote this api is  used to  getAllUser
		 * @param 
		 * @param  
		 * @return List<UserDto>
		 */


		// Get- user get
		@GetMapping("/users")
		public ResponseEntity<List<UserDto>> getAllUser() {
			logger.info("Start the api method getAllUser in UserController:");
			
			return ResponseEntity.ok(this.userServiceI.getAllUser());

		}
		
		/*
		 * @author Akshay
		 * @apiNote this api is  used to  getAllUser
		 * @param 
		 * @param  
		 * @return UserDto
		 */


		// Get- Single User
		@GetMapping("/users/{userId}")
		public ResponseEntity<UserDto> getSingleEntity(@PathVariable Integer userId) {

			logger.info("Statrt the api method getSingleEntity in UserController:");
			
			return ResponseEntity.ok(this.userServiceI.getUserById(userId));

		}
	
	
	
	
	
	
	

}
