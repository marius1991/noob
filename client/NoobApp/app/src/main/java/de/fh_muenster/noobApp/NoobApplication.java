package de.fh_muenster.noobApp;

import android.app.Application;

/**
 * Created by marius on 02.06.15.
 * @author marius
 * Subklasse von Application --> Austausch von Daten unter den Activities
 */
public class NoobApplication extends Application {
    private String city;
    private String category;
    private String location;

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
