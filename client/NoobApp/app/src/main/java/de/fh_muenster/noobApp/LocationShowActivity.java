package de.fh_muenster.noobApp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by marius on 02.06.15.
 * @author marius
 * Die Activity zeigt eine Location und deren Details
 */
public class LocationShowActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_show);

        //Titel der Activity ersetzen
        NoobApplication myApp = (NoobApplication) getApplication();
        setTitle(myApp.getLocation().getName() + " in " + myApp.getCity());

        TextView textViewAdress = (TextView)findViewById(R.id.textView14);
        textViewAdress.setText(myApp.getLocation().getStreet() + " " + myApp.getLocation().getNumber() + " " + myApp.getLocation().getPlz() + " " + myApp.getLocation().getCity());

        TextView textViewDescription = (TextView)findViewById(R.id.textView15);
        textViewDescription.setText(myApp.getLocation().getDescription() + "\n" + "Inhaber: " + myApp.getLocation().getOwner().getName());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_location_show, menu);
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
