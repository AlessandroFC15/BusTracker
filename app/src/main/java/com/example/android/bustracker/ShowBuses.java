package com.example.android.bustracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ShowBuses extends AppCompatActivity {

    private ArrayList<String> listOfBuses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_buses);

        Intent intent = getIntent();

        listOfBuses = (ArrayList) intent.getSerializableExtra(MainActivity.LIST_BUSES);

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

        for (String nameOfBus : listOfBuses)
        {
            printSingleBus(nameOfBus, layout);
        }

    }

    private void printSingleBus(String nameOfBus, LinearLayout layout)
    {
        TextView textView = new TextView(this);

        textView.setText(nameOfBus);
        textView.setTextSize(16);
        textView.setPadding(10, 10, 10, 11);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);

        layout.addView(textView);
    }
}
