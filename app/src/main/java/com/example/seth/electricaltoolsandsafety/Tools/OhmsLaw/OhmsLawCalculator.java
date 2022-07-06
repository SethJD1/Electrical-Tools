package com.example.seth.electricaltoolsandsafety.Tools.OhmsLaw;

import com.example.seth.electricaltoolsandsafety.Utilities.ElectricalConversion;

/**
 * Converts and calculates common electrical values.
 */
public class OhmsLawCalculator {

    private static final String AMPS = "Amps";
    private static final String OHMS = "Ohms";
    private static final String WATTS = "Watts";
    private static final String VOLTS = "Volts";

    /**
     * Calculates the values of volts, watts, amps and ohms based off the answer unit.
     *
     * @param topUnit - electrical subunit for the first value
     * @param topQuantity - electrical quantity for the first value
     * @param topValue - first electrical quantity value
     * @param bottomUnit - electrical subunit for the second value
     * @param bottomQuantity - electrical quantity for the second value
     * @param bottomValue - second electrical quantity value
     * @param answerUnit - unit for the solved for quantity
     * @param answerQuantity - quantity to solve for
     * @return answer
     */
    public static double ohmsLawCalculator(String topUnit, String topQuantity, double topValue,
                                           String bottomUnit, String bottomQuantity, double bottomValue, String answerUnit,
                                           String answerQuantity){

        double baseTopValue = ElectricalConversion.convertToBase(topUnit, topValue);
        double baseBottomValue = ElectricalConversion.convertToBase(bottomUnit, bottomValue);
        double solution = 0;

        switch(answerQuantity){

            case WATTS: // Calculation to solve for Watts

                if(topQuantity.equals(VOLTS)){

                    if(bottomQuantity.equals(OHMS)){
                        solution = Math.pow(baseTopValue, 2) / baseBottomValue;
                    } else if(bottomQuantity.equals(AMPS)){
                        solution = baseTopValue * baseBottomValue;
                    }

                } if (topQuantity.equals(AMPS)){

                    if(bottomQuantity.equals(VOLTS)){
                        solution = baseBottomValue * baseTopValue;
                    } else if(bottomQuantity.equals(OHMS)){
                        solution = Math.pow(baseTopValue, 2) * baseBottomValue;
                    }

                } if (topQuantity.equals(OHMS)) {

                    if(bottomQuantity.equals(VOLTS)){
                        solution = Math.pow(baseBottomValue, 2) / baseTopValue;
                    } else if(bottomQuantity.equals(AMPS)){
                        solution = baseTopValue * Math.pow(baseBottomValue, 2);
                    }
                }

                break;

            case VOLTS: // Calculations to solve for Volts

                if(topQuantity.equals(WATTS)){

                    if(bottomQuantity.equals(OHMS)){
                        solution = Math.sqrt(baseTopValue * baseBottomValue);
                    } else if(bottomQuantity.equals(AMPS)){
                        solution = baseTopValue / baseBottomValue;
                    }

                } else if (topQuantity.equals(AMPS)){

                    if(bottomQuantity.equals(WATTS)){
                        solution = baseBottomValue / baseTopValue;
                    } else if(bottomQuantity.equals(OHMS)){
                        solution = baseTopValue * baseBottomValue;
                    }

                } else if (topQuantity.equals(OHMS)) {

                    if(bottomQuantity.equals(WATTS)){
                        solution = Math.sqrt(baseTopValue * baseBottomValue);
                    } else if(bottomQuantity.equals(AMPS)){
                        solution = baseTopValue * baseBottomValue;
                    }
                }

                break;

            case AMPS: // Calculations to solve for Amps

                if(topQuantity.equals(VOLTS)){

                    if(bottomQuantity.equals(OHMS)){
                        solution = baseTopValue / baseBottomValue;
                    } else if(bottomQuantity.equals(WATTS)){
                        solution = baseBottomValue / baseTopValue;
                    }

                } else if (topQuantity.equals(WATTS)){

                    if(bottomQuantity.equals(VOLTS)){
                        solution = baseTopValue / baseBottomValue;
                    } else if(bottomQuantity.equals(OHMS)){
                        solution = Math.sqrt(baseTopValue / baseBottomValue);
                    }

                } else if (topQuantity.equals(OHMS)) {

                    if(bottomQuantity.equals(VOLTS)){
                        solution = baseBottomValue / baseTopValue;
                    } else if(bottomQuantity.equals(WATTS)){
                        solution = Math.sqrt(baseBottomValue / baseTopValue);
                    }
                }

                break;

            case OHMS: // Calculations to solve for Ohms

                if(topQuantity.equals(VOLTS)){

                    if(bottomQuantity.equals(WATTS)){
                        solution = Math.pow(baseTopValue, 2) / baseBottomValue;
                    } else if(bottomQuantity.equals(AMPS)){
                        solution = baseTopValue / baseBottomValue;
                    }

                } else if (topQuantity.equals(AMPS)){

                    if(bottomQuantity.equals(VOLTS)){
                        solution = baseBottomValue / baseTopValue;
                    } else if(bottomQuantity.equals(WATTS)){
                        solution = baseBottomValue / Math.pow(baseTopValue, 2);
                    }

                } else if (topQuantity.equals(WATTS)) {

                    if(bottomQuantity.equals(VOLTS)){
                        solution = Math.pow(baseBottomValue, 2) / baseTopValue;
                    } else if(bottomQuantity.equals(AMPS)){
                        solution = baseTopValue / Math.pow(baseBottomValue, 2);
                    }
                }

                break;
        }

        return ElectricalConversion.convertFromBase(answerUnit, solution);
    }
}
