package com.Akshay.blog.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.Akshay.blog.entity.Category;
import com.Akshay.blog.entity.Post;
import com.Akshay.blog.entity.User;
import com.Akshay.blog.exception.ResourceNotFoundException;
import com.Akshay.blog.payloads.PostDto;
import com.Akshay.blog.payloads.PostResponse;
import com.Akshay.blog.repository.CategoryRepo;
import com.Akshay.blog.repository.PostRepo;
import com.Akshay.blog.repository.UserRepo;
import com.Akshay.blog.service.PostServiceI;

@Service
public class PostServiceImpl implements PostServiceI {

	private static Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	/*
	 * @author Akshay
	 * 
	 * @apiNote this method is implementation of createPost
	 * 
	 * @return PostDto
	 */

	@Override
	public PostDto createPost(PostDto postdto, Integer userId, Integer categoryId) {
		logger.info("Statrt the createPost in PostServiceImpl :{}", userId, categoryId);

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User id", userId));

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));

		Post post = this.modelMapper.map(postdto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);

		Post newPost = this.postRepo.save(post);

		logger.info("complete the createPost in PostServiceImpl :{}", userId, categoryId);

		return this.modelMapper.map(newPost, PostDto.class);
	}

	/*
	 * @author Akshay
	 * 
	 * @apiNote this method is implementation of updatePost
	 * 
	 * @return PostDto
	 */
	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {

		logger.info("Statrt the updatePost in PostServiceImpl : {}", postDto, postId);

		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());

		Post update = this.postRepo.save(post);

		logger.info("Complete the updatePost in PostServiceImpl : {}", postDto, postId);

		return this.modelMapper.map(update, PostDto.class);
	}

	/*
	 * @author Akshay
	 * 
	 * @apiNote this method is implementation of deletePost
	 * 
	 * @return
	 */

	@Override
	public void deletePost(Integer postId) {
		logger.info("Statrt the deletePost in PostServiceImpl : {}", postId);

		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		logger.info("complete the deletePost in PostServiceImpl : {}", postId);

		this.postRepo.delete(post);

	}

	/*
	 * @author Akshay
	 * 
	 * @apiNote this method is implementation of getAllPost
	 * 
	 * @return postResponse
	 */

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

logger.info("Statrt the getAllPost in PostServiceImpl : {}",pageNumber,pageSize,sortBy,sortDir);
		
		Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		logger.info("we are create pagegination...PostServiceImpl");
		
		Pageable p = PageRequest.of(pageNumber, pageSize, sort);

		Page<Post> pagePost = this.postRepo.findAll(p);

		List<Post> allPosts = pagePost.getContent();

		List<PostDto> postDtos = allPosts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();

		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		logger.info("Complete the getAllPost in PostServiceImpl : {}",pageNumber,pageSize,sortBy,sortDir);
		return postResponse;
	}

	/*
	 * @author Akshay
	 * 
	 * @apiNote this method is implementation of getPostById
	 * 
	 * @return PostDto
	 */

	@Override
	public PostDto getPostById(Integer postId) {

		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));

		return this.modelMapper.map(post, PostDto.class);
	}

	/*
	 * @author Akshay
	 * 
	 * @apiNote this method is implementation of getPostByCategory
	 * 
	 * @return List<PostDto
	 */

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		logger.info("start the method getPostByCategory in PostServiceImpl :{}", categoryId);

		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
		List<Post> posts = this.postRepo.findBycategory(cat);

		List<PostDto> postDto = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		logger.info("Complete the method getPostByCategory in PostServiceImpl : {}", categoryId);

		return postDto;
	}

	/*
	 * @author Akshay
	 * 
	 * @apiNote this method is implementation of getPostByUser
	 * 
	 * @return List<PostDto
	 */

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		logger.info("start the method getPostByUser in PostServiceImpl: {}", userId);
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));

		List<Post> posts = this.postRepo.findByUser(user);

		logger.info("FindByUser...PostServiceImpl");

		List<PostDto> postDto = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		logger.info("Complete the method getPostByUser in PostServiceImpl : {}", userId);

		return postDto;

	}

	// Search
	
		/*
		 * @author Akshay
		 * 
		 * @apiNote this method is implementation of searchPost
		 * 
		 * @return List<PostDto
		 */
	
	@Override
	public List<PostDto> searchPost(String keyword) {
		

		logger.info("start the method searchPost in PostServiceImpl : {}",keyword);
		
		List<Post> posts = this.postRepo.searchByTitle("%"+keyword+"%");

		 List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		
		logger.info("Complete the method searchPost in PostServiceImpl : {}",keyword);

		return postDtos;
	}

}
