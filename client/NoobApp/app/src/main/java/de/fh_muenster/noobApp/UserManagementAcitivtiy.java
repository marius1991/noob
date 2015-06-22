package de.fh_muenster.noobApp;

import android.app.AlertDialog;
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
import android.widget.Toast;
import de.fh_muenster.noob.ReturnCodeResponse;
import de.fh_muenster.noob.UserTO;

/**
 * Created by marco
 * Diese Activity ist für Änderungen eines Users zuständig
 * @author marco
 */
public class UserManagementAcitivtiy extends ActionBarActivity {
    private EditText password;
    private EditText passwordwdh;
    private EditText benutzername;
    private String passwordString="";
    private String passwordStringWdh="";
    private String benutzernameString="";
    private Button userLoeschen;
    private Button setUserDetails;
    private String passwordStringHashWdh;
    private UserTO userTO;
    private String passwordStringHash;
    private Password passwordmeth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management_acitivtiy);

        //Referenz Klasse Password, Validierung und hashing
        passwordmeth= new Password();

        //Füllt die Variablen mit den ViewElementen aus der LoginActivity
        password = (EditText) findViewById(R.id.editText14);
        passwordwdh = (EditText) findViewById(R.id.editText15);
        userLoeschen = (Button) findViewById(R.id.button11);
        benutzername = (EditText) findViewById(R.id.editText13);
        setUserDetails=(Button) findViewById(R.id.button10);

        //Füllt die Variable "myApp" mit der globalen Applikation Klasse
        final NoobApplication myapp= (NoobApplication) getApplication();

        //Holt das aktuelle userTO Objekt
        userTO= myapp.getUser();

        //Setzt den Benutzernamen im Textfeld
        benutzername.setText(userTO.getName());

        benutzername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            /**
             * Diese Methode überprüft, ob beim Textfeld "benutzername" der Focus verlassen worden ist && das Textfeld "benutzername" nicht leer ist
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


        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            /**
             * Diese Methode überprüft, ob beim Textfeld "password" der Focus verlassen worden ist && das Textfeld "password" nicht leer ist
             * Ist dies der Fall, wird die Error-Meldung nicht mehr angezeigt
             * @param v
             * @param hasFocus
             */
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && !password.getText().toString().isEmpty()) {
                    password.setError(null);
                }
            }
        });


        passwordwdh.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            /**
             * Diese Methode überprüft, ob beim Textfeld "passwordwdh" der Focus verlassen worden ist && das Textfeld "passwordwdh" nicht leer ist
             * Ist dies der Fall, wird die Error-Meldung nicht mehr angezeigt
             * @param v
             * @param hasFocus
             */
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && !passwordwdh.getText().toString().isEmpty()) {
                    passwordwdh.setError(null);
                }
            }
        });

        setUserDetails.setOnClickListener(new View.OnClickListener() {
            /**
             * Diese Methode überprüft, ob die Felder anhand der Validierung und einfachen IF-Abfragen ob
             * die Daten richtig eingegeben worden sind.
             * Wenn nur der Benutzername geändert worden ist, werden die alten Passwortwerte mitgeschickt vom userTO
             * Wenn alle Werte geändert worden sind werde diese auch zum Server geschickt
             * @param view
             */
            @Override
            public void onClick(View view) {
                NoobApplication myapp= (NoobApplication) getApplication();
                userTO= myapp.getUser();
                passwordString = password.getText().toString();
                passwordStringWdh = passwordwdh.getText().toString();
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
                        if (!passwordmeth.validPassword(passwordString)) {
                            password.setError("Passwort muss mindestens 8 Zeichen haben");
                            password.requestFocus();
                            isCorrect=false;
                        }
                        if (!passwordmeth.validPasswordWdh(passwordString, passwordStringWdh)) {
                            passwordwdh.setError("Passwort stimmt nicht überein");
                            passwordwdh.requestFocus();
                            isCorrect=false;
                        }
                        if(isCorrect) {
                            SetUserDetails setUser = new SetUserDetails(view.getContext());
                            passwordStringHash = passwordmeth.hashPasswort(passwordString);
                            passwordStringHashWdh = passwordmeth.hashPasswort(passwordStringWdh);
                            setUser.execute(benutzernameString, passwordStringHash, passwordStringHashWdh);
                            GetUserDetails userDetails = new GetUserDetails(getApplicationContext(), (NoobApplication) getApplication());
                            userDetails.execute();
                            password.setText("");
                            passwordwdh.setText("");
                        }
                    }else {
                        SetUserDetails setUserOld = new SetUserDetails(view.getContext());
                        setUserOld.execute(benutzernameString, userTO.getPassword(), userTO.getPassword());
                        GetUserDetails userDetails = new GetUserDetails(getApplicationContext(),(NoobApplication) getApplication());
                        userDetails.execute();
                    }
                }
            }
        });

        userLoeschen.setOnClickListener(new View.OnClickListener() {
            /**
             * Diese Methode überprüft, öffnet ein Dialog Fenster, wodurch gepüft wird
             * ob der User wirklich seinen Account löschen will. Wenn die der Fall ist wird der Asynctask DeleAccount aufgerufen
             * @param view
             */
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setMessage("Wollen Sie Ihren Account wirklich löschen?")
                        .setCancelable(false)
                        .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                DeleteAccount accountLoeschen = new DeleteAccount();
                                password = (EditText) findViewById(R.id.editText30);
                                String passwordStringHash = passwordmeth.hashPasswort(password.getText().toString());
                                    accountLoeschen.execute(passwordStringHash);
                            }
                        })
                        .setNegativeButton("Nein", null)
                        .show();
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


    //Asynctask für Account löschen
    public class DeleteAccount extends AsyncTask<String, String, ReturnCodeResponse> {
        private int sessionId;
        private ProgressDialog Dialog = new ProgressDialog(UserManagementAcitivtiy.this);

        //Diese Methode zeigt während des Löschvorgangs ein Dialog an
        @Override
        protected void onPreExecute()
        {
            Dialog.setMessage("Account wird gelöscht...");
            Dialog.show();
        }

        /**
         * Diese Methode, führt einen Thread im Hintergrund aus und schickt die Werte Password und SessionId vom User zum Server
         * @param params
         */
        @Override
        protected ReturnCodeResponse doInBackground(String... params) {
            String password=params[0];
            ReturnCodeResponse userDelete;
            NoobApplication myApp = (NoobApplication) getApplication();
            NoobOnlineServiceImpl onlineService = new NoobOnlineServiceImpl();
            sessionId=myApp.getSessionId();
            userDelete = onlineService.deleteUser(sessionId,password);
            return userDelete;
        }
        /**
         * Diese Methode wird ausgeführt nachdem doInBackground durchgelaufen ist.Überprüft den returnCode vom Server,
         * ob alles geklappt hat
         * @param deleteUserResponse
         */
        protected void onPostExecute(ReturnCodeResponse deleteUserResponse) {
            Dialog.dismiss();
            if (deleteUserResponse.getReturnCode() == 10) {
                Toast.makeText(getApplicationContext(), "Keine Verbidung zum Server", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(UserManagementAcitivtiy.this, deleteUserResponse.getMessage(), Toast.LENGTH_LONG).show();
                if(deleteUserResponse.getReturnCode() != 2) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        }
    }

    //AysncTask für User-Änderungen speichern
    public class SetUserDetails extends AsyncTask<String, String, ReturnCodeResponse> {
        private Context context;
        private int sessionId;
        private ProgressDialog Dialog = new ProgressDialog(UserManagementAcitivtiy.this);

        public SetUserDetails(Context context) {
            this.context = context;
        }
        //Diese Methode zeigt während der Speicherung der Daten ein Dialog an
        @Override
        protected void onPreExecute()
        {
            Dialog.setMessage("Daten werden gespeichert...");
            Dialog.show();
        }

        /**
         * Diese Methode, führt einen Thread im Hintergrund aus und schickt die Werte Passwort, Passwortwdh und Name vom User zum Server
         * @param params
         */
        @Override
        protected ReturnCodeResponse doInBackground(String... params) {
            String name=params[0];
            String passwort=params[1];
            String passwortwdh=params[2];
            NoobApplication myApp = (NoobApplication) getApplication();
            sessionId=myApp.getSessionId();
            ReturnCodeResponse setUserDetails = null;
            NoobOnlineServiceImpl onlineService = new NoobOnlineServiceImpl();
            setUserDetails = onlineService.setUserDetails(sessionId,name,passwort,passwortwdh);
            return setUserDetails;
        }

        /**
         * Diese Methode wird ausgeführt nachdem doInBackground durchgelaufen ist.Überprüft den returnCode vom Server,
         * ob alles geklappt hat
         * @param returnCodeResponse
         */
        protected void onPostExecute(ReturnCodeResponse returnCodeResponse){
            Dialog.dismiss();
            if (returnCodeResponse.getReturnCode() == 10) {
                Toast.makeText(getApplicationContext(), "Keine Verbidung zum Server", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context, returnCodeResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }
}
