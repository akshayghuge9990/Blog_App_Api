package com.Akshay.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Akshay.blog.entity.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
