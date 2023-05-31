package com.Akshay.blog.service;

import java.util.List;

import com.Akshay.blog.entity.Post;
import com.Akshay.blog.payloads.PostDto;
import com.Akshay.blog.payloads.PostResponse;


public interface PostServiceI {

	// create

	PostDto createPost(PostDto postdto, Integer userId, Integer categoryId);

	// update

	PostDto updatePost(PostDto postDto, Integer postId);

	// delete

	void deletePost(Integer postId);

	// getAllPosts

	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	// get Single posts

	PostDto getPostById(Integer postId);

	// get all posts by category

	List<PostDto> getPostByCategory(Integer categoryId);

	// get all posts by user

	List<PostDto> getPostByUser(Integer userId);

	// search posts

	List<PostDto> searchPost(String keyword);

}
