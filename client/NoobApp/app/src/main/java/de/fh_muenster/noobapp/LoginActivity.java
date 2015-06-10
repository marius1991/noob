package de.fh_muenster.noobApp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


public class LoginActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

}

