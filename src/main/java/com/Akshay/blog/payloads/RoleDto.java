package com.Akshay.blog.payloads;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RoleDto {
	
	private int id;
	@NotNull
	private String name;

}
