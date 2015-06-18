package de.fh_muenster.noobApp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import de.fh_muenster.noob.LocationTO;
import de.fh_muenster.noobApp.R;

/**
 * @author marius
 * Diese Activity zeigt die Suchergebnisse an, nachdem nach einer Location gesucht wurde.
 */
public class LocationSearchActivity extends ActionBarActivity {

    private LocationTO selectedFromList = null;
    private static final String TAG = LocationSearchActivity.class.getName();

    /**
     * Diese Methode wird aufgerufen, wenn die Activity gestartet wird.
     * Der Titel der Activity wird gesetzt.
     * Die Suchergebnisse werden in einer Liste angezeigt.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_search);
        NoobApplication myApp = (NoobApplication) getApplication();

        //Titel der Activity verändern
        setTitle("Suche nach '" + myApp.getSearch() + "' in " + myApp.getCity());

        //ListView Objekt mit Daten füllen
        ArrayAdapter adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, myApp.getLocationSearchResults());
        final ListView lv = (ListView)findViewById(R.id.listView2);
        lv.setAdapter(adapter);

        //Beim Selektieren eines Eintrags der Liste wird die LocationShowActivity der ausgewählten Location aufgerufen
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
                selectedFromList = (LocationTO) (lv.getItemAtPosition(myItemInt));
                //Toast.makeText(getApplicationContext(), selectedFromList + " ausgewählt", Toast.LENGTH_SHORT).show();
                NoobApplication myApp = (NoobApplication) getApplication();
                myApp.setLocation(selectedFromList);
                Log.d(TAG, myApp.getLocation().getName());
                Log.d(TAG, myApp.getLocation().getDescription());
                Intent i = new Intent(LocationSearchActivity.this, LocationShowActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_location_search, menu);
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

    /**
     * Diese Methode wird aufgerufen, wenn über das Menü der Eintrag 'Logout' gewählt wird.
     * Es erscheint eine Dialog, auf dem die Eingabe bestätigt werden muss.
     * Dann wird ein LogoutTask gestartet.
     * @param item
     */
    public void clickFuncLogout(MenuItem item) {
        Log.d(TAG, "Menüeintrag 'Logout' ausgewählt");
        new AlertDialog.Builder(this)
                .setMessage("Wollen Sie sich wirklich abmelden?")
                .setCancelable(false)
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        NoobApplication myApp = (NoobApplication) getApplication();
                        new LogoutTask(getApplicationContext(), myApp).execute(myApp.getSessionId());
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Nein", null)
                .show();
    }
}
