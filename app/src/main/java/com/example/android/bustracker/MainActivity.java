package com.example.android.bustracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Attributes
    private BusData busData = BusData.getInstance();

    // Constants
    public final static String LIST_BUSES = "com.example.android.BusTracker.LIST_BUSES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method gets called when the Button Add is pressed and proceeds to add a bus to the app.
     * @param view
     */

    public void addBus(View view)
    {
        String nameOfBus = getNameInput();

        if (nameOfBus.length() > 0)
        {
            if (!busData.isBusRegistered(nameOfBus))
            {
                busData.addBus(nameOfBus);

                clearNameInputField();

                makeToast("Bus successfully added!");
            } else
            {
                makeToast("Bus already registered");
            }
        } else
        {
            makeToast("Enter a valid name!");
        }
    }

    // Helper Function

    private String getNameInput()
    {
        EditText input = (EditText) findViewById(R.id.nameOfBus);
        return input.getText().toString().toUpperCase();
    }

    private void clearNameInputField()
    {
        EditText input = (EditText) findViewById(R.id.nameOfBus);
        input.setText("");
    }

    private void makeToast(String text)
    {
        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        toast.show();
    }

    // Functions to change activities

    public void showBuses(View view)
    {
        Intent intent = new Intent(this, ShowBuses.class);

        startActivity(intent);
    }

    public void addRide(View view)
    {
        Intent intent = new Intent(this, AddRide.class);

        startActivity(intent);
    }

}
