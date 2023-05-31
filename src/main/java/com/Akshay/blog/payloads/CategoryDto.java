package com.Akshay.blog.payloads;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryDto {

	private int categoryId;

	@NotEmpty
	@Size(min = 4, max = 100, message = " size of category title is min 4 and max 100 char")
	private String categoryTitle;

	@NotEmpty
	@Size(min = 10, max = 100, message = " size of category desc is min 10 and max 100")
	private String categoryDescription;

}
