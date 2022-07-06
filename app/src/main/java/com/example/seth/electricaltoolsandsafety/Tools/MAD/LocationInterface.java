package com.example.seth.electricaltoolsandsafety.Tools.MAD;

/**
 * Facilitates communication between the LocationData and requesting classes.
 */
public interface LocationInterface {

    void onLocationChange(double elevation);

    void onLocationChange(double longitude, double elevation);

    void onLocationChange(double latitude, double longitude, double elevation);

}
