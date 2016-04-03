package com.example.android.bustracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
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
        setTitle("Home");

        configureMenu();
    }

    public void onResume()
    {
        // Always call the superclass method first
        super.onResume();

        // Set home as checked. Home is the first
        drawerList.setItemChecked(HOME_INDEX, true);
    }

    /**
     * This method gets called when the Button Add is pressed and proceeds to add a bus to the app.
     *
     * @param view
     */

    public void addBus(View view) {
        String nameOfBus = getNameInput();

        if (nameOfBus.length() > 0) {
            if (!busData.isBusRegistered(nameOfBus)) {
                busData.addBus(nameOfBus);

                clearNameInputField();

                makeToast("Bus successfully added!");
            } else {
                makeToast("Bus already registered");
            }
        } else {
            makeToast("Enter a valid name!");
        }
    }

    // Helper Function

    private String getNameInput() {
        EditText input = (EditText) findViewById(R.id.nameOfBus);
        return input.getText().toString().toUpperCase();
    }

    private void clearNameInputField() {
        EditText input = (EditText) findViewById(R.id.nameOfBus);
        input.setText("");
    }

    private void makeToast(String text) {
        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        toast.show();
    }

    // Part of the code related to the drawer menu

    private DrawerLayout drawerLayout;
    // String array to hold the choices in the drawer menu
    private String[] allActivities;
    private ListView drawerList;
    private static final int HOME_INDEX = 0;
    private static final int SHOW_BUSES_INDEX = 1;
    private static final int ADD_RIDE_INDEX = 2;

    private void configureMenu()
    {
        configureDrawerLayout();

        configureListView();
    }

    private void configureDrawerLayout()
    {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Set a custom shadow that overlays the main content when the drawer opens
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        // Set the correct listener
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View view, float v) {

            }

            @Override
            public void onDrawerOpened(View view) {

            }

            @Override
            public void onDrawerClosed(View view) {

            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });
    }

    private void configureListView()
    {
        drawerList = (ListView) findViewById(R.id.left_drawer);

        // Get string array of all activities to be shown in the drawer menu
        allActivities = getResources().getStringArray(R.array.all_activities);

        drawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, allActivities));
        drawerList.setItemChecked(0, true);

        // Set the list's click listener
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);

            changeActivity(position);
        }
    }

    private void selectItem(int position) {
        // Highlight the selected item and close the drawer
        drawerList.setItemChecked(position, true);
        drawerLayout.closeDrawer(drawerList);
    }

    private void changeActivity(int position)
    {
        String input = allActivities[position];

        if (input.equals("Home"))
        {
            makeToast("Home already selected!");
        } else if (input.equals("Show Buses"))
        {
            startActivity(new Intent(this, ShowBuses.class));
        } else if (input.equals("Add Ride"))
        {
            startActivity(new Intent(this, AddRide.class));
        } else
        {
            makeToast("Option not configured yet!");
        }
    }



}
