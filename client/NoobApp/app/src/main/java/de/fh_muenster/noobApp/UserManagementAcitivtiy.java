package de.fh_muenster.noobApp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
    private String passwordStringHashWdh;
    private UserTO userTO;
    private String passwordStringHash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management_acitivtiy);
        password = (EditText) findViewById(R.id.editText14);
        passwordwdh = (EditText) findViewById(R.id.editText15);
        userLoeschen = (Button) findViewById(R.id.button11);
        benutzername = (EditText) findViewById(R.id.editText13);
        setUserDetails=(Button) findViewById(R.id.button10);
        NoobApplication myapp= (NoobApplication) getApplication();
        userTO= myapp.getUser();
        benutzername.setText(userTO.getName());


        benutzername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    benutzername.setError(null);
                }

            }
        });



        setUserDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NoobApplication myapp= (NoobApplication) getApplication();
                userTO= myapp.getUser();
                passwordString = password.getText().toString();
                passwordStringHash = hashPasswort(passwordString);
                passwordStringWdh = passwordwdh.getText().toString();
                passwordStringHashWdh = hashPasswort(passwordStringWdh);
                benutzernameString = benutzername.getText().toString();
                if (benutzernameString.equals("")) {
                    benutzername.setError("Bitte trage einen Benutzernamen ein");
                    benutzername.requestFocus();
                }

                if (benutzernameString.equals(userTO.getName()) && passwordString.isEmpty() && passwordStringWdh.isEmpty()) {
                    benutzername.setError("Es wurden keine Daten geändert");
                    benutzername.requestFocus();
                } else {
                    if (!passwordString.isEmpty() || !passwordStringWdh.isEmpty()) {
                        boolean isCorrect=true;
                        if (passwordStringWdh.equals("")) {
                            passwordwdh.setError("Bitte Wiederhole dein Passwort");
                            passwordwdh.requestFocus();
                            isCorrect=false;
                        }
                        if (!validPassword(passwordString)) {
                            password.setError("Passwort muss mindestens 8 Zeichen haben");
                            password.requestFocus();
                            isCorrect=false;
                        }
                        if (!validPasswordWdh(passwordString, passwordStringWdh)) {
                            passwordwdh.setError("Passwort stimmt nicht überein");
                            passwordwdh.requestFocus();
                            isCorrect=false;
                        }
                        if(isCorrect) {
                            SetUserDetails setUser = new SetUserDetails(view.getContext());
                            setUser.execute(benutzernameString, passwordStringHash, passwordStringHashWdh);
                            GetUserDetails userDetails = new GetUserDetails(getApplicationContext(), (NoobApplication) getApplication());
                            userDetails.execute();
                            password.setText("");
                            passwordwdh.setText("");
                        }
                    } else {
                        SetUserDetails setUserOld = new SetUserDetails(view.getContext());
                        setUserOld.execute(benutzernameString, userTO.getPassword(), userTO.getPassword());
                        GetUserDetails userDetails = new GetUserDetails(getApplicationContext(),(NoobApplication) getApplication());
                        userDetails.execute();
                    }
                }
            }
        });

        userLoeschen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setMessage("Wollen Sie Ihren Account wirklich löschen?")
                        .setCancelable(false)
                        .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                DeleteAccount accountLoeschen = new DeleteAccount();
                                password = (EditText) findViewById(R.id.editText30);
                                String passwordStringHash = hashPasswort(password.getText().toString());

                                accountLoeschen.execute(passwordStringHash);
                            }
                        })
                        .setNegativeButton("Nein", null)
                        .show();
            }

        });
    }
    private boolean validPassword(String password){

        if(password!=null&&password.length()>=8){
            return true;
        }
        return false;
    }


    private boolean validPasswordWdh(String password, String passwordwdh){
        if(password.equals(passwordwdh)){
            return true;
        }
        return false;
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
    private String hashPasswort(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(password.getBytes());
            String passwordnew= new BigInteger(1,messageDigest.digest()).toString(16);
            //String passwordnew = messageDigest.toString();
            return passwordnew;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


    public class DeleteAccount extends AsyncTask<String, String, ReturnCodeResponse> {
        private int sessionId;


        @Override
        protected ReturnCodeResponse doInBackground(String... params) {
            String password=params[0];
            ReturnCodeResponse userDelete = null;
            NoobOnlineServiceImpl onlineService = new NoobOnlineServiceImpl();
            NoobApplication myApp = (NoobApplication) getApplication();
            sessionId=myApp.getSessionId();
            try {
                userDelete = onlineService.deleteUser(sessionId,password);
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
            String passwort=params[1];
            String passwortwdh=params[2];
            NoobApplication myApp = (NoobApplication) getApplication();

            sessionId=myApp.getSessionId();
            ReturnCodeResponse setUserDetails = null;
            NoobOnlineServiceImpl onlineService = new NoobOnlineServiceImpl();
            try {
                setUserDetails = onlineService.setUserDetails(sessionId,name,passwort,passwortwdh);
                returnCode = setUserDetails.getReturnCode();
                message=setUserDetails.getMessage();
                return setUserDetails;
            } catch (Exception e) {

            }
            return null;
        }
        protected void onPostExecute(ReturnCodeResponse returnCodeResponse){
            Dialog.dismiss();

                    Toast.makeText(context, returnCodeResponse.getMessage(), Toast.LENGTH_SHORT).show();

        }

    }
}
