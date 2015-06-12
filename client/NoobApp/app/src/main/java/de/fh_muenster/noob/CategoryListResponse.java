package de.fh_muenster.noob;

import java.util.List;

public class CategoryListResponse extends ReturnCodeResponse {

	private static final long serialVersionUID = -5729437303533320120L;

	private List<String> categories = null;

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}	
}
