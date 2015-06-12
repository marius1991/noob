package de.fh_muenster.noobApp;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import de.fh_muenster.noob.ReturnCodeResponse;
import de.fh_muenster.noob.UserTO;


public class UserManagementAcitivtiy extends ActionBarActivity {
    private EditText email;
    private EditText password;
    private EditText passwordwdh;
    private EditText benutzername;
    private Button userLoeschen;
    private Button setUserDetails;
    private String emailString = "";
    private String passwordString = "";
    private String passwordStringWdh = "";
    private String benutzernameString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management_acitivtiy);
        GetUserDetails getUser = new GetUserDetails(getApplicationContext());
        getUser.execute();

        email = (EditText) findViewById(R.id.editText12);
        password = (EditText) findViewById(R.id.editText14);
        passwordwdh = (EditText) findViewById(R.id.editText15);
        userLoeschen = (Button) findViewById(R.id.button11);
        benutzername = (EditText) findViewById(R.id.editText13);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_management_acitivtiy, menu);
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

    public class GetUserDetails extends AsyncTask<String, String, UserTO> {
        private Context context;
        private String message;
        private int returnCode;
        private int sessionId;
        UserTO userTO;

        public GetUserDetails(Context context) {
            this.context = context;
        }

        @Override
        protected UserTO doInBackground(String... params) {
            NoobOnlineServiceImpl onlineService = new NoobOnlineServiceImpl();
            UserTO userTO;
            try {
                NoobApplication myApp = (NoobApplication) getApplication();
                sessionId = myApp.getSessionId();
                userTO = onlineService.getUserDetails(sessionId);
                return userTO;
            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(UserTO userTO) {
            if (userTO.getReturnCode() == 0) {
                email.setText(userTO.getEmail(), TextView.BufferType.EDITABLE);
                password.setText(userTO.getPassword(), TextView.BufferType.EDITABLE);
                passwordwdh.setText(userTO.getPassword(), TextView.BufferType.EDITABLE);
                benutzername.setText(userTO.getName(), TextView.BufferType.EDITABLE);
            }
        }
    }

    public class DeleteAccount extends AsyncTask<String, String, ReturnCodeResponse> {
        private Context context;
        private int returnCode;
        private int sessionId;

        public DeleteAccount(Context context) {
            this.context = context;
        }

        @Override
        protected ReturnCodeResponse doInBackground(String... params) {
            ReturnCodeResponse userDelete = null;
            NoobOnlineServiceImpl onlineService = new NoobOnlineServiceImpl();
            try {
                userDelete = onlineService.deleteUser(sessionId);
                returnCode = userDelete.getReturnCode();
                return userDelete;
            } catch (Exception e) {

            }
            return null;
        }

        protected void onPostExecute(ReturnCodeResponse deleteUserResponse) {
            Toast.makeText(UserManagementAcitivtiy.this, deleteUserResponse.getMessage(), Toast.LENGTH_LONG).show();


        }
    }

    public class SetUserDetails extends AsyncTask<String, String, ReturnCodeResponse> {
        private Context context;
        private int returnCode;
        private int sessionId;

        public SetUserDetails(Context context) {
            this.context = context;
        }
        @Override
        protected ReturnCodeResponse doInBackground(String... params) {
            ReturnCodeResponse setUserDetails = null;
            NoobOnlineServiceImpl onlineService = new NoobOnlineServiceImpl();
            try {
                setUserDetails = onlineService.setUserDetails(UserTO);
                returnCode = setUserDetails.getReturnCode();
                return setUserDetails;
            } catch (Exception e) {

            }
            return null;
        }
    }
}
