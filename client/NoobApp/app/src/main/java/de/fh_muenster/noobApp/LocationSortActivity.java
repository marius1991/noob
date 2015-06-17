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
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import de.fh_muenster.noobApp.R;

/**
 * @author marius
 * In dieser Activity können die angezeigten Locations einer Stadt und Kategorie sortiert und gefiltert werden
 */
public class LocationSortActivity extends ActionBarActivity {

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private static final String TAG = LocationSortActivity.class.getName();
    private SeekBar seekBar;
    private TextView textView;

    /**
     * Diese Methode wird beim Start der Activity aufgerufen.
     * Es wird eine SeekBar und dessen OnChangeListener erstellt.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_sort);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textView = (TextView) findViewById(R.id.textViewSeekBar);
        // TextView mit '0' initialisieren
        textView.setText(getString(R.string.activity_location_sort_minRating) + ": " + seekBar.getProgress() + "/" + seekBar.getMax());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //Nichts machen
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textView.setText(getString(R.string.activity_location_sort_minRating) + ": " + progress + "/" + seekBar.getMax());
            }
        });
    }

    /**
     * Diese Funktion wird aufgerufen sobald auf den Button gedrückt wird, welcher die Sortierung/Filterung aktiviert
     * @param view
     */
    public void clickFuncSort(View view){
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedId);

        //Zeigen wie sortiert wurde
        NoobApplication myApp = (NoobApplication) getApplication();
        if(radioButton != null) {
            if (radioButton.getText() != null) {
                Toast.makeText(LocationSortActivity.this, radioButton.getText(), Toast.LENGTH_SHORT).show();
                myApp.setSortBy(radioButton.getText().toString());
            }
        }

        myApp.setRatingFilter(seekBar.getProgress());
        Intent i = new Intent(LocationSortActivity.this, LocationListActivity.class);
        startActivity(i);
        LocationSortActivity.this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_location_sort, menu);
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
                        new LogoutTask(getApplicationContext()).execute(myApp.getSessionId());
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Nein", null)
                .show();
    }
}
