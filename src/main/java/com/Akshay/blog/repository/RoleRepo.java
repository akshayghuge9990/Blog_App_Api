package com.Akshay.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Akshay.blog.entity.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {
	

}
