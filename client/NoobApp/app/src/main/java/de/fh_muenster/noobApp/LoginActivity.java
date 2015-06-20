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
import de.fh_muenster.noob.NoobOnlineService;
import de.fh_muenster.noob.UserLoginResponse;

/**
 * Created by marco
 * Diese Activity ist für den Login zuständig.Außerdem gelangt man zum Hilfe-Center und zusätzlich
 * zur "Register Activity"
 * @author marco
 */
public class LoginActivity extends ActionBarActivity {
    private EditText email;
    private EditText password;
    private Button loginButton;
    private String passwordStringHash;
    private Switch testMode;
    private static final String TAG = LoginActivity.class.getName();
    String emailString;
    String passwordString;

    /**
     * Diese Methode wird aufgerufen, wenn die NoobApp gestartet wird. Sie lädt das Layout von der
     * XML-Datei "activity_login"
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Setze Layout Conent
        setContentView(R.layout.activity_login);

        //Füllt die Variable "myApp" mit der globalen Applikation Klasse
        final NoobApplication myApp = (NoobApplication) getApplication();

        //Füllt die Variablen mit den ViewElementen aus der LoginActivity
        email = (EditText) findViewById(R.id.editText3);
        password = (EditText) findViewById(R.id.editText4);
        testMode = (Switch) findViewById(R.id.switch1);
        loginButton=(Button) findViewById(R.id.button2);

        //Diese Methode überprüft ob der TestMode an oder aus ist
        testMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    myApp.setTestmode(true);
                    Log.d(TAG, "TESTMODE ON");
                }
                if (!isChecked) {
                    myApp.setTestmode(false);
                    Log.d(TAG, "TESTMODE OFF");
                }
            }
        });

        /**
         * Diese Methode überprüft, ob beim Textfeld "email" der Focus verlassen worden ist && das Textfeld "email" nicht leer ist
         * Ist dies der Fall, wird die Error-Meldung nicht mehr angezeigt
         */
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus&&!email.getText().toString().isEmpty()) {
                    email.setError(null);
                }

            }
        });

        /**
         * Diese Methode überprüft, ob beim Textfeld "password" der Focus verlassen worden ist && das Textfeld "password" nicht leer ist
         * Ist dies der Fall, wird die Error-Meldung nicht mehr angezeigt
         */
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus&&!password.getText().toString().isEmpty()) {
                    password.setError(null);
                }

            }
        });

        /**
         * Diese Methode überprüft, ob die Felder Passwort und Email nicht leer sind. Ist dies der Fall wird
         * der Asynctask "LoginTask" aufgerufen.
         */
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailString= email.getText().toString();
                passwordString = password.getText().toString();
                LoginTask loginTask = new LoginTask(view.getContext());
                if(emailString.isEmpty()){
                    email.setError("Bitte tragen Sie eine E-Mail Adresse ein");
                    email.requestFocus();
                }
                if(passwordString.isEmpty()){
                    password.setError("Bitte tragen Sie Ihr Passwort ein");
                    password.requestFocus();
                }
                if (!emailString.isEmpty() && !passwordString.isEmpty()) {
                    NoobApplication myApp = (NoobApplication) getApplication();
                    passwordStringHash=myApp.hashPasswort(passwordString);
                    loginTask.execute(emailString, passwordStringHash);
                    myApp.setUserId(emailString);
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

    //Diese Methode öffnet die HelpActivity
    public void openHelpActivity(){
        Intent i =new Intent(LoginActivity.this,HelpActivity.class);
        startActivity(i);
    }

    //Diese Methode öffnet die RegisterView
    public void openRegisterActivity(View view){
        Intent y= new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(y);
    }


    //Asynctask für Login
    public class LoginTask extends AsyncTask<String,String,UserLoginResponse>{
        private ProgressDialog Dialog = new ProgressDialog(LoginActivity.this);

        private Context context;

        //Konstuktor setze den Context
        public LoginTask(Context context){
            this.context=context;
        }

        //Diese Methode zeigt während des Loginvorgangs ein Dialog an
        @Override
        protected void onPreExecute()
        {
            Dialog.setMessage("Anmelden...");
            Dialog.show();
        }

        /**
         * Diese Methode, führt einen Thread im Hintergrund aus und schickt das gesetzte Passwort und die gesetzte Email zum Server
         * ist der Testmode gesetzt wird die Methode im MockObject aufgerufen
         * @param params
         */
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
                userLogin = onlineService.login(email, password);
                return userLogin;
        }
        /**
         * Diese Methode wird ausgeführt nachdem doInBackground durchgelaufen ist.Überprüft den returnCode vom Server
         * und start bei returnCode=0 die CitySelectionActivity
         * @param userLoginResponse
         */
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

