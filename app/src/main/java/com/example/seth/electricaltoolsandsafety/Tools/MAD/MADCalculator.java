package com.example.seth.electricaltoolsandsafety.Tools.MAD;

import com.example.seth.electricaltoolsandsafety.Utilities.DistanceConversion;

import java.text.DecimalFormat;

/**
 * Performs Minimum Approach Distance Calculations.
 */
public class MADCalculator {

    private static final String FEET = "Feet";
    private static final String METERS = "Meters";

    /**
     * Calculates the Phase-to-Ground minimum approach distance given the kilovolts, elevation,
     * and unit of measure the elevation is in.
     *
     * @param kilovolts for the equation
     * @param elevation for the equation
     * @param elevationUnitOfMeasure either feet or meters
     * @return the MAD in feet or meters
     */
    public static String phaseToGroundMAD(double kilovolts, double elevation,
                                          String elevationUnitOfMeasure){

        double correctionFactor = 0;

        if(elevationUnitOfMeasure.equalsIgnoreCase(FEET)){
            correctionFactor = altitudeCorrectionFactor(elevation, FEET);
        } else if(elevationUnitOfMeasure.equalsIgnoreCase(METERS)){
            correctionFactor = altitudeCorrectionFactor(elevation, METERS);
        }

        double phaseToGroundMAD = 0;

        if(kilovolts <= 0.3){
            phaseToGroundMAD = 0;

        } else if(kilovolts <= 0.75){
            phaseToGroundMAD = 0.31 + 0.02;

        } else if(kilovolts <= 5){
            phaseToGroundMAD = 0.61 + 0.02;

        } else if(kilovolts <= 15){
            phaseToGroundMAD = 0.61 + correctionFactor * 0.04;

        } else if(kilovolts <= 36){
            phaseToGroundMAD = 0.61 + correctionFactor * 0.16;

        } else if(kilovolts <= 46){
            phaseToGroundMAD = 0.61 + correctionFactor * 0.23;

        } else if(kilovolts <= 72.5){
            phaseToGroundMAD = 0.61 + correctionFactor * 0.39;

        } else if(kilovolts <= 121){
            phaseToGroundMAD = 0.3048 * (0.01 + 0) * (kilovolts/Math.sqrt(3)) * 3.5
                    * correctionFactor + 0.31;

        } else if(kilovolts <= 145){
            phaseToGroundMAD = 0.3048 * (0.01 + 0) * (kilovolts/Math.sqrt(3)) * 3.5
                    * correctionFactor + 0.31;

        } else if(kilovolts <= 169){
            phaseToGroundMAD = 0.3048 * (0.01 + 0) * (kilovolts/Math.sqrt(3)) * 3.5
                    * correctionFactor + 0.31;

        } else if(kilovolts <= 242){
            phaseToGroundMAD = 0.3048 * (0.01 + 0) * (kilovolts/Math.sqrt(3)) * 3.5
                    * correctionFactor + 0.31;

        } else if(kilovolts <= 362){
            phaseToGroundMAD = 0.3048 * (0.01 + 0) * (kilovolts/Math.sqrt(3)) * 3.5
                    * correctionFactor + 0.31;

        } else if(kilovolts <= 420){
            phaseToGroundMAD = 0.3048 * (0.01 + 0) * (kilovolts/Math.sqrt(3)) * 3.5
                    * correctionFactor + 0.31;

        } else if(kilovolts <= 550){
            phaseToGroundMAD = 0.3048 * (0.01 + 0) * (kilovolts/Math.sqrt(3)) * 3
                    * correctionFactor + 0.31;

        } else if(kilovolts <= 635){
            phaseToGroundMAD = 0.3048 * (0.01 + 0) * (kilovolts/Math.sqrt(3)) * 2.5
                    * correctionFactor + 0.31;

        } else if(kilovolts <= 800){
            phaseToGroundMAD = 0.3048 * (0.01 + ((kilovolts - 635)/140000))
                    * (kilovolts/Math.sqrt(3)) * 2.5 * correctionFactor + 0.31;
        }

        // Returns the MAD in feet or meters based of the unit of measure given
        if(elevationUnitOfMeasure.equalsIgnoreCase(FEET)){
            return decimalToFeet(DistanceConversion.convertMetersToFeet(phaseToGroundMAD));

        } else {
            return decimalToMeters(phaseToGroundMAD);
        }
    }

    /**
     * Calculates the Phase-to-Phase minimum approach distance given the kilovolts, elevation,
     * and unit of measure the elevation is in.
     *
     * @param kilovolts for the equation
     * @param elevation for the equation
     * @param elevationUnitOfMeasure either feet or meters
     * @return the MAD in feet or meters
     */
    public static String phaseToPhaseMAD(double kilovolts, double elevation,
                                         String elevationUnitOfMeasure){

        double correctionFactor = 0;

        if(elevationUnitOfMeasure.equalsIgnoreCase(FEET)){
            correctionFactor = altitudeCorrectionFactor(elevation, FEET);

        } else if(elevationUnitOfMeasure.equalsIgnoreCase(METERS)){
            correctionFactor = altitudeCorrectionFactor(elevation, METERS);
        }

        double phaseToPhaseMAD = 0;

        if(kilovolts <= 0.3){
            phaseToPhaseMAD = 0;

        } else if(kilovolts <= 0.75){
            phaseToPhaseMAD = 0.31 + 0.02;

        } else if(kilovolts <= 5){
            phaseToPhaseMAD = 0.61 + 0.02;

        } else if(kilovolts <= 15){
            phaseToPhaseMAD = 0.61 + correctionFactor * 0.07;

        } else if(kilovolts <= 36){
            phaseToPhaseMAD = 0.61 + correctionFactor * 0.28;

        } else if(kilovolts <= 46){
            phaseToPhaseMAD = 0.61 + correctionFactor * 0.37;

        } else if(kilovolts <= 72.5){
            phaseToPhaseMAD = 0.61 + correctionFactor * 0.59;

        } else if(kilovolts <= 121){
            phaseToPhaseMAD = 0.3048 * (0.01 + 0) * (kilovolts/Math.sqrt(3)) * (1.35 * 3.5 + 0.45)
                    * correctionFactor + 0.31;

        } else if(kilovolts <= 145){
            phaseToPhaseMAD = 0.3048 * (0.01 + 0) * (kilovolts/Math.sqrt(3)) * (1.35 * 3.5 + 0.45)
                    * correctionFactor + 0.31;

        } else if(kilovolts <= 169){
            phaseToPhaseMAD = 0.3048 * (0.01 + 0) * (kilovolts/Math.sqrt(3)) * (1.35 * 3.5 + 0.45)
                    * correctionFactor + 0.31;

        } else if(kilovolts <= 242){
            phaseToPhaseMAD = 0.3048 * (0.01 + 0) * (kilovolts/Math.sqrt(3)) * (1.35 * 3.5 + 0.45)
                    * correctionFactor + 0.31;

        } else if(kilovolts <= 362){
            phaseToPhaseMAD = 0.3048 * (0.01 + 0) * (kilovolts/Math.sqrt(3)) * (1.35 * 3.5 + 0.45)
                    * correctionFactor + 0.31;

        } else if(kilovolts <= 420){
            phaseToPhaseMAD = 0.3048 * (0.01 + 0) * (kilovolts/Math.sqrt(3)) * (1.35 * 3.5 + 0.45)
                    * correctionFactor + 0.31;

        } else if(kilovolts <= 550){
            phaseToPhaseMAD = 0.3048 * (0.01 + 0) * (kilovolts/Math.sqrt(3)) * (1.35 * 3 + 0.45)
                    * correctionFactor + 0.31;

        } else if(kilovolts <= 630){
            phaseToPhaseMAD = 0.3048 * (0.01 + 0) * (kilovolts/Math.sqrt(3)) * (1.35 * 2.5 + 0.45)
                    * correctionFactor + 0.31;

        } else if(kilovolts <= 800){
            phaseToPhaseMAD = 0.3048 * (0.01 + ((kilovolts - 630)/155000)) * (kilovolts/Math.sqrt(3))
                    * (1.35 * 2.5 + 0.45) * correctionFactor + 0.31;
        }

        // Returns the MAD in feet or meters based of the unit of measure given
        if(elevationUnitOfMeasure.equalsIgnoreCase(FEET)){
            return decimalToFeet(DistanceConversion.convertMetersToFeet(phaseToPhaseMAD));
        } else {
            return decimalToMeters(phaseToPhaseMAD);
        }
    }

    /**
     * Calculates the Altitude Correction Factor for the Minimum Approach Distance Calculator.
     * @param elevation to provide correction factor for
     * @param elevationUnitOfMeasure either feet or meters
     * @return the altitude correction factor for the given elevation
     */
    public static double altitudeCorrectionFactor(double elevation, String elevationUnitOfMeasure){

        double correctionFactor = 1;
        double elevationInMeters = 0;

        // Converts elevation to meters to compute the Altitude Correction Factor
        if(elevationUnitOfMeasure.equalsIgnoreCase(FEET)){
            elevationInMeters = DistanceConversion.convertFeetToMeters(elevation);
        } else if (elevationUnitOfMeasure.equalsIgnoreCase(METERS)){
            elevationInMeters = elevation;
        }

        if(elevationInMeters <= 900){
            correctionFactor = 1;

        } else if(elevationInMeters <= 1200){
            correctionFactor = 1.02;

        } else if(elevationInMeters <= 1500){
            correctionFactor = 1.05;

        } else if(elevationInMeters <= 1800){
            correctionFactor = 1.08;

        } else if(elevationInMeters <= 2100){
            correctionFactor = 1.11;

        } else if(elevationInMeters <= 2400){
            correctionFactor = 1.14;

        } else if(elevationInMeters <= 2700){
            correctionFactor = 1.17;

        } else if(elevationInMeters <= 3000){
            correctionFactor = 1.20;

        } else if(elevationInMeters <= 3600){
            correctionFactor = 1.25;

        } else if(elevationInMeters <= 4200){
            correctionFactor = 1.30;

        } else if(elevationInMeters <= 4800){
            correctionFactor = 1.35;

        } else if(elevationInMeters <= 5400){
            correctionFactor = 1.39;

        } else if(elevationInMeters <= 6000){
            correctionFactor = 1.44;
        }

        return correctionFactor;
    }

    /**
     * Converts a decimal number to a Feet and Inches.
     * @param decimal to convert
     * @return a String representation of the decimal in feet and inches
     */
    public static String decimalToFeet(double decimal){

        double totalInches = decimal * 12;
        int feet = (int) totalInches / 12;
        int inches = (int) totalInches % 12;

        return feet + "\' " + inches + "\"";
    }

    /**
     * Converts a decimal number to meters with two digits.
     * @param decimal to convert
     * @return a String representation of the decimal to meters
     */
    public static String decimalToMeters(double decimal){

        String pattern = "#.##";
        DecimalFormat numberFormat = new DecimalFormat(pattern);

        return numberFormat.format(decimal) + " Meters";
    }
}
