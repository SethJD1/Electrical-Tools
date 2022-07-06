package com.example.seth.electricaltoolsandsafety.Utilities;

/**
 * Converts various pressure units.
 */
public class PressureConversion {

    private static final String PSI = "PSI";
    private static final String TORR = "Torr";
    private static final String PASCAL = "Pascal";

    private static final String[] PRESSURE_UNITS = {PSI, TORR, PASCAL};

    /**
     * Converts a various pressures to another pressure unit.
     * @param fromUnit unit to convert from
     * @param toUnit unit to convert to
     * @param value to convert
     * @return converted value
     */
    public static double convertPressure(String fromUnit, String toUnit, double value){

        if(fromUnit.equals(toUnit)){
            return value;
        } else {
            return convertFromPascals(toUnit, convertToPascals(fromUnit, value));
        }
    }

    /**
     * Converts a pressure unit to pascals
     * @param unit to convert to pascals
     * @param value to convert
     * @return value in pascals
     */
    private static double convertToPascals(String unit, double value){

        final double PSI_CONVERSION_FACTOR = 6894.76;
        final double TORR_CONVERSION_FACTOR = 133.322;

        switch(unit){

            case(PSI):
                return value * PSI_CONVERSION_FACTOR;
            case(TORR):
                return value * TORR_CONVERSION_FACTOR;
            default:
                return value;
        }
    }

    /**
     * Converts a pressure unit from pascals
     * @param unit to convert from pascals
     * @param value to convert
     * @return value converted from pascals.
     */
    private static double convertFromPascals(String unit, double value){

        final double PSI_CONVERSION_FACTOR = 0.000145038;
        final double TORR_CONVERSION_FACTOR = 0.00750062;

        switch(unit){

            case(PSI):
                return value * PSI_CONVERSION_FACTOR;
            case(TORR):
                return value * TORR_CONVERSION_FACTOR;

            default:
                return value;
        }
    }

    /**
     * Returns a applicable pressure units.
     * @return array of pressure units.
     */
    public static String[] getPressureUnits(){
        return PRESSURE_UNITS;
    }

}
