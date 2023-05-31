package com.Akshay.blog.service;

import com.Akshay.blog.payloads.CommentDto;

public interface CommentServiceI {

	CommentDto createComment(CommentDto commentDto, Integer postId);

	void deleteComment(Integer commentId);

}
