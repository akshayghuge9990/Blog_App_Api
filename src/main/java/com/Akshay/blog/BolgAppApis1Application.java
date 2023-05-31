package com.Akshay.blog;


import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.Akshay.blog.config.AppConstatnt;
import com.Akshay.blog.entity.Role;
import com.Akshay.blog.repository.RoleRepo;


@SpringBootApplication
public class BolgAppApis1Application implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	
	public static void main(String[] args) {
		SpringApplication.run(BolgAppApis1Application.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		
		return new ModelMapper();
		
	}

	@Override
	public void run(String... args) throws Exception {
		
		System.out.println(this.passwordEncoder.encode("abe123"));
		
		try {
			
			
			Role role = new Role();
			role.setId(AppConstatnt.ADMIN_USER);
			role.setName("ROLE_ADMIN");
			
			Role role1 = new Role();
			role1.setId(AppConstatnt.NORMAL_USER);
			role1.setName("ROLE_NORMAL");
			
			List<Role> list = List.of(role,role1);
			
			List<Role> saveAll = this.roleRepo.saveAll(list);
			
			saveAll.forEach(r ->
				System.out.println(r.getName()));
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
}
