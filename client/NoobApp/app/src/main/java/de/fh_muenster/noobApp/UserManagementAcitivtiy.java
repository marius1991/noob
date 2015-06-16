package de.fh_muenster.noobApp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
    private String emailString="";
    private String passwordString="";
    private String passwordStringWdh="";
    private String benutzernameString="";
    private Button userLoeschen;
    private Button setUserDetails;
    private UserTO userTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management_acitivtiy);
        email = (EditText) findViewById(R.id.editText12);
        password = (EditText) findViewById(R.id.editText14);
        passwordwdh = (EditText) findViewById(R.id.editText15);
        userLoeschen = (Button) findViewById(R.id.button11);
        benutzername = (EditText) findViewById(R.id.editText13);
        setUserDetails=(Button) findViewById(R.id.button10);

        setUserDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailString = email.getText().toString();
                passwordString = password.getText().toString();
                passwordStringWdh = passwordwdh.getText().toString();
                benutzernameString =benutzername.getText().toString();
                SetUserDetails setUser = new SetUserDetails(view.getContext());
                setUser.execute(benutzernameString,emailString,passwordString);
                NoobApplication myapp= (NoobApplication) getApplication();
                userTO= myapp.getUser();
                email.setText(userTO.getEmail());
                password.setText(userTO.getPassword());
                passwordwdh.setText(userTO.getPassword());
                benutzername.setText(userTO.getName());
            }

        });
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
        private String message;
        private ProgressDialog Dialog = new ProgressDialog(UserManagementAcitivtiy.this);


        public SetUserDetails(Context context) {
            this.context = context;
        }
        @Override
        protected void onPreExecute()
        {
            Dialog.setMessage("Holt Daten vom Server");
            Dialog.show();
        }

        @Override
        protected ReturnCodeResponse doInBackground(String... params) {
            String name=params[0];
            String email=params[1];
            String passwort=params[2];
            NoobApplication myApp = (NoobApplication) getApplication();

            sessionId=myApp.getSessionId();
            ReturnCodeResponse setUserDetails = null;
            NoobOnlineServiceImpl onlineService = new NoobOnlineServiceImpl();
            try {
                setUserDetails = onlineService.setUserDetails(sessionId,name,email,passwort);
                returnCode = setUserDetails.getReturnCode();
                message=setUserDetails.getMessage();
                return setUserDetails;
            } catch (Exception e) {

            }
            return null;
        }
        protected void onPostExecute(ReturnCodeResponse returncode){
            Dialog.dismiss();
                if (returnCode == 0) {
                    Toast.makeText(context, returncode.getMessage(), Toast.LENGTH_SHORT).show();
                }
            else {
                Toast.makeText(context, "Keine Verbidung zum Server", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
