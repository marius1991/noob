package de.fh_muenster.noobapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marius on 02.06.15.
 * @author marius
 * Activity zeigt die Kategorien von Locations der ausgewählten Stadt
 */
public class CategorySelectionActivity extends ActionBarActivity implements OnItemSelectedListener {

    private String selected = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selection);

        //Titel der Activity durch den Namen der ausgewählten Stadt ersetzen
        NoobApplication myApp = (NoobApplication) getApplication();
        setTitle(myApp.getCity());

        //Liste mit Testdaten füllen
        List valueList = new ArrayList<String>();
        valueList.add("Kneipe");
        valueList.add("Arzt");
        valueList.add("Supermarkt");
        valueList.add("Bar");
        valueList.add("Fastfood");
        valueList.add("Tankstelle");

        //Spinner Objekt mit Testdaten füllen
        ArrayAdapter adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, valueList);
        Spinner sp = (Spinner)findViewById(R.id.spinner);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_category_selection, menu);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selected = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //Beim Klick auf den Button "Aüswählen" wird die nächste Activity aufgerufen und die ausgewählte Kategorie zetral gespeichert
    public void clickFunc(View view){
        NoobApplication myApp = (NoobApplication) getApplication();
        myApp.setCategory(selected);
        //Toast.makeText(CategorySelectionActivity.this, selected, Toast.LENGTH_SHORT).show();
        Intent i = new Intent(CategorySelectionActivity.this, CategoryListActivity.class);
        startActivity(i);
    }
}
