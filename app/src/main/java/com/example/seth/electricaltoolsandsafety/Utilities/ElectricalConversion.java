package com.example.seth.electricaltoolsandsafety.Utilities;

/**
 * Converts an electrical unit another unit.
 */
public class ElectricalConversion {

    private static final String GIGA = "Giga";
    private static final String MEGA = "Mega";
    private static final String KILO = "Kilo";
    private static final String MILLI = "Milli";
    private static final String MICRO = "Micro";
    private static final String NANO = "Nano";

    private static final double giga = 1000000000;
    private static final double mega = 1000000;
    private static final double kilo = 1000;
    private static final double base = 1;
    private static final double milli = .001;
    private static final double micro = .000001;
    private static final double nano = .000000001;

    /**
     * Converts a standard electrical unit to the base value.
     * @param unit - unit to convert to the base value
     * @param value - value to convert
     * @return - the base value for the unit
     */
    public static double convertToBase(String unit, double value){

        double baseUnit = 0;

        switch(unit){
            case GIGA:
                baseUnit = value * giga;
                break;
            case MEGA:
                baseUnit = value * mega;
                break;
            case KILO:
                baseUnit = value * kilo;
                break;
            case MILLI:
                baseUnit = value * milli;
                break;
            case MICRO:
                baseUnit = value * micro;
                break;
            case NANO:
                baseUnit = value * nano;
                break;
            default:
                baseUnit = value * base;
                break;
        }

        return baseUnit;
    }

    /**
     * Converts a base electrical unit to a specified unit.
     * @param unit - the unit to convert to
     * @param value - the unit value to convert
     * @return returns an electrical unit value.
     */
    public static double convertFromBase(String unit, double value){

        double baseUnit = 0;

        switch(unit){
            case GIGA:
                baseUnit = value / giga;
                break;
            case MEGA:
                baseUnit = value / mega;
                break;
            case KILO:
                baseUnit = value / kilo;
                break;
            case MILLI:
                baseUnit = value / milli;
                break;
            case MICRO:
                baseUnit = value / micro;
                break;
            case NANO:
                baseUnit = value / nano;
                break;
            default:
                baseUnit = value / base;
                break;
        }

        return baseUnit;
    }

    /**
     * Converts a standard electrical unit to another unit.
     * @param fromUnit to change from
     * @param toUnit to change to
     * @param value to convert
     * @return converted electrical subunit value
     */
    public static double convertUnits(String fromUnit, String toUnit, double value){

        double valueToConvert;
        valueToConvert = convertToBase(fromUnit, value);
        return convertFromBase(toUnit, valueToConvert);
    }

    /**
     * Converts radians to degrees
     * @param radians to convert
     * @return degrees
     */
    public static double convertToDegreesFromRadians(double radians){
        return Math.toDegrees(radians);
    }
}
