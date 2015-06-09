package de.fh_muenster.noobApp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.fh_muenster.noob.CommentTO;
import de.fh_muenster.noob.ReturnCodeResponse;

/**
 * Created by marius on 02.06.15.
 * @author marius
 * Die Activity zeigt eine Location und deren Details
 */
public class LocationShowActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_show);

        //Titel der Activity ersetzen
        NoobApplication myApp = (NoobApplication) getApplication();
        setTitle(myApp.getLocation().getName() + " in " + myApp.getCity());

        TextView textViewAddress = (TextView)findViewById(R.id.textView14);
        textViewAddress.setText(myApp.getLocation().getStreet() + " " + myApp.getLocation().getNumber() + " " + myApp.getLocation().getPlz() + " " + myApp.getLocation().getCity());

        TextView textViewDescription = (TextView)findViewById(R.id.textView15);
        textViewDescription.setText(myApp.getLocation().getDescription() + "\n" + "Inhaber: " + myApp.getLocation().getOwner().getName());

        TextView textViewRating = (TextView)findViewById(R.id.textView12);
        textViewRating.append(" " + myApp.getLocation().getAverageRating() + "/5.0 Sterne");

        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                new sendRatingToServer().execute(Math.round(rating));
            }
        });
//        for(int i=0; i<myApp.getLocation().getRatings().size(); i++) {
//            if(myApp.getLocation().getRatings().get(i).getOwner().getId() == 1) {
//                ratingBar.setRating(myApp.getLocation().getRatings().get(i).getValue());
//            }
//        }

        List <CommentTO> comments = new ArrayList<>();
        List <String> valueList = new ArrayList<>();
        comments = myApp.getLocation().getComments();
        if (comments != null) {
            for (int i=0; i<comments.size(); i++) {
                valueList.add(comments.get(i).getText());
            }
            ArrayAdapter adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.comment_item, valueList);
            final ListView lv = (ListView)findViewById(R.id.listView3);
            lv.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_location_show, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void clickFunc2(View view){
        Intent i = new Intent(LocationShowActivity.this, LocationCommentActivity.class);
        startActivity(i);
    }

    public class sendRatingToServer extends AsyncTask<Integer, String, ReturnCodeResponse> {
        @Override
        protected ReturnCodeResponse doInBackground(Integer... params) {
            NoobApplication myApp = (NoobApplication) getApplication();
            myApp.setSessionId(1);
            NoobOnlineServiceMock onlineService = new NoobOnlineServiceMock();
            ReturnCodeResponse response = onlineService.giveRating(myApp.getSessionId(), myApp.getLocation().getId(), params[0]);
            return response;
        }

        @Override
        protected  void onPostExecute(ReturnCodeResponse response) {
            Toast.makeText(getApplicationContext(), "Rating erfolgreich" , Toast.LENGTH_LONG).show();
        }
    }

}
