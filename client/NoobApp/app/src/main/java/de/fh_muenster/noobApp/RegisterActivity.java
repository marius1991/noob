package de.fh_muenster.noobApp;

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
    private String passwordStringHash;
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
                passwordString=hashPasswort(passwordString);
                passwordStringWdh = passwordwdh.getText().toString();
                passwordStringHash=hashPasswort(passwordStringWdh);
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
                 }
                new RegisterTask(view.getContext()).execute(emailString,benutzernameString,passwordString,passwordStringWdh);

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
    //Uervorhanden responseCode 2
    public class RegisterTask extends AsyncTask<String,String, ReturnCodeResponse> {
        private Context context;
        private String message;
        private int returnCode;

        public RegisterTask(Context context){
            this.context=context;
        }
        @Override
        protected ReturnCodeResponse doInBackground(String... params) {
            ReturnCodeResponse userRegister = null;
            NoobOnlineServiceImpl onlineService=new NoobOnlineServiceImpl();
            String email=params[0];
            String benutzername=params[1];
            String passwort=params[2];
            String passwortwdh=params[3];
            try{
                userRegister = onlineService.register(benutzername,email,passwort,passwortwdh);
                message=userRegister.getMessage();
                returnCode=userRegister.getReturnCode();
            }catch (Exception e) {
                e.printStackTrace();
            }
            return userRegister;
        }

        @Override
        protected void onPostExecute(ReturnCodeResponse response) {
            Toast.makeText(RegisterActivity.this, response.getMessage(), Toast.LENGTH_LONG).show();
            if(returnCode==0){
                Intent i= new Intent(context,LoginActivity.class);
                startActivity(i);
            }
        }

    }
}
