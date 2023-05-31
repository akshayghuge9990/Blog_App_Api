package com.Akshay.blog.serviceImpl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Akshay.blog.entity.Comment;
import com.Akshay.blog.entity.Post;
import com.Akshay.blog.exception.ResourceNotFoundException;
import com.Akshay.blog.payloads.CommentDto;
import com.Akshay.blog.repository.CommentRepo;
import com.Akshay.blog.repository.PostRepo;
import com.Akshay.blog.service.CommentServiceI;

@Service
public class CommentServiceImpl implements CommentServiceI {

	private static Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private CommentRepo commentRepo;

	@Autowired
	private ModelMapper modelMapper;

	/*
	 * @author Akshay
	 * 
	 * @apiNote this method is implementation of create Comment
	 * 
	 * @return CommentDto
	 */

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		logger.info("Statrt the creating Commient in CommentServiceImpl :{}", commentDto, postId);

		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));

		Comment comment = this.modelMapper.map(commentDto, Comment.class);

		comment.setPost(post);

		Comment savedComment = this.commentRepo.save(comment);

		logger.info("Complete the creating Commient in CommentServiceImpl:{}", commentDto, postId);

		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	/*
	 * @author Akshay
	 * 
	 * @apiNote this method is implementation of deleteComment
	 * 
	 * @return
	 */

	@Override
	public void deleteComment(Integer commentId) {
		logger.info("Statrt the deleteCommient in CommentServiceImpl : {}", commentId);

		Comment com = this.commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "Comment id", commentId));
		this.commentRepo.delete(com);

		logger.info("Complete the deleteCommient in CommentServiceImpl : {}", commentId);

	}

}
