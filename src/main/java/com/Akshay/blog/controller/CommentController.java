package com.Akshay.blog.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Akshay.blog.payloads.ApiResponse;
import com.Akshay.blog.payloads.CommentDto;
import com.Akshay.blog.service.CommentServiceI;

@RestController
@RequestMapping("/api")
public class CommentController {
	
	
	private static Logger logger = LoggerFactory.getLogger(CommentController.class);
	
	@Autowired
	private CommentServiceI commentServiceI;
	
	/*
	 * @author Akshay
	 * @apiNote this is api  controller to  createComment
	 * @param comment
	 * @param postId
	 * @return CommentDto
	 */
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto>createComment(@RequestBody CommentDto comment,@PathVariable Integer postId) {
		logger.info("Start the api method createComment in CommentController : {}",comment,postId);
		CommentDto createComment = this.commentServiceI.createComment(comment, postId);
		
		logger.info("Complete the api method createComment in CommentController : {}",comment,postId);
		
		return new ResponseEntity<CommentDto>(createComment,HttpStatus.CREATED);
		
		
	}
	
	/*
	 * @author Akshay
	 * @apiNote this is api  controller to  deleteComment
	 * @param commentId
	 * @param 
	 * @return ApiResponse
	 */
	
	@DeleteMapping("/comment/{commentId}")
	public ResponseEntity<ApiResponse>deleteComment(@PathVariable Integer commentId) {
		logger.info("Start the api method deleteComment in CommentController : {}",commentId);
		this.commentServiceI.deleteComment(commentId);
		
		logger.info("Start the api method deleteComment in CommentController : {}",commentId);
	
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment Deleted Successfully",true),HttpStatus.OK);
		
	}

}
