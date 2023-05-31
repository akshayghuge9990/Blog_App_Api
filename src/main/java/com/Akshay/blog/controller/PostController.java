package com.Akshay.blog.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Akshay.blog.config.AppConstatnt;
import com.Akshay.blog.payloads.ApiResponse;
import com.Akshay.blog.payloads.PostDto;
import com.Akshay.blog.payloads.PostResponse;
import com.Akshay.blog.service.FileService;
import com.Akshay.blog.service.PostServiceI;

@RestController
@RequestMapping("/api")
public class PostController {

	private static Logger logger = LoggerFactory.getLogger(PostController.class);

	@Autowired
	private PostServiceI postServiceI;
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;

	// create

	/*
	 * @author Akshay
	 * 
	 * @apiNote this api is used to createPost
	 * 
	 * @param postDto
	 * 
	 * @param userId
	 * 
	 * @return PostDto
	 */
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {

		logger.info("start the api method createPost in PostController : {}", postDto, userId);

		PostDto createPost = this.postServiceI.createPost(postDto, userId, categoryId);

		logger.info("Complete the api method createPost in PostController : {}", postDto, userId);
		return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);

	}

	/*
	 * @author Akshay
	 * 
	 * @apiNote this is api controller to getPostByUser
	 * 
	 * @param userId
	 * 
	 * @param
	 * 
	 * @return List<PostDto>
	 */

	// getByUser
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId) {

		logger.info("start the api method getPostByUser in PostController : {}", userId);
		List<PostDto> posts = this.postServiceI.getPostByUser(userId);

		logger.info("Complete the api method getPostByUser in PostController : {}", userId);

		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);

	}

	/*
	 * @author Akshay
	 * 
	 * @apiNote this is api controller to getPostCategory
	 * 
	 * @param categoryId
	 * 
	 * @param
	 * 
	 * @return List<PostDto>
	 */

	// getByCategory
	@GetMapping("/category/{categoryId}/post")
	public ResponseEntity<List<PostDto>> getPostCategory(@PathVariable Integer categoryId) {

		logger.info("start the api method getPostCategory in PostController : {}", categoryId);
		List<PostDto> posts = this.postServiceI.getPostByCategory(categoryId);

		logger.info("Complete the api method getPostCategory in PostController : {}", categoryId);

		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);

	}

	/*
	 * @author Akshay
	 * 
	 * @apiNote this is api controller to getAllPost
	 * 
	 * @param pageNumber
	 * 
	 * @param pageSize
	 * 
	 * @Param sortBy
	 * 
	 * @param sortDir
	 * 
	 * @return PostResponse
	 */

	// getAllPost
	@GetMapping("/post")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageNumber", defaultValue = AppConstatnt.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstatnt.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstatnt.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstatnt.SORT_DIR, required = false) String sortDir) {

		logger.info("start the api method getAllPost in PostController : {}", pageNumber, pageSize, sortBy, sortDir);
		PostResponse postResponse = this.postServiceI.getAllPost(pageNumber, pageSize, sortBy, sortDir);

		logger.info("Complete the api method getAllPost in PostController : {}", pageNumber, pageSize, sortBy, sortDir);

		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);

	}

	/*
	 * @author Akshay
	 * 
	 * @apiNote this is api controller to getPostById
	 * 
	 * @param postId
	 * 
	 * @param
	 * 
	 * @return PostDto
	 */
	// get post details by id

	@GetMapping("/post/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
		logger.info("start the api method getPostById in PostController : {}", postId);
		PostDto postDto = this.postServiceI.getPostById(postId);
		logger.info("Complete the api method getPostById in PostController : {}", postId);
		return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
	}

	/*
	 * @author Akshay
	 * 
	 * @apiNote this is api controller to deletePost
	 * 
	 * @param postId
	 * 
	 * @param
	 * 
	 * @return ApiResponse
	 */
	// delete Post
	@DeleteMapping("/post/{postId}")
	public ApiResponse deletePost(@PathVariable Integer postId) {
		logger.info("start the api method deletePost in PostController : {}", postId);
		this.postServiceI.deletePost(postId);

		logger.info("Complete the api method deletePost in PostController : {}", postId);

		return new ApiResponse(AppConstatnt.DELETE_POST, true);

	}

	/*
	 * @author Akshay
	 * 
	 * @apiNote this is api controller to updatePost
	 * 
	 * @param postDto
	 * 
	 * @param postId
	 * 
	 * @return PostDto
	 */
	// update Post
	@PutMapping("/post/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId) {
		logger.info("start the api method updatePost in PostController : {}", postDto, postId);
		PostDto updatePost = this.postServiceI.updatePost(postDto, postId);

		logger.info("Complete the api method updatePost in PostController : {}", postDto, postId);

		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);

	}

	/*
	 * @author Akshay
	 * 
	 * @apiNote this is api controller to searchPostByTitle
	 * 
	 * @param keyword
	 * 
	 * @param
	 * 
	 * @return PostDto
	 */
	// Search
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keyword") String keyword) {
		logger.info("start the api method searchPostByTitle in PostController : {}", keyword);
		List<PostDto> result = this.postServiceI.searchPost(keyword);

		logger.info("Complete the api method searchPostByTitle in PostController : {}", keyword);

		return new ResponseEntity<List<PostDto>>(result, HttpStatus.OK);

	}

	/*
	 * @author Akshay
	 * 
	 * @apiNote this is api controller to uploadPostImage
	 * 
	 * @param image
	 * 
	 * @param postId
	 * 
	 * @return PostDto
	 */

	// Post Image Upload

	@PostMapping("/post/image/upload/{PostId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId) throws IOException  {

		logger.info("start the api method uploadPostImage in PostController : {}", image, postId);
		PostDto postDto = this.postServiceI.getPostById(postId);

		String fileName = this.fileService.uploadImage(path, image);
		logger.info("Complete the api method uploadPostImage in PostController : {}", image, postId);

		postDto.setImageName(fileName);

		PostDto updatePost = this.postServiceI.updatePost(postDto, postId);

		logger.info("uploadPostImage...PostController");

		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);

	}
	
	/*
	 * @author Akshay
	 * @apiNote this is api  controller to  downloadImage
	 * @param imageName
	 * @param response
	 * @return 
	 */
	
	//method to server files
	
	@GetMapping(value="/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {
		logger.info("start the api method downloadImage in PostController : {}",imageName,response);
		InputStream resource = this.fileService.getResource(path, imageName);
		
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		logger.info("Complete the api method downloadImage in PostController : {}",imageName,response);
		StreamUtils.copy(resource, response.getOutputStream());
		
	}
	
	

}
