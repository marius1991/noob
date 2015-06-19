package de.fh_muenster.noobApp;

import java.util.ArrayList;
import java.util.List;

import de.fh_muenster.noob.CommentTO;
import de.fh_muenster.noob.LocationTO;
import de.fh_muenster.noob.RatingTO;

/**
 * Created by marius on 19.06.15.
 */
public class TestDB {
    private static TestDB testDB = new TestDB();
    private static List<LocationTO> locations = new ArrayList<>();
    private static List<CommentTO> comments = new ArrayList<>();
    private static List<RatingTO> ratings = new ArrayList<>();
    private TestDB(){};



    public static TestDB getInstance() {
        if(locations.isEmpty()) {
            LocationTO locationTO = new LocationTO();
            locationTO.setName("Blaues Haus");
            locationTO.setCategory("Kneipe");
            locationTO.setAverageRating(3.0);
            locationTO.setCity("Münster");
            locationTO.setPlz(48143);
            locationTO.setStreet("Kreuzstraße");
            locationTO.setNumber("13");
            locationTO.setOwnerId("test@test.de");
            locationTO.setDescription("Die verwinkelte Kult-Kneipe mit Kunsttapeten, Live-Bühne und westfälischer Kost war einst ein Hippie-Treff.");
            locationTO.setId(1);
            CommentTO commentTO = new CommentTO();
            commentTO.setDate("2015-01-01 00:00");
            commentTO.setId(1);
            commentTO.setOwnerId("test@test.de");
            commentTO.setOwnerName("Tester");
            commentTO.setLocationId(1);
            commentTO.setText("Tolle Kneipe");
            comments.add(commentTO);
            locationTO.setComments(comments);
            RatingTO ratingTO = new RatingTO();
            ratingTO.setOwnerId("test@test.de");
            ratingTO.setId(1);
            ratingTO.setValue(3);
            ratingTO.setLocationId(1);
            ratings.add(ratingTO);
            locationTO.setRatings(ratings);
            locations.add(locationTO);
        }
        return testDB;
    }
    protected static List<LocationTO> getLocations() {
        return locations;
    }
    protected static List<CommentTO> getComments() {
        return comments;
    }
    protected static List<RatingTO> getRatings() {
        return ratings;
    }
    protected static void setLocations(List<LocationTO> locations1) {
        locations = locations1;
    }
    protected static void setComments(List<CommentTO> comments1) {
        comments = comments1;
    }
    protected static void setRatings(List<RatingTO> ratings1) {
        ratings = ratings1;
    }
}
