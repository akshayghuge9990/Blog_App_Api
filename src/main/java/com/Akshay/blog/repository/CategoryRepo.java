package com.Akshay.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Akshay.blog.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
