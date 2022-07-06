package com.example.seth.electricaltoolsandsafety.Utilities;

/**
 * Converts various distance units of measure.
 */
public class DistanceConversion {

    private static final String MILES = "Miles";
    private static final String YARDS = "Yards";
    private static final String FEET = "Feet";
    private static final String INCHES = "Inches";
    private static final String KILOMETERS = "Kilometers";
    private static final String METERS = "Meters";
    private static final String CENTIMETERS = "Centimeters";
    private static final String MILLIMETERS = "Millimeters";

    private static final String[] DISTANCE_UNITS = {MILES, YARDS, FEET, INCHES, KILOMETERS,
            METERS, CENTIMETERS, MILLIMETERS};

    private static final double CONVERSION_FACTOR = 3.28084;

    /**
     * Converts a distance to another unit
     * @param fromUnit unit to convert from
     * @param toUnit unit to convert to
     * @param value to convert
     * @return converted value
     */
    public static double convertDistance(String fromUnit, String toUnit, double value){

        if(fromUnit.equals(toUnit)){
            return value;
        } else {
            return convertFromMeters(toUnit, convertToMeters(fromUnit, value));
        }
    }

    /**
     * Returns a list of distance units that can be converted.
     * @return array of distance units
     */
    public static String[] getDistanceUnits(){
        return DISTANCE_UNITS;
    }

    /**
     * Converts a value to meters.
     * @param unit to convert to meters
     * @param value to convert
     * @return converted value in meters
     */
    private static double convertToMeters(String unit, double value){

        final double MILE_CONVERSION_FACTOR = 1609.344;
        final double YARD_CONVERSION_FACTOR = 0.9144;
        final double INCH_CONVERSION_FACTOR = 0.0254;
        final double FOOT_CONVERSION_FACTOR = 0.3048;
        final double KILOMETER_CONVERSION_FACTOR = 1000;
        final double CENTIMETER_CONVERSION_FACTOR = 0.01;
        final double MILLIMETERS_CONVERSION_FACTOR = 0.001;

        switch(unit){

            case(MILES):
                return value * MILE_CONVERSION_FACTOR;
            case(YARDS):
                return value * YARD_CONVERSION_FACTOR;
            case(FEET):
                return value * FOOT_CONVERSION_FACTOR;
            case(INCHES):
                return value * INCH_CONVERSION_FACTOR;
            case(KILOMETERS):
                return value * KILOMETER_CONVERSION_FACTOR;
            case(CENTIMETERS):
                return value * CENTIMETER_CONVERSION_FACTOR;
            case(MILLIMETERS):
                return value * MILLIMETERS_CONVERSION_FACTOR;
            default:
                return value;
        }
    }

    /**
     * Converts a value from meters
     * @param unit to convert from meters
     * @param value to convert
     * @return converted value
     */
    private static double convertFromMeters(String unit, double value){

        final double MILE_CONVERSION_FACTOR = 0.000621371;
        final double YARD_CONVERSION_FACTOR = 1.09361;
        final double INCH_CONVERSION_FACTOR = 39.3701;
        final double FOOT_CONVERSION_FACTOR = 3.28084;
        final double KILOMETER_CONVERSION_FACTOR = 0.001;
        final double CENTIMETER_CONVERSION_FACTOR = 100;
        final double MILLIMETERS_CONVERSION_FACTOR = 1000;

        switch(unit){

            case(MILES):
                return value * MILE_CONVERSION_FACTOR;
            case(YARDS):
                return value * YARD_CONVERSION_FACTOR;
            case(FEET):
                return value * FOOT_CONVERSION_FACTOR;
            case(INCHES):
                return value * INCH_CONVERSION_FACTOR;
            case(KILOMETERS):
                return value * KILOMETER_CONVERSION_FACTOR;
            case(CENTIMETERS):
                return value * CENTIMETER_CONVERSION_FACTOR;
            case(MILLIMETERS):
                return value * MILLIMETERS_CONVERSION_FACTOR;
            default:
                return value;
        }
    }

    /**
     * Converts Meters to Feet.
     * @param meters to convert
     * @return distance in feet
     */
    public static double convertMetersToFeet(double meters){
        return meters * CONVERSION_FACTOR;
    }

    /**
     * Converts Feet to Meters
     * @param feet to convert
     * @return distance in meters
     */
    public static double convertFeetToMeters(double feet){
        return feet / CONVERSION_FACTOR;
    }
}
