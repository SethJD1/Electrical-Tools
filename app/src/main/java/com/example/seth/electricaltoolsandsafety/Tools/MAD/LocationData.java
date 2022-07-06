package com.example.seth.electricaltoolsandsafety.Tools.MAD;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

/**
 * Request permission and acquires user location data from the internal phone GPS.
 */
public class LocationData {

    private Activity activity;

    private LocationManager locationManager;
    private LocationListener locationListener;
    private final int PERMISSION_REQUEST_CODE = 1;
    private final int GPS_UPDATE_TIME = 10;
    private final int GPS_UPDATE_DISTANCE = 100;
    private Criteria criteria;

    private double latitude; // Included for future use
    private double longitude; // included for future use
    private double elevation;

    private LocationInterface locationInterface;

    /**
     * Default Constructor
     */
    public LocationData(Activity activity, LocationInterface locationInterface){

        this.activity = activity;
        this.locationInterface = locationInterface;

        latitude = 0.0;
        longitude = 0.0;
        elevation = 0.0;
    }

    /**
     * Returns the current location latitude.
     *
     * * Included for future use.
     *
     * @return current location latitude.
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Returns the current location longitude.
     *
     * * Included for future use.
     *
     * @return current location longitude.
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Retunrs the current location elevation.
     * @return currnt location elevation.
     */
    public double getElevation() {
        return elevation;
    }

    /**
     * Checks if the User has permitted the use of Location data. If permission has not been granted,
     * permission is requested.
     */
    public boolean checkLocationDataPermission() {

        // Verify if Location Data is permitted
        if (ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Request permission if not permitted
            return requestLocationDataPermission();

        } else {
            enableGPSFunctionality();
            setupLocationListener();
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, GPS_UPDATE_TIME, GPS_UPDATE_DISTANCE, locationListener);
            return true;
        }
    }

    /**
     * Request permission from the user to enable the use of Location Data.
     * @return true if permission has been granted, false otherwise.
     */
    private boolean requestLocationDataPermission() {

        // If Access is not permitted, ask the user for permission
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);

        int result = ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);

        // Enable and Display GPS option if permission is granted
        if (result == PackageManager.PERMISSION_GRANTED) {

            enableGPSFunctionality();
            setupLocationListener();
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, GPS_UPDATE_TIME, GPS_UPDATE_DISTANCE, locationListener);

            return true;

        } else {
            return false;
        }
    }

    /**
     * Modifies the GPS Switch visibility and function based on whether Location data is enabled.
     */
    private void enableGPSFunctionality() {

        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

        // Set Accuracy to Fine
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
    }

    /**
     * Sets up the Location Listener to update the latitude, longitude and elevation.
     */
    private void setupLocationListener(){

        locationListener = new LocationListener() {

            /**
             * Updates locational data when the user changes location.
             * @param location
             */
            @Override
            public void onLocationChanged(Location location) {

                latitude = location.getLatitude(); // Include for future use
                longitude = location.getLongitude(); // Include for future use
                elevation = location.getAltitude();

                locationInterface.onLocationChange(elevation);
                locationInterface.onLocationChange(longitude, latitude); // Include for future use
                locationInterface.onLocationChange(longitude, latitude, elevation); // Include for future use
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(String provider) {}

            @Override
            public void onProviderDisabled(String provider) {}
        };
    }
}
