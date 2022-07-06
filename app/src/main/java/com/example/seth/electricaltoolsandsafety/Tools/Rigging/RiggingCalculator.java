package com.example.seth.electricaltoolsandsafety.Tools.Rigging;

/**
 * Calculates the weight on each sling of a load.
 */
public class RiggingCalculator {

    private static final int twoSlings = 2;
    private static final int threeSlings = 3;
    private static final int fourSlings = 4;

    private static final Integer[] SLING_LIFTS = {twoSlings, threeSlings, fourSlings};

    /**
     * Calculates the weight applied on each sling of a load.
     * @param numberOfSlings on the load
     * @param slingLength length of each sling
     * @param slingHeight height of each sling on the load
     * @param loadWeight total load weight
     * @return weight applied to each sling
     */
    public static double riggingCalculator(int numberOfSlings, double slingLength,
                                           double slingHeight, double loadWeight){

        return ((slingLength / slingHeight) * loadWeight) / numberOfSlings;
    }

    /**
     * Returns an array of applicable sling numbers.
     * @return an array of sling numbers.
     */
    public static Integer[] getSlingNumbers(){
        return SLING_LIFTS;
    }
}
