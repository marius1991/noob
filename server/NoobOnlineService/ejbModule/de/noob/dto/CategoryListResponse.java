package de.noob.dto;

import java.util.List;

/**
 * Diese Klasse erweitert ReturnCodeResponse so, dass auch eine Liste mit Category-Strings zurück gegeben werden kann.
 * @author Philipp Ringele
 *
 */
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
