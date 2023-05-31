package com.Akshay.blog.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.Akshay.blog.payloads.CategoryDto;
import com.Akshay.blog.service.CategoryServiceI;

@RestController
@RequestMapping("/api")
public class CategoryController {

	private static Logger logger = LoggerFactory.getLogger(CategoryController.class);

	@Autowired
	private CategoryServiceI categoryServiceI;

	/*
	 * @author Akshay
	 * 
	 * @apiNote this is api controller of createCategory
	 * 
	 * @param categoryDto
	 * 
	 * @param
	 * 
	 * @return CategoryDto
	 */
	// create
	@PostMapping("/categories/createCategory")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		logger.info("start the api method createCategory in CategoryController : {}", categoryDto);
		CategoryDto createCategory = this.categoryServiceI.createCategory(categoryDto);

		logger.info("Compelet the api method createCategory in CategoryController : {}", categoryDto);

		return new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);

	}

	/*
	 * @author Akshay
	 * 
	 * @apiNote this is api controller of updatecategory
	 * 
	 * @param categoryDto
	 * 
	 * @param catId
	 * 
	 * @return CategoryDto
	 */

	// update
	@PutMapping("/categories/{catId}")
	public ResponseEntity<CategoryDto> updatecategory(@Valid @RequestBody CategoryDto categoryDto,
			@PathVariable Integer catId) {
		logger.info("Start the api method updatecategory in CategoryController : {}", categoryDto, catId);
		CategoryDto updateCategory = this.categoryServiceI.updateCategory(categoryDto, catId);

		logger.info("Compelet the api method updatecategory in CategoryController : {}", categoryDto, catId);

		return new ResponseEntity<CategoryDto>(updateCategory, HttpStatus.OK);

	}

	/*
	 * @author Akshay
	 * 
	 * @apiNote this is api controller of deleteCategory
	 * 
	 * @param
	 * 
	 * @param catId
	 * 
	 * @return ApiResponse
	 */
	// delete

	@DeleteMapping("/categories/{catId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId) {
		logger.info("Start the api method deleteCategory in CategoryController : {}", catId);
		this.categoryServiceI.deleteCategory(catId);

		logger.info("Complete the api method deleteCategory in CategoryController : {}", catId);

		return new ResponseEntity<ApiResponse>(new ApiResponse(AppConstatnt.DELETE_CATEGORY, true),
				HttpStatus.OK);

	}

	/*
	 * @author Akshay
	 * 
	 * @apiNote this is api controller of getCategory
	 * 
	 * @param
	 * 
	 * @param catId
	 * 
	 * @return CategoryDto
	 */

	// get
	@GetMapping("/categories/{catId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer catId) {
		logger.info("Start the api method getCategory in CategoryController : {}", catId);
		CategoryDto categoryDto = this.categoryServiceI.getCategory(catId);

		logger.info("Complete the api method getCategory in CategoryController : {}", catId);

		return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);

	}
	
	/*
	 * @author Akshay
	 * @apiNote this is api  controller of getCategories
	 * @param 
	 * @param catId
	 * @return List<CategoryDto>
	 */
	
	// getAll
		@GetMapping("/categories/getCategories")
		public ResponseEntity<List<CategoryDto>> getCategories() {
			logger.info("Start the api method getCategories in CategoryController ");
			List<CategoryDto> allCategories = this.categoryServiceI.getAllCategories();

			logger.info("Complete the api method getCategories in CategoryController ");
			
			return ResponseEntity.ok(allCategories);
	
		}
	

}
