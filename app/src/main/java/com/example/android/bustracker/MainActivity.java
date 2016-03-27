package com.example.android.bustracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> listOfBuses = new ArrayList<String>();

    public final static String LIST_BUSES = "com.example.android.BusTracker.LIST_BUSES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addBus(View view)
    {
        String nameOfBus = getInput();

        if (nameOfBus.length() > 0)
        {
            if (!isBusRegistered(nameOfBus))
            {
                listOfBuses.add(nameOfBus);

                cleanInputField();

                makeToast("Bus successfully added!");
            } else
            {
                makeToast("Bus already registered");
            }
        } else
        {
            makeToast("Enter a valid name");
        }

    }

    private void cleanInputField()
    {
        EditText input = (EditText) findViewById(R.id.nameOfBus);
        input.setText("");
    }

    private boolean isBusRegistered(String nameOfBus)
    {
        return listOfBuses.contains(nameOfBus);
    }

    private void addBusToList(String nameOfBus)
    {
        TextView newBus = new TextView(this);

        newBus.setTextSize(16);
        newBus.setText(nameOfBus);

        LinearLayout layout = (LinearLayout) findViewById(R.id.content);
        layout.addView(newBus);
    }

    private String getInput()
    {
        EditText input = (EditText) findViewById(R.id.nameOfBus);
        return input.getText().toString().toUpperCase();
    }

    private void makeToast(String text)
    {
        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void changeActivity(View view)
    {
        Intent intent = new Intent(this, ShowBuses.class);

        intent.putExtra(LIST_BUSES, listOfBuses);

        startActivity(intent);
    }

}
