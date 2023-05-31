package com.Akshay.blog.service;

import java.util.List;

import com.Akshay.blog.payloads.UserDto;

public interface UserServiceI {
	
	UserDto registerNewUser(UserDto user);

	UserDto createUser(UserDto user);

	UserDto updateUser(UserDto user, Integer userId);

	UserDto getUserById(Integer userId);

	List<UserDto> getAllUser();

	void deleteUser(Integer userId);

}
