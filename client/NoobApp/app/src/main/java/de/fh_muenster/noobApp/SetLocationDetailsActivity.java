package de.fh_muenster.noobApp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import de.fh_muenster.noob.CategoryListResponse;
import de.fh_muenster.noob.LocationTO;
import de.fh_muenster.noob.ReturnCodeResponse;


public class SetLocationDetailsActivity extends ActionBarActivity {
    private final int PICK_IMAGE_REQUEST = 1;
    private final int CAMERA_REQUEST = 2;
    private EditText locationame;
    private EditText beschreibung;
    private EditText strasse;
    private EditText nummer;
    private EditText plz;
    private EditText ort;
    private Button editlocation;
    private Button locationLoeschen;
    private LocationTO locationTO;
    private NoobApplication myApp;
    private Bitmap bitmap;
    private byte[] byteArray;
    List<String> categoryList;
    private String selectedSpinnerElement;
    private static final String TAG = SetLocationDetailsActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_location_details);
        //Akutelle Location holen von der Applikation Klasse
        myApp = (NoobApplication) getApplication();
        locationTO=myApp.getLocation();
        //Anfangswert des Bytearrays ist gleich der Location die bearbeitet wird
        byteArray=locationTO.getImage();
        //ButtonsFestlegen
        categoryList=myApp.getCategories();
        editlocation=(Button) findViewById(R.id.button15);
        locationame = (EditText) findViewById(R.id.editText9);
        beschreibung= (EditText) findViewById(R.id.editText10);
        strasse= (EditText) findViewById(R.id.editText18);
        nummer= (EditText) findViewById(R.id.editText21);
        plz= (EditText) findViewById(R.id.editText11);
        ort= (EditText) findViewById(R.id.editText);
        //Hier eventuell noch überprüfen ob locationTO nicht leer ist
        locationame.setHint(locationTO.getName());
        beschreibung.setHint(locationTO.getDescription());
        strasse.setHint(locationTO.getStreet());
        nummer.setHint(locationTO.getNumber());
        plz.setHint(String.valueOf(locationTO.getPlz()));
        ort.setHint(locationTO.getCity());
        bitmap=BitmapFactory.decodeByteArray(locationTO.getImage(), 0, locationTO.getImage().length);
        ((ImageView) findViewById(R.id.imageView4)).setImageBitmap(bitmap);
        //Befüllt Spinner mit dem Array from Kategories vom Server(categoryList)
        if(!categoryList.isEmpty()) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, categoryList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Spinner spinner = (Spinner) findViewById(R.id.spinner2);
            spinner.setAdapter(adapter);
            //krallt sich die SpinnerPosition von der ausgewählten Kategory
            int spinnerPosition = adapter.getPosition(locationTO.getCategory());
            //Setzt den Spinner auf die Kategorie
            spinner.setSelection(spinnerPosition);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View selectedItemView, int position, long id) {
                    selectedSpinnerElement = parent.getItemAtPosition(position).toString();
                    Log.d(TAG, selectedSpinnerElement);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {

                }
            });
        }

        //ClickListener für setLocationDetails
        editlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!locationame.getText().toString().equals("")) {
                    locationTO.setName(locationame.getText().toString());
                }
                if(!beschreibung.getText().toString().equals("")){
                    locationTO.setDescription(beschreibung.getText().toString());
                }
                if(!strasse.getText().toString().equals("")) {
                    locationTO.setStreet(strasse.getText().toString());
                }
                if(!nummer.getText().toString().equals("")) {
                    locationTO.setNumber(nummer.getText().toString());
                }
                if(!plz.getText().toString().equals("")) {
                    locationTO.setPlz(Integer.parseInt(plz.getText().toString()));
                }
                if(!ort.getText().toString().equals("")) {
                    locationTO.setCity(ort.getText().toString());
                }
                if(byteArray!=locationTO.getImage()){
                    myApp.setByteArray(byteArray);
                }
                //Marius muss myApp Byte Array befüllen
                if(locationame.getText().toString().isEmpty()&&beschreibung.getText().toString().isEmpty()&&strasse.getText().toString().isEmpty()&&nummer.getText().toString().isEmpty()&&byteArray==locationTO.getImage()) {
                    Toast.makeText(view.getContext(), "Es wurde keine Werte geändert", Toast.LENGTH_SHORT).show();
                }

                if(!locationame.getText().toString().isEmpty()||!beschreibung.getText().toString().isEmpty()||!strasse.getText().toString().isEmpty()||!nummer.getText().toString().isEmpty()||!plz.getText().toString().isEmpty()||!ort.getText().toString().isEmpty()||byteArray!=locationTO.getImage()){
                    myApp.setLocation(locationTO);
                    SetLocationDetails setLocation = new SetLocationDetails(view.getContext());
                    setLocation.execute();
                }

        }


        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST) {
            try {
                //bitmap Factory muss 0 sein. Wenn man erst ein Bild von Kamera und dann ein Bild
                //von der Gallery in die view lädt muss die bitmapfactory 0 sein!!
                if (bitmap != null) {
                    bitmap.recycle();
                    bitmap = null;
                }
                bitmap = (Bitmap) data.getExtras().get("data");
                ((ImageView) findViewById(R.id.imageView4)).setImageBitmap(bitmap);
                bitmapToByte();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_REQUEST) {
            Uri uri = data.getData();
            try {
                //bitmap Factory muss 0 sein. Wenn man erst ein Bild von Kamera und dann ein Bild
                //von der Gallery in die view lädt muss die bitmapfactory 0 sein!!
                if (bitmap != null) {
                    bitmap.recycle();
                    bitmap = null;
                }
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ((ImageView) findViewById(R.id.imageView4)).setImageBitmap(bitmap);
                bitmapToByte();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void bitmapToByte() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap=Bitmap.createScaledBitmap(bitmap,200,200,true);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byteArray = stream.toByteArray();
    }
    public void startKamera(View view) {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i, CAMERA_REQUEST);
    }
    public void pickPhoto(View view) {
        Intent intent = new Intent();
// Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    public void deletePicture(View view){
        if(((ImageView) findViewById(R.id.imageView4)).getDrawable()!=null)
            ((ImageView) findViewById(R.id.imageView4)).setImageBitmap(null);
        if (bitmap != null) {
            bitmap.recycle();
            bitmap = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_set_location_details, menu);
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

    public class SetLocationDetails extends AsyncTask<String, String, ReturnCodeResponse> {
        private int returnCode;
        private int sessionId;
        private String message;
        private Context context;
        public SetLocationDetails(Context context){
            this.context=context;
        }

        @Override
        protected ReturnCodeResponse doInBackground(String... params) {
            NoobApplication myApp = (NoobApplication) getApplication();

            sessionId = myApp.getSessionId();
            ReturnCodeResponse setLocation = null;
            NoobOnlineServiceImpl onlineService = new NoobOnlineServiceImpl();
            locationTO = myApp.getLocation();
            try {
                setLocation = onlineService.setLocationDetails(sessionId,locationTO.getId(),locationTO.getName(),locationTO.getCategory(),locationTO.getDescription(),locationTO.getStreet(),locationTO.getNumber(),locationTO.getPlz(),locationTO.getCity(),locationTO.getImage());
                returnCode = setLocation.getReturnCode();
                message = setLocation.getMessage();
                return setLocation;
            } catch (Exception e) {
                 return null;
            }
        }

        protected void onPostExecute(ReturnCodeResponse returnCodeResponse){
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();


        }

    }

}
