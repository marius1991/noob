package de.fh_muenster.noobapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;


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
}
