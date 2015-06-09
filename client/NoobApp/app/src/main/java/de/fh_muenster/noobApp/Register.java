package de.fh_muenster.noobApp;

import android.os.AsyncTask;
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

import de.fh_muenster.exceptions.InvalidRegisterException;
import de.fh_muenster.noob.ReturnCodeResponse;


public class Register extends ActionBarActivity {

    private EditText email;
    private EditText password;
    private Button registerButton;
    private NoobOnlineServiceImpl onlineService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        onlineService = new NoobOnlineServiceImpl();
    //Instanzen in die Variable speichern
    email=(EditText) findViewById(R.id.editText);
    password=(EditText) findViewById(R.id.editText2);
    registerButton=(Button) findViewById(R.id.button);

    registerButton.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View view){
            final String emailString= email.getText().toString();
            final String passwordString = password.getText().toString();

            if(!validMail(emailString)){
                email.setError("Keine reale Email-Adresse");
                email.requestFocus();
            }
            else if(!validPassword(passwordString)){
                password.setError("Passwort muss mindestens 8 Zeichen haben");
                password.requestFocus();
            }
            else{
                Toast.makeText(view.getContext(), "Account wurde erfolgreich erstellt",
                Toast.LENGTH_SHORT).show();
            }
            new PostLoginToServer().execute();

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

        if(password!=null&&password.length()>8){
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
    //Asynchron senden (Innere Klasse)
    class PostLoginToServer extends AsyncTask<String, String, ReturnCodeResponse> {

        @Override
        protected ReturnCodeResponse doInBackground(String... params) {
            ReturnCodeResponse response = null;
            try {
                response = onlineService.register("Test", "test@test.de", "12345678", "12345678");
            } catch (InvalidRegisterException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(ReturnCodeResponse response) {
            Toast.makeText(Register.this, response.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
