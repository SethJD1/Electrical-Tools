package com.example.seth.electricaltoolsandsafety.Tools.PowerFactor;

import com.example.seth.electricaltoolsandsafety.Utilities.ElectricalConversion;

/**
 * Calculates and converts Power Factor values.
 */
public class PowerFactorCalculator {

    private static final String VA = "VA";
    private static final String WATTS = "Watts";
    private static final String VAR = "VAR";
    private static final String POWER_FACTOR = "Power Factor";
    private static final String ANGLE_THETA = "Angle Theta";
    private static final String ANGLE_Y = "Angle Y";
    private static final String ANGLE = "Angle";

    private static final String[] ANGLES = {POWER_FACTOR, ANGLE_THETA, ANGLE_Y};
    private static final String[] SIDE = {VA, WATTS, VAR};

    private static final double angleC = 90;

    private static double powerFactor;
    private static double angleTheta;
    private static double angleY;
    private static double va;
    private static double watts;
    private static double var;

    /**
     * Calculates the values for VA, VAR, Watts and Angles based off the answer unit.
     *
     * @param unitOne subunit for the first value
     * @param quantityOne quantity for the first value
     * @param valueOne first quantity value
     * @param unitTwo subunit for the second value
     * @param quantityTwo quantity for the second value
     * @param valueTwo second quantity value
     * @param answerUnit unit for the solved for quantity
     * @param answerQuantity quantity to solve for
     * @return answer
     */
    public static double powerFactorCalculator(String unitOne, String quantityOne, double valueOne,
                                               String unitTwo, String quantityTwo, double valueTwo,
                                               String answerUnit, String answerQuantity){

        double baseValueOne = ElectricalConversion.convertToBase(unitOne, valueOne);
        double baseValueTwo = ElectricalConversion.convertToBase(unitTwo, valueTwo);

        if(isSideCalculation(quantityOne, quantityTwo)){

            solveForSides(quantityOne, baseValueOne, quantityTwo, baseValueTwo);

        } else {

            if(isAngleCalculation(quantityOne)){

                solveMixedVariables(quantityOne, baseValueOne, quantityTwo, baseValueTwo);

            } else if(isAngleCalculation(quantityTwo)){

                solveMixedVariables(quantityTwo, baseValueTwo, quantityOne, baseValueOne);
            }
        }

        return getAnswerQuantity(answerQuantity, answerUnit);
    }

    /**
     * Returns the specific answer based on the unit and quantity.
     * @param answerQuantity to convert
     * @param answerUnit to convert
     * @return designated value for the unit and quantity
     */
    private static double getAnswerQuantity(String answerQuantity, String answerUnit){

        switch(answerQuantity){

            case VA:
                return ElectricalConversion.convertFromBase(answerUnit, va);

            case VAR:
                return ElectricalConversion.convertFromBase(answerUnit, var);

            case WATTS:
                return ElectricalConversion.convertFromBase(answerUnit, watts);

            case ANGLE:

                if(answerUnit.equals(POWER_FACTOR)){
                    return powerFactor;
                } else if(answerUnit.equals(ANGLE_THETA)){
                    return ElectricalConversion.convertToDegreesFromRadians(angleTheta);
                } else if(answerUnit.equals(ANGLE_Y)){
                    return ElectricalConversion.convertToDegreesFromRadians(angleY);
                }

            default:
                return 0.0;
        }
    }

    /**
     * Check whether the calculation is an angle calculation.
     * @param quantity for the equation
     */
    private static boolean isAngleCalculation(String quantity){

        for(int index = 0; index < ANGLES.length; index ++){

            if(quantity.equals(ANGLES[index])){
                return true;
            }
        }

        return false;
    }

    /**
     * Checks whether the calculation is a triangle side calculation.
     * @param quantityOne for the equation
     * @param quantityTwo for the equation
     */
    private static boolean isSideCalculation(String quantityOne, String quantityTwo){

        int countSides = 0;

        for(int index = 0; index < SIDE.length; index ++){

            if(quantityOne.equals(SIDE[index]) || quantityTwo.equals(SIDE[index])){
                countSides ++;
            }
        }

        return countSides == 2;
    }

    /**
     * Solves for all the angles based one angle value.
     * @param angle name
     * @param value value of the angle
     */
    private static void solveForAngles(String angle, double value){

        if(angle.equals(ANGLE_THETA)){

            angleTheta = value;
            angleY = angleC - angleTheta;
            powerFactor = Math.cos(Math.toDegrees(angleTheta));

        } else if(angle.equals(ANGLE_Y)){

            angleY = value;
            angleTheta = angleC - angleY;
            powerFactor = Math.cos(Math.toDegrees(angleTheta));

        } else if(angle.equals(POWER_FACTOR)){

            powerFactor = value;
            angleTheta = Math.acos(powerFactor);
            angleY = angleC - angleTheta;
        }
    }

    /**
     * Solves for all the angles after sides calculation have been completed.
     */
    private static void solveForAngles(){

        angleTheta = Math.asin(var / va);
        angleY = Math.acos(var / va);
        powerFactor = watts / va;
    }

    /**
     * Solves for each side of the triangle.
     * @param sideOne designation
     * @param valueOne for side one
     * @param sideTwo designation
     * @param valueTwo for side two
     */
    private static void solveForSides(String sideOne, double valueOne, String sideTwo, double valueTwo){

        if(sideOne.equals(VA)){

            va = valueOne;

            if(sideTwo.equals(WATTS)){

                watts = valueTwo;
                var = Math.sqrt(Math.pow(va, 2) - Math.pow(watts, 2));

            } else if(sideTwo.equals(VAR)){

                var = valueTwo;
                watts = Math.sqrt(Math.pow(va, 2) - Math.pow(var, 2));
            }

        } else if(sideOne.equals(WATTS)){

            watts = valueOne;

            if(sideTwo.equals(VA)){

                va = valueTwo;
                var = Math.sqrt(Math.pow(va, 2) - Math.pow(watts, 2));

            } else if(sideTwo.equals(VAR)){

                var = valueTwo;
                va = Math.sqrt(Math.pow(watts, 2) + Math.pow(var, 2));
            }

        } else if(sideOne.equals(VAR)){

            var = valueOne;

            if(sideTwo.equals(VA)){

                va = valueTwo;
                watts = Math.sqrt(Math.pow(va, 2) - Math.pow(var, 2));

            } else if(sideTwo.equals(WATTS)){

                watts = valueTwo;
                va = Math.sqrt(Math.pow(watts, 2) + Math.pow(var, 2));
            }
        }

        solveForAngles();
    }

    /**
     * Solves a mixed triangle equation that includes a side and an angle.
     * @param angle designation
     * @param angleValue for the specified angle
     * @param side designation
     * @param sideValue for the specified side
     */
    private static void solveMixedVariables(String angle, double angleValue, String side, double sideValue){

        solveForAngles(angle, angleValue);

        double angleThetaRadians = Math.toRadians(angleTheta);

        if(side.equals(VA)){

            va = sideValue;
            watts = Math.cos(angleThetaRadians) * sideValue;
            var = Math.sin(angleThetaRadians) * sideValue;

        } else if(side.equals(VAR)){

            var = sideValue;
            watts = Math.tan(angleThetaRadians) * sideValue;
            va = Math.sin(angleThetaRadians) * sideValue;

        } else if(side.equals(WATTS)){

            watts = sideValue;
            var = Math.tan(angleThetaRadians) * sideValue;
            va = sideValue / Math.cos(angleThetaRadians);
        }
    }
}























