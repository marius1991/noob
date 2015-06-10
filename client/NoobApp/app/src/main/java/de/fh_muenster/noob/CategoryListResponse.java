package de.fh_muenster.noob;

import java.util.List;

public class CategoryListResponse extends ReturncodeResponse {

    private static final long serialVersionUID = -4886044511644962076L;

    private List<String> categories;

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

}