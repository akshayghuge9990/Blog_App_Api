package com.Akshay.blog.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Akshay.blog.config.AppConstatnt;
import com.Akshay.blog.entity.Role;
import com.Akshay.blog.entity.User;
import com.Akshay.blog.exception.ResourceNotFoundException;
import com.Akshay.blog.payloads.UserDto;
import com.Akshay.blog.repository.RoleRepo;
import com.Akshay.blog.repository.UserRepo;
import com.Akshay.blog.service.UserServiceI;


@Service
public class UserServiceImpl implements UserServiceI {

	private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;

	/*
	 * @author Akshay
	 * 
	 * @apiNote this method is implementation of create user
	 * 
	 * @return UserDto
	 */

	@Override
	public UserDto createUser(UserDto userDto) {

		logger.info("Statrt the creating User in UserServiceImpl: {}", userDto);
		User user = this.dtoToUser(userDto);
		User savedUser = this.userRepo.save(user);
		logger.info("Complete the converte  UserToDto in UserServiceImpl:{}", userDto);
		return this.userToDto(savedUser);
	}

	/*
	 * @author Akshay
	 * 
	 * @apiNote this method is implementation of updateUser
	 * 
	 * @return UserDto
	 */

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		logger.info("Statrt the updateUser and find by Id in UserServiceImpl:{}", userDto, userId);
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		logger.info(" Set User in UserServiceImpl:{}", userDto, userId);
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		User updatedUser = this.userRepo.save(user);

		logger.info(" converte UserToDto in UserServiceImpl:{}", userDto, userId);

		UserDto userDto1 = this.userToDto(updatedUser);

		logger.info("Complete the updateUser in UserServiceImpl :{}", userDto, userId);

		return userDto1;
	}

	/*
	 * @author Akshay
	 * 
	 * @apiNote this method is implementation of getUserById
	 * 
	 * @return UserDto
	 */

	@Override
	public UserDto getUserById(Integer userId) {
		logger.info("Start the getUserById in UserServiceImpl :{}", userId);

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		logger.info("Complete the getUserById and converted to UserToDto in UserServiceImpl :{}", userId);

		return this.userToDto(user);
	}

	/*
	 * @author Akshay
	 * 
	 * @apiNote this method is implementation of getAllUser
	 * 
	 * @return List<UserDto>
	 */
	
	@Override
	public List<UserDto> getAllUser() {
		logger.info("Start the getAllUser in UserServiceImpl");

		List<User> users = this.userRepo.findAll();

		List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());

		logger.info("Complete the getAllUser in UserServiceImpl");

		return userDtos;
	}

	/*
	 * @author Akshay
	 * 
	 * @apiNote this method is implementation of deleteUser
	 * 
	 * @return 
	 */
	@Override
	public void deleteUser(Integer userId) {
		logger.info("Start the deleteUser in UserServiceImpl :{}",userId);
		User user2 = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		logger.info("Complete the deleteUser in UserServiceImpl :{}",userId);

		this.userRepo.delete(user2);


	}

	public User dtoToUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setAbout(userDto.getAbout());
//		user.setPassword(userDto.getPassword());
		return user;
	}

	public UserDto userToDto(User user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setAbout(user.getAbout());
//		userDto.setPassword(user.getPassword());
		return userDto;

	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		
		
		User user = this.modelMapper.map(userDto, User.class);
		
		//encoded the password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		//roles
		
		Role role = this.roleRepo.findById(AppConstatnt.NORMAL_USER).get();
		
		user.getRoles().add(role);
		
		User newUser = this.userRepo.save(user);
		
		return this.modelMapper.map(newUser, userDto.getClass());
	}

}