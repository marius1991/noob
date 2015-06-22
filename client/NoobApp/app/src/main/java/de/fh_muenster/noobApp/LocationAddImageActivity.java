package de.fh_muenster.noobApp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import de.fh_muenster.noob.NoobOnlineService;
import de.fh_muenster.noob.ReturnCodeResponse;

/**
 * Mit dieser Klasse können Fotos vom Handy geladen und zum Server gesendet werden.
 * @author marius
 */
public class LocationAddImageActivity extends ActionBarActivity {
    private static final String TAG = LocationAddImageActivity.class.getName();
    private byte[] byteArray;
    private ImageView imageView;
    private Bitmap bitmap;
    private Button rotate;

    /**
     * Diese Methode wird aufgerufen wenn die Activity gestartet wird. Der Kamerabutton wird mit einem
     * OnClickListener belegt
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_add_image);
        imageView = (ImageView) findViewById(R.id.imageView5);
        Button photoButton = (Button) findViewById(R.id.button19);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 1888);
            }
        });
    }

    /**
     * Diese Methode wird aufgerufen, wenn auf die Activity gewechselt wird. Auch wenn sie vorher
     * nur pausiert wurde. Der Button zum Drehen wird angezeigt.
     */
    @Override
    public void onResume() {
        super.onResume();
        rotate = (Button) findViewById(R.id.button21);
        if(bitmap != null) {
            rotate.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_location_add_image, menu);
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

    /**
     * Diese Methode wird aufgerufen, wenn auf auf den Button "Auswählen" geklickt wird. Die
     * Galerie des Smartphones wird geöffnet.
     * @param view
     */
    public void clickFuncImageFromFile (View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    /**
     * Mit dieser Methode kann das Bild um 90° gedreht werden.
     * @param view
     */
    public void clickFuncRotate (View view) {
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap , 0, 0, bitmap .getWidth(), bitmap .getHeight(), matrix, true);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        rotatedBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byteArray = stream.toByteArray();
        imageView.setImageBitmap(rotatedBitmap);
        rotate.setVisibility(View.INVISIBLE);
    }

    /**
     * Diese Methode wird aufgerufen, wenn auf den Button "Hochladen" geklickt wird. Der AsyncTask wird gestartet
     * @param view
     */
    public void clickFuncUpload (View view) {
        NoobApplication myApp = (NoobApplication) getApplication();
        if(myApp.getLocation().getImages().size()<11) {
            new ImageUpload().execute(byteArray);
        }
        else {
            Toast.makeText(getApplicationContext(), R.string.activity_location_add_image_maxanzahl, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Diese Methode wird aufgerufen, wenn über das Menü der Eintrag 'Logout' gewählt wird.
     * Es erscheint eine Dialog, auf dem die Eingabe bestätigt werden muss.
     * Dann wird ein LogoutTask gestartet.
     * @param item
     */
    public void clickFuncLogout(MenuItem item) {
        Log.d(TAG, "Menüeintrag 'Logout' ausgewählt");
        new AlertDialog.Builder(this)
                .setMessage(R.string.menu_ausloggen_frage)
                .setCancelable(false)
                .setPositiveButton(R.string.menu_ausloggen_ja, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        NoobApplication myApp = (NoobApplication) getApplication();
                        new LogoutTask(getApplicationContext(), myApp).execute(myApp.getSessionId());
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.menu_ausloggen_nein, null)
                .show();
    }

    /**
     * Diese Methode nimmt die Images aus dem Speicher entgegen, überprüft ihre Größe, verkleinert sie, wenn nötig
     * und speichert sie als Bitmap.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
        {
            Uri imageUri = data.getData();
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                InputStream is = getContentResolver().openInputStream(imageUri);
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(is, null, options);
                int imageHeight = options.outHeight;
                int imageWidth = options.outWidth;
                String imageType = options.outMimeType;
                Log.d(TAG, "Imagesize: " + imageHeight + " x " + imageWidth);
                Log.d(TAG, "ImageType: " + imageType);
                int inSampleSize = 1;
                if (imageHeight > 400) {
                    final int halfHeight = imageHeight / 2;
                    while ((halfHeight / inSampleSize) > 400) {
                        inSampleSize *= 2;
                    }
                }
                options.inSampleSize = inSampleSize;
                options.inJustDecodeBounds = false;
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                bitmap = BitmapFactory.decodeStream(inputStream, null, options);
                imageView.setImageBitmap(bitmap);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byteArray = stream.toByteArray();
                rotate.setVisibility(View.INVISIBLE);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * AsyncTask für den Upload eines Bildes für eine Location
     * @author marius
     */
    public class ImageUpload extends AsyncTask<byte[], String, ReturnCodeResponse> {
        private ProgressDialog Dialog = new ProgressDialog(LocationAddImageActivity.this);

        /**
         * Während des Bilderuploads wird ein Dialog angezeigt.
         */
        @Override
        protected void onPreExecute()
        {
            Dialog.setMessage("Bilderupload...");
            Dialog.show();
        }

        /**
         * Startet den Thread für den Bilderupload.
         * @param params
         * @return ReturnCodeRespone (Enthält Fehler- bzw. Erfolgmeldungen)
         */
        @Override
        protected ReturnCodeResponse doInBackground(byte[]... params) {
            NoobApplication myApp = (NoobApplication) getApplication();
            NoobOnlineService onlineService;
            ReturnCodeResponse returnCodeResponse = new ReturnCodeResponse();
            if(myApp.isTestmode()) {
                onlineService = new NoobOnlineServiceMock();
            }
            else {
                onlineService  = new NoobOnlineServiceImpl();
            }
            if(params[0] != null) {
                returnCodeResponse = onlineService.addImageToLocation(myApp.getSessionId(), myApp.getLocation().getId(), params[0]);
            }
            else {
                returnCodeResponse.setReturnCode(11);
            }
            return returnCodeResponse;
        }

        /**
         * Nimmt den Returncode entgegen.
         * @param response ReturnCodeRespone (Enthält Fehler- bzw. Erfolgmeldungen)
         */
        @Override
        protected void onPostExecute (ReturnCodeResponse response) {
            Dialog.dismiss();
            if (response.getReturnCode() == 10) {
                Toast.makeText(getApplicationContext(), R.string.keine_verbindung, Toast.LENGTH_SHORT).show();
            }
            if (response.getReturnCode() == 11) {
                Toast.makeText(getApplicationContext(), R.string.activity_location_add_image_keinbild, Toast.LENGTH_SHORT).show();
            }
            else {
                Log.d(TAG, "BILDUPLOAD: " + response.getMessage());
                Toast.makeText(getApplicationContext(), response.getMessage(), Toast.LENGTH_SHORT).show();
                LocationAddImageActivity.this.finish();
            }
        }
    }

}
