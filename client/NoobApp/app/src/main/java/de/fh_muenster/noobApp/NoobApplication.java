package de.fh_muenster.noobApp;

import android.app.Application;

import java.util.List;

import de.fh_muenster.noob.CategoryListResponse;
import de.fh_muenster.noob.LocationTO;

/**
 * Created by marius on 02.06.15.
 * @author marius
 * Subklasse von Application --> Austausch von Daten unter den Activities
 */
public class NoobApplication extends Application {
    private int sessionId;
    private String userId;
    private String city;
    private String category;
    private LocationTO location;
    private String sortBy;
    private List<LocationTO> locationSearchResults;
    private String search;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public List<LocationTO> getLocationSearchResults() {
        return locationSearchResults;
    }

    public void setLocationSearchResults(List<LocationTO> locationSearchResults) {
        this.locationSearchResults = locationSearchResults;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return this.city;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return this.category;
    }

    public LocationTO getLocation() {
        return location;
    }

    public void setLocation(LocationTO location) {
        this.location = location;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortBy() {
        return sortBy;
    }
}
