package com.Akshay.blog.payloads;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {

	private int id;
	@NotNull
	@Size(min = 4, message = "Min size of comment content  is 4")
	private String content;

	
	
}
