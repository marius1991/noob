package de.fh_muenster.noobApp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import de.fh_muenster.noobApp.R;

/**
 * @author marius
 * In dieser Activity können die angezeigten Locations einer Stadt und einer Kategorie sortiert und gefiltert werden
 */
public class LocationSortActivity extends ActionBarActivity {

    private RadioGroup radioGroup;
    private RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_sort);
    }

    /**
     * Diese Funktion wird aufgerufen sobald auf den Butto gedrückt wird, welcher die Sortierung aktiviert
     * @param view
     */
    public void clickFuncSort(View view){
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedId);

        //Zeigen wie sortiert wurde
        Toast.makeText(LocationSortActivity.this,
                radioButton.getText(), Toast.LENGTH_SHORT).show();

        NoobApplication myApp = (NoobApplication) getApplication();
        myApp.setSortBy(radioButton.getText().toString());
        Intent i = new Intent(LocationSortActivity.this, LocationListActivity.class);
        startActivity(i);
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
}
