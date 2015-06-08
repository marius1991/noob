package de.noob.dto;

import java.util.List;

public class CategoryListResponse extends ReturnCodeResponse {

	private static final long serialVersionUID = -5729437303533320120L;

	public List<String> categories;

	
	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}
}
