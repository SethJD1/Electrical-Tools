package com.example.seth.electricaltoolsandsafety.Utilities;

/**
 * Various electrical units and quantity arrays.
 */
public class ElectricalProperties {

    private static final String GIGA = "Giga";
    private static final String MEGA = "Mega";
    private static final String KILO = "Kilo";
    private static final String BASE = "Base";
    private static final String MILLI = "Milli";
    private static final String MICRO = "Micro";
    private static final String NANO = "Nano";

    private static final String[] ELECTRICAL_UNITS = {GIGA, MEGA, KILO, BASE, MILLI, MICRO, NANO};

    private static final String AMPS = "Amps";
    private static final String OHMS = "Ohms";
    private static final String WATTS = "Watts";
    private static final String VOLTS = "Volts";

    private static final String[] ELECTRICAL_QUANTITIES = {WATTS, VOLTS, AMPS, OHMS};

    private static final String VA = "VA";
    private static final String VAR = "VAR";
    private static final String POWER_FACTOR = "Power Factor";
    private static final String ANGLE_Y = "Angle Y";
    private static final String ANGLE_THETA = "Angle Theta";

    private static final String[] POWER_FACTOR_QUANTITIES = {VA, VAR, WATTS, POWER_FACTOR, ANGLE_THETA, ANGLE_Y,};

    private static final String[] POWER_FACTOR_QUANTITIES_MINUS_ANGLES = {VA, VAR, WATTS};

    private static final String[] POWER_FACTOR_UNITS = {POWER_FACTOR, ANGLE_THETA, ANGLE_Y};

    /**
     * Returns a list of standard electrical subunits
     * @return an array of electrical subunits
     */
    public static String[] getElectricalUnits(){
        return ELECTRICAL_UNITS;
    }

    /**
     * Returns a list of electrical quantities
     * @return an array of electrical quantities
     */
    public static String[] getElectricalQuantities(){
        return ELECTRICAL_QUANTITIES;
    }

    /**
     * Returns a list of power factor quantities
     * @return an array of power factor quantities
     */
    public static String[] getPowerFactorQuantities(){
        return POWER_FACTOR_QUANTITIES;
    }

    /**
     * Returns a list of power factor unit
     * @return an array of power factor units
     */
    public static String[] getPowerFactorUnits(){
        return POWER_FACTOR_UNITS;
    }

    /**
     * Returns a list of power factor units without the angles
     * @return an array of power factor unit without angles
     */
    public static String[] getPowerFactorUnitMinusAngles(){
        return POWER_FACTOR_QUANTITIES_MINUS_ANGLES;
    }
}
