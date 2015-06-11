package de.fh_muenster.noobApp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // If your minSdkVersion is 11 or higher, instead use:
        // getActionBar().setDisplayHomeAsUpEnabled(true);

        email=(EditText) findViewById(R.id.editText5);
        password=(EditText) findViewById(R.id.editText7);
        passwordwdh=(EditText) findViewById(R.id.editText8);
        registerButton=(Button) findViewById(R.id.button6);
        benutzername=(EditText) findViewById(R.id.editText6);

        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                emailString = email.getText().toString();
                passwordString = password.getText().toString();
                passwordStringWdh = passwordwdh.getText().toString();
                benutzernameString =benutzername.getText().toString();

                 if(emailString.equals("")) {
                     email.setError("Bitte trage eine Email-Adresse ein");
                     email.requestFocus();
                 }  else if (passwordString.equals("")){
                     password.setError("Bitte trage ein Passwort ein");
                     password.requestFocus();
                 }  else if (passwordStringWdh.equals("")) {
                     passwordwdh.setError("Bitte Wiederhole dein Passwort");
                     passwordwdh.requestFocus();
                 }  else if (benutzernameString.equals("")) {
                     benutzername.setError("Bitte trage ein Benutzernamen ein");
                     benutzername.requestFocus();
                 }  else if (!validMail(emailString)) {
                     email.setError("Keine reale Email-Adresse");
                     email.requestFocus();
                 }  else if (!validPassword(passwordString)) {
                     password.setError("Passwort muss mindestens 8 Zeichen haben");
                     password.requestFocus();
                 }  else if (!validPasswordWdh(passwordString, passwordStringWdh)) {
                    passwordwdh.setError("Passwort stimmt nicht ueberein");
                    passwordwdh.requestFocus();
                 }  else {
                    Toast.makeText(view.getContext(), "Account wurde erfolgreich erstellt",
                            Toast.LENGTH_SHORT).show();
                 }

            }

        });

    }

    private boolean validMail(String email){
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
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
}
