package de.fh_muenster.noobApp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import de.fh_muenster.noob.NoobOnlineService;
import de.fh_muenster.noob.UserLoginResponse;


public class LoginActivity extends ActionBarActivity {
    private EditText email;
    private EditText password;
    private Button loginButton;
    private String passwordStringHash;
    private Switch testMode;
    private static final String TAG = LoginActivity.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final NoobApplication myApp = (NoobApplication) getApplication();

        testMode = (Switch) findViewById(R.id.switch1);
        testMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true) {
                    myApp.setTestmode(true);
                    Log.d(TAG, "TESTMODE ON");
                }
                if(isChecked == false) {
                    myApp.setTestmode(false);
                    Log.d(TAG, "TESTMODE OFF");
                }
            }
        });
        loginButton=(Button) findViewById(R.id.button2);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginTask loginTask = new LoginTask(view.getContext());
                email = (EditText) findViewById(R.id.editText3);
                password = (EditText) findViewById(R.id.editText4);
                String emailString = email.getText().toString();
                String passwordString = password.getText().toString();
                passwordStringHash=hashPasswort(passwordString);
                if (!emailString.equals("") && !passwordStringHash.equals("")) {
                    loginTask.execute(emailString, passwordStringHash);
                    NoobApplication myApp = (NoobApplication) getApplication();
                    myApp.setUserId(emailString);
                } else {
                    Toast.makeText(view.getContext(), "Username und Password dürfen nicht leer sein!",
                            Toast.LENGTH_SHORT).show();
                }
            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_login, menu);

        return super.onCreateOptionsMenu(menu);

    }
    //Über das Fragezeichen wird die Help Activity gestartet
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_settings:
                openHelpActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openHelpActivity(){
        Intent i =new Intent(LoginActivity.this,HelpActivity.class);
        startActivity(i);
    }

    /*
    Methode um GoogleMaps mit Münster zu öffnen
    public void openGoogleMaps(View view) {
        String geoUriString = getResources().getString(R.string.map_location);
        Uri geoUri = Uri.parse(geoUriString);
        Intent mapCall = new Intent(Intent.ACTION_VIEW, geoUri);
        startActivity(mapCall);
    }
    */
    public void openRegisterActivity(View view){
        Intent y= new Intent(LoginActivity.this,UserManagementAcitivtiy.class);
        startActivity(y);
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


    public void openNewLocation(View view){
        Intent z= new Intent(LoginActivity.this,NewLocationActivity.class);
        startActivity(z);
    }

    public void testMode(View view) {
        NoobApplication myApp = (NoobApplication) getApplication();
        myApp.setTestmode(true);
    }


    public class LoginTask extends AsyncTask<String,String,UserLoginResponse>{
        private ProgressDialog Dialog = new ProgressDialog(LoginActivity.this);

        private Context context;

        public LoginTask(Context context){
            this.context=context;
        }

        /**
         * Während des Loginvorgangs wird ein Dialog angezeigt
         */
        @Override
        protected void onPreExecute()
        {
            Dialog.setMessage("Anmelden...");
            Dialog.show();
        }

        @Override
        protected UserLoginResponse doInBackground(String... params){
            String email=params[0];
            String password=params[1];
            UserLoginResponse userLogin;
            NoobApplication myApp = (NoobApplication) getApplication();
            NoobOnlineService onlineService;
            if(myApp.isTestmode()) {
                onlineService = new NoobOnlineServiceMock();
            }
            else {
                onlineService  = new NoobOnlineServiceImpl();
            }
            try{
                userLogin = onlineService.login(email, password);
                return userLogin;
            }
            catch(Exception e){

            }
            return null;
        }

        protected void onPostExecute(UserLoginResponse userLoginResponse){
            Dialog.dismiss();
            if (userLoginResponse.getReturnCode() == 10) {
                Toast.makeText(getApplicationContext(), "Keine Verbidung zum Server", Toast.LENGTH_SHORT).show();
            }
            else {
                if(userLoginResponse.getReturnCode() == 1) {
                    email.setError(userLoginResponse.getMessage());
                    email.requestFocus();
                }
                if(userLoginResponse.getReturnCode() == 2) {
                    Toast.makeText(getApplicationContext(), userLoginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
                if (userLoginResponse.getReturnCode() == 0) {
                    Toast.makeText(getApplicationContext(), userLoginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    NoobApplication myApp = (NoobApplication) getApplication();
                    myApp.setSessionId(userLoginResponse.getSessionId());
                    GetUserDetails userDetails = new GetUserDetails(getApplicationContext(),(NoobApplication) getApplication());
                    userDetails.execute();
                    Intent i = new Intent(context, CitySelectionActivity.class);
                    startActivity(i);
                }
            }
        }
    }

}

