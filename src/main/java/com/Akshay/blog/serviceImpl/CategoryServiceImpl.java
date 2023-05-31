package com.Akshay.blog.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Akshay.blog.entity.Category;
import com.Akshay.blog.exception.ResourceNotFoundException;
import com.Akshay.blog.payloads.CategoryDto;
import com.Akshay.blog.repository.CategoryRepo;
import com.Akshay.blog.service.CategoryServiceI;

@Service
public class CategoryServiceImpl implements CategoryServiceI {

	private static Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;

	/*
	 * @author Akshay
	 * 
	 * @apiNote this method is implementation of createCategory
	 * 
	 * @return CategoryDto
	 */

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		logger.info("Statrt the createPost in CategoryServiceImpl :{}", categoryDto);

		Category cat = this.modelMapper.map(categoryDto, Category.class);

		Category addedCat = this.categoryRepo.save(cat);

		logger.info("Complate the createPost in CategoryServiceImpl :{}", categoryDto);

		return this.modelMapper.map(addedCat, CategoryDto.class);

	}

	/*
	 * @author Akshay
	 * 
	 * @apiNote this method is implementation of createCategory
	 * 
	 * @return CategoryDto
	 */

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		logger.info("Start the updateCategory in CategoryServiceImpl :{}", categoryDto, categoryId);

		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());

		Category updatecat = this.categoryRepo.save(cat);

		logger.info("Complate the updateCategory in CategoryServiceImpl :{}", categoryDto, categoryId);

		return this.modelMapper.map(updatecat, CategoryDto.class);
	}

	/*
	 * @author Akshay
	 * 
	 * @apiNote this method is implementation of createCategory
	 * 
	 * @return
	 */

	@Override
	public void deleteCategory(Integer categoryId) {
		logger.info("Start the deleteCategory in CategoryServiceImpl :{}", categoryId);
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));

		logger.info("complate the deleteCategory in CategoryServiceImpl :{}", categoryId);
		this.categoryRepo.delete(cat);

	}

	/*
	 * @author Akshay
	 * 
	 * @apiNote this method is implementation of createCategory
	 * 
	 * @return CategoryDto
	 */

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		logger.info("Start the getCategory in CategoryServiceImpl :{}", categoryId);

		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));

		logger.info("Complate the getCategory in CategoryServiceImpl :{}", categoryId);

		return this.modelMapper.map(cat, CategoryDto.class);

	}

	/*
	 * @author Akshay
	 * 
	 * @apiNote this method is implementation of createCategory
	 * 
	 * @return List<CategoryDto>
	 */

	@Override
	public List<CategoryDto> getAllCategories() {

		logger.info("Start the getAllCategories in CategoryServiceImpl");

		List<Category> categories = this.categoryRepo.findAll();

		List<CategoryDto> catDtos = categories.stream().map((cat) -> this.modelMapper.map(cat, CategoryDto.class))
				.collect(Collectors.toList());

		logger.info("Completet the getAllCategories in CategoryServiceImpl");

		return catDtos;

	}

}
