package de.fh_muenster.noobApp;

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
import android.widget.Toast;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import de.fh_muenster.noob.ReturnCodeResponse;


/**
 * Created by marco
 * Diese Activity ist f�r die Registrieung eines Users zust�ndig
 * @author marco
 */
public class RegisterActivity extends ActionBarActivity {
    private EditText email;
    private EditText password;
    private EditText passwordwdh;
    private EditText benutzername;
    private Button registerButton;
    private String emailString="";
    private String passwordString="";
    private String passwordStringWdh="";
    private String benutzernameString="";
    private String passwordStringHash="";
    private String passwordStringHashWdh="";
    private Password passwordmeth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Referenz Klasse Password, Validierung und hashing
        passwordmeth= new Password();
        //F�llt die Variable "myApp" mit der globalen Applikation Klasse
        final NoobApplication myApp = (NoobApplication) getApplication();

       //F�llt die Variablen mit den View-Elementen aus der RegisterActivity
        email=(EditText) findViewById(R.id.editText5);
        password=(EditText) findViewById(R.id.editText7);
        passwordwdh=(EditText) findViewById(R.id.editText8);
        registerButton=(Button) findViewById(R.id.button6);
        benutzername=(EditText) findViewById(R.id.editText6);



        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            /**
             * Diese Methode �berpr�ft, ob beim Textfeld "email" der Focus verlassen worden ist && das Textfeld "email" nicht leer ist
             * Ist dies der Fall, wird die Error-Meldung nicht mehr angezeigt
             * @param v
             * @param hasFocus
             */
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && !email.getText().toString().isEmpty()) {
                    email.setError(null);
                }
            }
        });

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            /**
             *Diese Methode �berpr�ft, ob beim Textfeld "email" der Focus verlassen worden ist && das Textfeld "email" nicht leer ist
             * Ist dies der Fall, wird die Error-Meldung nicht mehr angezeigt
             * @param v
             * @param hasFocus
             */
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus&&!password.getText().toString().isEmpty()) {
                    password.setError(null);
                }

            }
        });


        passwordwdh.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            /**
             *Diese Methode �berpr�ft, ob beim Textfeld "email" der Focus verlassen worden ist && das Textfeld "email" nicht leer ist
             * Ist dies der Fall, wird die Error-Meldung nicht mehr angezeigt
             * @param v
             * @param hasFocus
             */
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus&&!passwordwdh.getText().toString().isEmpty()) {
                    passwordwdh.setError(null);
                }

            }
        });

        benutzername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            /**
             *Diese Methode �berpr�ft, ob beim Textfeld "benutzername" der Focus verlassen worden ist && das Textfeld "benutzername" nicht leer ist
             * Ist dies der Fall, wird die Error-Meldung nicht mehr angezeigt
             * @param v
             * @param hasFocus
             */
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && !benutzername.getText().toString().isEmpty()) {
                    benutzername.setError(null);
                }

            }
        });

        registerButton.setOnClickListener(new View.OnClickListener(){
            /**
             * Diese Methode �berpr�ft, ob die Felder anhand der Validierung und einfachen IF-Abfragen ob
             * die Daten richtig eingegeben worden sind.
             * Ist dies der Fall wird Asynctask "RegisterTask" aufgerufen.
             * @param view
             */
            @Override
            public void onClick(View view) {
                emailString = email.getText().toString();
                passwordString = password.getText().toString();
                passwordStringWdh = passwordwdh.getText().toString();
                benutzernameString = benutzername.getText().toString();

                boolean isCorrect=true;
                 if(emailString.isEmpty()) {
                     email.setError("Bitte trage eine Email-Adresse ein");
                     email.requestFocus();
                     isCorrect=false;
                 }
                 if(benutzernameString.isEmpty()) {
                     benutzername.setError("Bitte trage einen Benutzernamen ein");
                     benutzername.requestFocus();
                     isCorrect=false;
                 }
                 if(passwordString.isEmpty()){
                     password.setError("Bitte trage ein Passwort ein");
                     password.requestFocus();
                     isCorrect=false;
                 }
                 if(passwordStringWdh.isEmpty()) {
                     passwordwdh.setError("Bitte Wiederhole dein Passwort");
                     passwordwdh.requestFocus();
                     isCorrect=false;
                 }
                 if(!validMail(emailString)&&!emailString.isEmpty()) {
                     email.setError("Keine reale Email-Adresse");
                     email.requestFocus();
                     isCorrect=false;
                 }
                 if(!passwordmeth.validPassword(passwordString)) {
                     password.setError("Passwort muss mindestens 8 Zeichen haben");
                     password.requestFocus();
                     isCorrect=false;
                 }
                 if(!passwordmeth.validPasswordWdh(passwordString, passwordStringWdh)) {
                     passwordwdh.setError("Passwort stimmt nicht ueberein");
                     passwordwdh.requestFocus();
                     isCorrect=false;
                 }
                 if(isCorrect&&!passwordString.isEmpty()&&!passwordStringWdh.isEmpty()&&!emailString.isEmpty()&&!benutzernameString.isEmpty()) {
                     passwordStringHash = passwordmeth.hashPasswort(passwordString);
                     passwordStringHashWdh = passwordmeth.hashPasswort(passwordStringWdh);
                     RegisterTask register = new RegisterTask(view.getContext());
                     register.execute(emailString, benutzernameString, passwordStringHash, passwordStringHashWdh);
                }

            }

        });

    }

        /**
          * Diese Methode �berpr�ft ob die eingebene EMail-Andresse eine "echte" ist
          * @param email
          */
        private boolean validMail(String email){
           String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            Pattern pattern = Pattern.compile(EMAIL_PATTERN);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
         }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_register, menu);
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

    //Asynctask f�r Register
    public class RegisterTask extends AsyncTask<String,String, ReturnCodeResponse> {
        private ProgressDialog Dialog = new ProgressDialog(RegisterActivity.this);
        private Context context;

        public RegisterTask(Context context){
            this.context=context;
        }
        //Diese Methode zeigt w�hrend des Registervorgangs ein Dialog an
        @Override
        protected void onPreExecute()
        {
            Dialog.setMessage("Registrieren...");
            Dialog.show();
        }

        /**
         * Diese Methode, f�hrt einen Thread schickt die Werte vom Formular zur Registrierung zum Server
         * @param params
         */
        @Override
        protected ReturnCodeResponse doInBackground(String... params) {
            ReturnCodeResponse userRegister = null;
            NoobOnlineServiceImpl onlineService=new NoobOnlineServiceImpl();
            String email=params[0];
            String benutzername=params[1];
            String passwort = params[2];
            String passwortwdh=params[3];
            userRegister = onlineService.register(benutzername,email,passwort,passwortwdh);
            return userRegister;
        }

        /**
         * Diese Methode wird ausgef�hrt nachdem doInBackground durchgelaufen ist.�berpr�ft den returnCode vom Server,
         * ob alles geklappt hat
         * @param response
         */
        @Override
        protected void onPostExecute(ReturnCodeResponse response) {
            Dialog.dismiss();
            if (response.getReturnCode() == 10) {
                Toast.makeText(getApplicationContext(), "Keine Verbidung zum Server", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(RegisterActivity.this, response.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

    }
}
