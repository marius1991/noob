package de.fh_muenster.noobApp;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import de.fh_muenster.noob.ReturnCodeResponse;

/**
 * @author marius
 * In dieser Activity kann ein Kommentar zu einer Location geschrieben werden
 */
public class LocationCommentActivity extends ActionBarActivity {
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_comment);

        //Titel der Activity ersetzen
        NoobApplication myApp = (NoobApplication) getApplication();
        setTitle(myApp.getLocation().getName() + " kommentieren");

        editText = (EditText)findViewById(R.id.editText16);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_location_comment, menu);
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

    public void clickFuncCommentOnLocation(View view) {
        new CommentOnLocation().execute(editText.getText().toString());
    }

    class CommentOnLocation extends AsyncTask<String, String, ReturnCodeResponse> {

        @Override
        protected ReturnCodeResponse doInBackground(String... params) {
            NoobOnlineServiceImpl onlineService = new NoobOnlineServiceImpl();
            NoobApplication myApp = (NoobApplication) getApplication();
            ReturnCodeResponse returnCodeResponse;
            returnCodeResponse = onlineService.commentOnLocation(myApp.getSessionId(), myApp.getLocation().getId(), params[0]);
            return returnCodeResponse;
        }

        @Override
        protected void onPostExecute (ReturnCodeResponse response) {
            Toast.makeText(LocationCommentActivity.this, response.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
