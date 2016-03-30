package com.example.android.bustracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;

public class ShowBuses extends AppCompatActivity {

    private HashMap<String, Integer> listOfBuses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_buses);

        listOfBuses = BusData.getInstance().getListOfBuses();

        if (listOfBuses.isEmpty()) {
            printWarning();
        } else {
            printAllBuses();
        }
    }

    private void printWarning()
    {
        TextView warning = new TextView(this);

        warning.setText("No buses were registered!");
        warning.setTextSize(16);
        warning.setGravity(Gravity.CENTER_HORIZONTAL);

        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mainShowBuses);

        // Add the warning just after the heading.
        mainLayout.addView(warning, 1);
    }

    private void printAllBuses()
    {
        LinearLayout layout = (LinearLayout) findViewById(R.id.mainShowBuses);

        for (String nameOfBus : listOfBuses.keySet())
        {
            printSingleBus(nameOfBus, layout);
        }
    }

    private void printSingleBus(String nameOfBus, LinearLayout layout)
    {
        TextView textView = new TextView(this);

        int numRides = listOfBuses.get(nameOfBus);

        textView.setText(nameOfBus + "\t" + Integer.toString(numRides));
        textView.setTextSize(16);
        textView.setPadding(10, 10, 10, 10);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);

        layout.addView(textView);
    }
}
