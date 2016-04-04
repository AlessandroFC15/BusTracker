package com.example.android.bustracker;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShowBuses extends AppCompatActivity {

    // Attributes
    private BusData busData = new BusData(this);
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_buses);

        setTitle("Show Buses");

        if (busData.isTableBusesEmpty()) {
            printWarning();
        } else {
            printAllBuses();
        }
    }

    private void printWarning()
    {
        TextView warning = new TextView(this);

        warning.setText(R.string.no_buses_registered);
        warning.setTextSize(16);
        warning.setGravity(Gravity.CENTER_HORIZONTAL);

        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mainShowBuses);

        // Add the warning just after the heading.
        mainLayout.addView(warning, 1);
    }

    private void printAllBuses()
    {
        SQLiteDatabase db = busData.getReadableDatabase();

        String[] projection = {
                busData.COLUMN_NAME,
                busData.COLUMN_TIMES,
                busData.COLUMN_DURATION};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                busData.COLUMN_TIMES + " DESC";

        Cursor cursor = db.query(
                busData.TABLE_NAME_BUSES,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );


        try {
            while (cursor.moveToNext()) {
                String nameOfBus = cursor.getString(cursor.getColumnIndex(busData.COLUMN_NAME));

                int timesTaken = cursor.getInt(cursor.getColumnIndex(busData.COLUMN_TIMES));

                double duration = cursor.getDouble(cursor.getColumnIndex(busData.COLUMN_DURATION));

                printSingleBus(nameOfBus, timesTaken, duration);
            }
        } finally {
            cursor.close();
        }
    }

    private void printSingleBus(String nameOfBus, int timesTaken, double duration)
    {
        LinearLayout layout = (LinearLayout) findViewById(R.id.mainShowBuses);

        TextView textView = new TextView(this);

        textView.setText(nameOfBus + "\t" + Integer.toString(timesTaken)
                + "\t" + Double.toString(duration));
        textView.setTextSize(16);
        textView.setPadding(10, 10, 10, 10);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);

        layout.addView(textView);
    }
}
