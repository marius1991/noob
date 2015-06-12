package de.fh_muenster.noobApp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.IOException;


public class NewLocationActivity extends ActionBarActivity {
    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_location2);
    }

    public void startKamera(View view){
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i, 0);
    }

    //Empfängt das Bild und prüft ihm resultcode ob ein bild gemacht worden ist

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                //bitmap Factory muss 0 sein. Wenn man erst ein Bild von Kamera und dann ein Bild
                //von der Gallery in die view lädt muss die bitmapfactory 0 sein!!
                if(bitmap!=null)
                {
                    bitmap.recycle();
                    bitmap=null;
                }
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ((ImageView) findViewById(R.id.imageView4)).setImageBitmap(bitmap);

            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void pickPhoto(View view) {
        Intent intent = new Intent();
// Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
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
