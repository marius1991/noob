package de.fh_muenster.noobApp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import de.fh_muenster.noob.LocationListResponse;
import de.fh_muenster.noob.ReturnCodeResponse;


public class NewLocationActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_location2);
    }

    public void startKamera(View view){
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i,0);
    }

    //Empfängt das Bild und prüft ihm resultcode ob ein bild gemacht worden ist

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            //Intentdata ist das Photo
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ((ImageView) findViewById(R.id.imageView5)).setImageBitmap(photo);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_location, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_maps:
                openGoogleMaps();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openGoogleMaps() {
        String geoUriString = getResources().getString(R.string.map_location);
        Uri geoUri = Uri.parse(geoUriString);
        Intent mapCall = new Intent(Intent.ACTION_VIEW, geoUri);
        startActivity(mapCall);
    }

    public void newLocation(View view) {
        NoobApplication myApp = (NoobApplication) getApplication();
        EditText locName = (EditText)findViewById(R.id.editText9);
        EditText locCity = (EditText)findViewById(R.id.editText11);
        new NewLocationTask().execute(Integer.toString(myApp.getSessionId()), locName.getText().toString(), "Kneipe", "Tolle Kneipe", "Jüdefelder Str.", "15", Integer.toString(48150), locCity.getText().toString());
    }

    //Asynchron senden (Innere Klasse)
    class NewLocationTask extends AsyncTask<String, String, ReturnCodeResponse> {

        @Override
        protected ReturnCodeResponse doInBackground(String... params) {
            NoobOnlineServiceImpl onlineService = new NoobOnlineServiceImpl();
            int sessionId = Integer.parseInt(params[0]);
            int plz = Integer.parseInt(params[6]);
            ReturnCodeResponse response;
            response = onlineService.createLocation(sessionId, params[1], params[2], params[3], params[4], params[5], plz, params[7]);
            return response;
        }

        @Override
        protected void onPostExecute(ReturnCodeResponse response) {
            Toast.makeText(NewLocationActivity.this, response.getMessage(), Toast.LENGTH_LONG).show();

        }
    }
}
