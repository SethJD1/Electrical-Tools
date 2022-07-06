package com.example.seth.electricaltoolsandsafety.Tools.Conversions;

import com.example.seth.electricaltoolsandsafety.Utilities.DistanceConversion;
import com.example.seth.electricaltoolsandsafety.Utilities.ElectricalConversion;
import com.example.seth.electricaltoolsandsafety.Utilities.PressureConversion;

/**
 * Coverts various Distance, Eletrical and Pressure units.
 */
public class ConversionsCalculator {

    private static final String ELECTRICAL = "Electrical";
    private static final String DISTANCE = "Distance";
    private static final String PRESSURE = "Pressure";

    private static final String[] TYPES = {ELECTRICAL, DISTANCE, PRESSURE};

    /**
     * Converts electrical, distance and pressure units given a two units and a value.
     *
     * @param conversionType to conduct
     * @param fromUnit to convert from
     * @param toUnit to convert to
     * @param amount to convert
     * @return converted value
     */
    public static double convert(String conversionType, String fromUnit, String toUnit, double amount){

        switch(conversionType){

            case(ELECTRICAL):
                return ElectricalConversion.convertUnits(fromUnit, toUnit, amount);

            case(DISTANCE):
                return DistanceConversion.convertDistance(fromUnit, toUnit, amount);

            case(PRESSURE):
                return PressureConversion.convertPressure(fromUnit, toUnit, amount);

            default:
                return 0;
        }
    }

    /**
     * Returns a list of conversion types.
     * @return conversion types
     */
    public static String[] getConversionTypes(){
        return TYPES;
    }
}
