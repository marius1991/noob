package de.fh_muenster.noobApp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import de.fh_muenster.noob.UserLoginResponse;


public class LoginActivity extends ActionBarActivity {
    private EditText email;
    private EditText password;
    private Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton=(Button) findViewById(R.id.button2);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginTask loginTask = new LoginTask(view.getContext());
                email = (EditText) findViewById(R.id.editText3);
                password = (EditText) findViewById(R.id.editText4);
                String emailString = email.getText().toString();
                String passwordString = password.getText().toString();
                if (!emailString.equals("") && !passwordString.equals("")) {
                    loginTask.execute(emailString, passwordString);
                } else {
                    Toast.makeText(view.getContext(), "Username und Password duerfen nicht leer sein!",
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
        Intent y= new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(y);
    }

    public void openNewLocation(View view){
        Intent z= new Intent(LoginActivity.this,NewLocationActivity.class);
        startActivity(z);
    }

    public class LoginTask extends AsyncTask<String,String,Integer>{
        private Context context;
        private String message;
        private int returnCode;

        public LoginTask(Context context){
            this.context=context;
        }
        @Override
        protected Integer doInBackground(String... params){
            String email=params[0];
            String password=params[1];
            UserLoginResponse userLogin;
            NoobOnlineServiceImpl onlineService=new NoobOnlineServiceImpl();
            try{
                userLogin = onlineService.login(email, password);
                Integer sessionId=userLogin.getSessionId();
                message=userLogin.getMessage();
                returnCode=userLogin.getReturnCode();
                return sessionId;
            }
            catch(Exception e){

            }
            return null;
        }

        protected void onPostExecute(Integer sessionId){
            //Toast.makeText(context,message,
              //      Toast.LENGTH_SHORT).show();
            email.setError(message);
            email.requestFocus();
            if(returnCode==0){
                //NoobApplication myApp=(NoobApplication)getApplication();
                //myApp.setSessionId(sessionId);
                Intent i= new Intent(context,CitySelectionActivity.class);
                startActivity(i);
            }
        }
    }

}

