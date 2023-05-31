package com.Akshay.blog.service;

import java.util.List;

import com.Akshay.blog.payloads.CategoryDto;

public interface CategoryServiceI {
	
	
	// create

		 CategoryDto createCategory(CategoryDto categoryDto);

		// update

		 CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);

		// delete

		 void deleteCategory(Integer categoryId);

		// get

		 CategoryDto getCategory(Integer categoryId);

		// getAll

		List<CategoryDto> getAllCategories();

	
	
	

}
