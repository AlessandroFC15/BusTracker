package com.example.android.bustracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Alessandro on 27/03/2016.
 */
public class BusData extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "BusData";
    private static final int DATABASE_VERSION = 1;

    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String REAL_TYPE = " REAL";

    private static final String COMMA_SEP = ",";

    /* Table of Buses
     * ID | Name | TimesTaken | TotalTime
     */

    public static final String TABLE_NAME_BUSES = "buses";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name_bus";
    public static final String COLUMN_TIMES = "times_taken";
    public static final String COLUMN_DURATION = "total_duration";
    private static final String CREATE_TABLE_BUSES =
            "CREATE TABLE " + TABLE_NAME_BUSES + " (" +
                    COLUMN_ID + INT_TYPE + " PRIMARY KEY," +
                    COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    COLUMN_TIMES + INT_TYPE + COMMA_SEP +
                    COLUMN_DURATION + REAL_TYPE + " )";

    private static final String DELETE_TABLE_BUSES =
            "DROP TABLE IF EXISTS " + TABLE_NAME_BUSES;

    /* Table of Rides
     * ID | Duration | BeginTime | EndTime
     */

    public static final String TABLE_NAME_RIDES = "rides";
    public static final String COLUMN_DURATION_RIDE = "durationOfRide";
    // public static final String COLUMN_BEGIN_TIME = "beginTime";
    // public static final String COLUMN_END_TIME = "endTime";
    private static final String CREATE_TABLE_RIDES =
            "CREATE TABLE " + TABLE_NAME_RIDES + " (" +
                    COLUMN_ID + INT_TYPE + " PRIMARY KEY," +
                    COLUMN_DURATION_RIDE + REAL_TYPE + " )";

    private static final String DELETE_TABLE_RIDES =
            "DROP TABLE IF EXISTS " + TABLE_NAME_RIDES;


    BusData(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_BUSES);
        db.execSQL(CREATE_TABLE_RIDES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(DELETE_TABLE_BUSES);
        db.execSQL(DELETE_TABLE_RIDES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void addBus(String nameOfBus) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, nameOfBus);
        values.put(COLUMN_TIMES, 0);
        values.put(COLUMN_DURATION, 0);

        // Insert the new row, returning the primary key value of the new row
        db.insert(TABLE_NAME_BUSES, "null", values);
    }

    public ArrayList<String> getAllBusesRegistered()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                COLUMN_NAME
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                COLUMN_NAME + " DESC";

        Cursor cursor = db.query(
                TABLE_NAME_BUSES,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        ArrayList<String> allNames = new ArrayList<>();

        try {
            while (cursor.moveToNext()) {
                String nameOfClass = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));

                allNames.add(nameOfClass);
            }
        } finally {
            cursor.close();
        }

        return allNames;
    }

    public boolean addRide(String nameOfBus)
    {
        if (isBusRegistered(nameOfBus))
        {
            // listOfBuses.put(nameOfBus, listOfBuses.get(nameOfBus) + 1);
            return true;
        } else
        {
            return false;
        }
    }

    public boolean isTableBusesEmpty() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME_BUSES, null);
        if (cursor != null) {
            cursor.moveToFirst();                       // Always one row returned.
            return cursor.getInt(0) == 0;              // Zero count means empty table.
        }

        return true;
    }

    public boolean isBusRegistered(String nameOfBus)
    {
        ArrayList<String> allBuses = getAllBusesRegistered();

        return allBuses.indexOf(nameOfBus) != -1;
    }
}
