package de.fh_muenster.noobapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marius on 02.06.15.
 * @author marius
 * Activity zum Auswählen der Stadt
 */
public class CitySelectionActivity extends Activity implements OnItemSelectedListener {

    private String selected = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_selection);

        //Liste mit Testdaten füllen
        List valueList = new ArrayList<String>();
        valueList.add("Münster");
        valueList.add("Dortmund");
        valueList.add("Essen");
        valueList.add("Osnabrück");
        valueList.add("Hamburg");
        valueList.add("Bremen");

        //Spinner Objekt mit Liste füllen
        ArrayAdapter adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, valueList);
        Spinner sp = (Spinner)findViewById(R.id.spinner);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(this);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_city_selection, menu);
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
        //Toast.makeText(parent.getContext(), selected, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //Bei Klick auf Login Button wird die nächste Activity aufgerufen und die ausgewählte Stadt zentral gespeichert
    public void clickFunc(View view){
        NoobApplication myApp = (NoobApplication) getApplication();
        myApp.setCity(selected);
        //Toast.makeText(CitySelectionActivity.this, selected, Toast.LENGTH_SHORT).show();
        Intent i = new Intent(CitySelectionActivity.this, CategorySelectionActivity.class);
        startActivity(i);
    }
}
