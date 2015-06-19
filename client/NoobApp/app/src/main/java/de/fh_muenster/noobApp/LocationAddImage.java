package de.fh_muenster.noobApp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import de.fh_muenster.noob.NoobOnlineService;
import de.fh_muenster.noob.ReturnCodeResponse;

/**
 * Mit diese Klasse können Fotos vom Handy geladen und zum Server gesendet werden.
 * @author marius
 */
public class LocationAddImage extends ActionBarActivity {
    private static final String TAG = LocationAddImage.class.getName();
    private byte[] byteArray;
    private ImageView imageView;
    private Bitmap bitmap;

    /**
     * Diese Methode wird aufgerufen wenn die Activity aufgerufen wird.
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
        if(bitmap == null) {
            Button rotate = (Button) findViewById(R.id.button21);
            rotate.setVisibility(View.INVISIBLE);
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
     * Diese Methode wird aufgerufen, wenn auf auf den Button "Auswählen" geklickt wird.
     * @param view
     */
    public void clickFuncImageFromFile (View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    public void clickFuncRotate (View view) {
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap , 0, 0, bitmap .getWidth(), bitmap .getHeight(), matrix, true);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        rotatedBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byteArray = stream.toByteArray();
        imageView.setImageBitmap(rotatedBitmap);
    }

    /**
     * Diese Methode wird aufgerufen, wenn auf den Button "Hochladen" geklcikt wird.
     * @param view
     */
    public void clickFuncUpload (View view) {
        NoobApplication myApp = (NoobApplication) getApplication();
        if(myApp.getLocation().getImages().size()<11) {
            new ImageUpload().execute(byteArray);
        }
        else {
            Toast.makeText(getApplicationContext(), "Maximale Anzahl an Bildern erreicht!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Diese Methode nimmt die Images aus dem Speicher entgegen, überprüft ihre Größe und speichert sie
     * als Bitmap.
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
                if (imageHeight > 500) {
                    final int halfHeight = imageHeight / 2;
                    while ((halfHeight / inSampleSize) > 500) {
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
        private ProgressDialog Dialog = new ProgressDialog(LocationAddImage.this);

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
         * @return
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
         * @param response
         */
        @Override
        protected void onPostExecute (ReturnCodeResponse response) {
            Dialog.dismiss();
            if (response.getReturnCode() == 10) {
                Toast.makeText(getApplicationContext(), "Keine Verbidung zum Server", Toast.LENGTH_SHORT).show();
            }
            if (response.getReturnCode() == 11) {
                Toast.makeText(getApplicationContext(), "Kein Bild ausgewählt", Toast.LENGTH_SHORT).show();
            }
            else {
                Log.d(TAG, "BILDUPLOAD: " + response.getMessage());
                Toast.makeText(getApplicationContext(), "Bildupload erfolgreich", Toast.LENGTH_SHORT).show();
                LocationAddImage.this.finish();
            }
        }
    }

}
