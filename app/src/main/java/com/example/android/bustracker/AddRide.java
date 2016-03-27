package com.example.android.bustracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class AddRide extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Attributes
    private ArrayList<String> listOfBuses = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ride);

        Intent intent = getIntent();

        listOfBuses = (ArrayList) intent.getSerializableExtra(MainActivity.LIST_BUSES);

        if (listOfBuses.isEmpty()) {
            printWarning();
        } else {
            setSpinner();
        }
    }

    // Methods related to the spinner

    private void setSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.busSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, listOfBuses);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        String item = parent.getItemAtPosition(pos).toString();

        //grade = item;
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
        //grade = "EXC";
    }


    private void printWarning() {
        clearScreen();

        TextView warning = new TextView(this);

        warning.setText("No buses are registered!");
        warning.setTextSize(16);
        warning.setGravity(Gravity.CENTER_HORIZONTAL);

        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.addRideMain);

        // Add the warning just after the heading.
        mainLayout.addView(warning);

    }

    private void clearScreen() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.inputNewRide);
        layout.removeAllViews();
    }
}