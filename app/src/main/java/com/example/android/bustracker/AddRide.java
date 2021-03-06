package com.example.android.bustracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddRide extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final int MAX_DURATION = 600;

    private BusData busData = new BusData(this);
    private String busSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ride);

        setTitle("Add Ride");

        if (busData.isTableBusesEmpty()) {
            printWarning();
        } else {
            setSpinner();
        }
    }

    // Methods related to the spinner

    private void setSpinner() {

        // Store all the names of buses in a list to use in the ArrayAdapter
        // List<String> listOfBuses = new ArrayList<>(busData.getAllBusesRegistered());

        Spinner spinner = (Spinner) findViewById(R.id.busSpinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, busData.getAllBusesRegistered());

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

        busSelected = item;
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
        busSelected = "";
    }

    private void printWarning() {
        clearScreen();

        TextView warning = new TextView(this);

        warning.setText(R.string.no_buses_registered);
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

    public void addRide(View view) {
        // Validate bus selection
        if (busSelected.equals("")) {
            makeToast("Select a bus!");
            return;
        }

        // We proceed to get a valid duration
        int duration = getDurationOfRide();

        if (duration == -1)
        {
            return;
        }

        // We proceed to add a ride for that bus
        if (busData.addRide(busSelected, duration) == false) {
            makeToast("Operation failed. Try again!");
        } else {
            makeToast("Ride for bus " + busSelected + " added!");
        }

    }

    private int getDurationOfRide()
    {
        EditText editText = (EditText) findViewById(R.id.durationRide);

        String input = editText.getText().toString().trim();

        try {
            int duration = Integer.parseInt(input);

            if (duration > 0 && duration <= MAX_DURATION)
            {
                return duration;
            } else
            {
                makeToast("Duration must be between 1 min and " +
                        Integer.toString(MAX_DURATION) + " min!");
                return -1;
            }

        } catch (NumberFormatException e)
        {
            makeToast("Enter a valid duration!");
            return -1;
        }
    }

    private void makeToast(String text) {
        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        toast.show();
    }
}