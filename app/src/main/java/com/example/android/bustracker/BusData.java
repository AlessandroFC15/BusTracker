package com.example.android.bustracker;

import java.util.HashMap;

/**
 * Created by Alessandro on 27/03/2016.
 */
public class BusData {

    // The following HashMap maps a string nameOfBus to an int
    // corresponding to the number of rides
    private HashMap<String, Integer> listOfBuses = new HashMap<>();

    private static final BusData busData = new BusData();
    public static BusData getInstance() {return busData;}

    public HashMap<String, Integer> getListOfBuses()
    {
        return listOfBuses;
    }

    public void addBus(String nameOfBus)
    {
        listOfBuses.put(nameOfBus, 0);
    }

    public boolean addRide(String nameOfBus)
    {
        if (isBusRegistered(nameOfBus))
        {
            listOfBuses.put(nameOfBus, listOfBuses.get(nameOfBus) + 1);
            return true;
        } else
        {
            return false;
        }
    }

    public boolean isBusRegistered(String nameOfBus)
    {
        return listOfBuses.containsKey(nameOfBus);
    }
}
