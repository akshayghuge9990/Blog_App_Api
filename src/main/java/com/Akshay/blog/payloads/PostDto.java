package com.Akshay.blog.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.Akshay.blog.entity.Comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
	
	
	private int postId;
	@NotBlank
	@Size(min = 8, max = 15, message = "Title must be mini of 8 character and maxi 15 character allowed")
	private String title;

	@NotNull
	@Size(min = 10, max = 10, message = "Content must be mini of 10 character and maxi 10 character allowed")
	private String content;
	@NotNull
	@Size(min = 10, max = 30, message = "Image size 10 MB and maxi 10 MB allowed")
	private String imageName;

	private Date addedDate;

	private CategoryDto category;

	private UserDto user;
	
	private Set<Comment> comment = new HashSet<>();

}
