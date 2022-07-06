package com.example.seth.electricaltoolsandsafety.Tools.PowerFactor;

import com.example.seth.electricaltoolsandsafety.Tools.ToolInterface;
import com.example.seth.electricaltoolsandsafety.Utilities.DatabaseManager;
import com.example.seth.electricaltoolsandsafety.Utilities.ElectricalProperties;
import com.example.seth.electricaltoolsandsafety.Utilities.Utility;

import android.content.Context;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Manages the Power Factor Fragment data.
 */
public class PowerFactorImplementation implements ToolInterface {

    private PowerFactorTool tool;
    private Context context;

    private String unitOne;
    private String unitTwo;
    private String unitAnswer;

    private String quantityOne;
    private String quantityTwo;
    private String quantityAnswer;

    private Double valueOne;
    private Double valueTwo;
    private Double answer;

    private final String POWER_FACTOR = "Power Factor";
    private final String ANGLE = "Angle";
    private final String VA = "VA";
    private final String VAR = "VAR";
    private final String WATTS = "Watts";

    private final String PATTERN_LONG = "#.###########";
    private final String PATTERN_SHORT = "#.##";

    private final DecimalFormat NUMBER_FORMAT_LONG = new DecimalFormat(PATTERN_LONG);
    private final DecimalFormat NUMBER_FORMAT_SHORT = new DecimalFormat(PATTERN_SHORT);

    private final NumberFormat percent = NumberFormat.getPercentInstance();

    /**
     * Defualt Constructor
     * @param context
     */
    public PowerFactorImplementation(Context context){

        this.context = context;
        tool = new PowerFactorTool();
    }

    /**
     * Sets the base Power Factor Tool values from the user interface.
     * @param obj to set.
     */
    @Override
    public void setBaseToolValues(Object obj) {

        tool = (PowerFactorTool) obj;

        unitOne = tool.getUnitOne();
        unitTwo = tool.getUnitTwo();
        unitAnswer = tool.getUnitAnswer();
        quantityOne = tool.getQuantityOne();
        quantityTwo = tool.getQuantityTwo();
        quantityAnswer = tool.getQuantityAnswer();
        valueOne = tool.getValueOne();
        valueTwo = tool.getValueTwo();

        setCalculatedToolValues();
    }

    /**
     * Returns the current Power Factor tool.
     * @return a Power Factor tool
     */
    @Override
    public Object getTool() {
        return tool;
    }

    /**
     * Validates user inputs for the ohms law equation; quantities must not be the same, values
     * must be valid and both quantities cannot be an angle.
     * @param obj to check for valid values
     * @return boolean value if the the inputs are value and false otherwise
     */
    @Override
    public boolean hasValidInputs(Object obj) {

        PowerFactorTool tool = (PowerFactorTool) obj;
        int count = 0;

        if(isValueValid(tool.getQuantityOne(), tool.getValueOne())){
            count ++;
        }

        if(isValueValid(tool.getQuantityTwo(), tool.getValueTwo())){
            count ++;
        }

        if(isAngleSelected(tool.getQuantityOne()) && isAngleSelected(tool.getQuantityTwo())){
            return false;
        } else {
            count ++;
        }

        return count == 3;
    }

    /**
     * Adds a Power Factor tool to the saved equation list.
     */
    @Override
    public void addToSavedEquationList() {

        DatabaseManager databaseManager = new DatabaseManager(context);
        databaseManager.addTool(tool);
    }

    /**
     * Calculates and sets the Power Factor Tool values.
     */
    private void setCalculatedToolValues(){

        String date = Utility.getCurrentDate();

        tool.setTitle(POWER_FACTOR + "    " + date);
        tool.setDate(date);

        answer = PowerFactorCalculator.powerFactorCalculator(unitOne, quantityOne, valueOne, unitTwo,
                quantityTwo, valueTwo, unitAnswer, quantityAnswer);

        // Remove the unit value if its the base unit
        if(unitAnswer.equalsIgnoreCase(quantityAnswer)){

            tool.setSnapshot(quantityAnswer.toUpperCase() + " = " + NUMBER_FORMAT_LONG.format(answer));

        } else if(quantityAnswer.equals(ANGLE)){

            if(unitAnswer.equals(POWER_FACTOR)){
                tool.setSnapshot(unitAnswer + " = " + percent.format(answer));
            } else {
                tool.setSnapshot(unitAnswer + " = " + NUMBER_FORMAT_SHORT.format(answer) + "\u00B0");
            }

        } else {
            tool.setSnapshot(unitAnswer + quantityAnswer.toLowerCase()
                    + " = " + NUMBER_FORMAT_LONG.format(answer));
        }

        tool.setDetails(unitOne + "," + quantityOne + "," + valueOne + "," + unitTwo + ","
                + quantityTwo + "," + valueTwo + "," + unitAnswer + "," + quantityAnswer
                + "," + answer);
    }

    /**
     * Returns the Power Factor Answer.
     * @return formatted string representation of the answer number value
     */
    public String getAnswer() {

        percent.setMinimumFractionDigits(2);

        if(unitAnswer.equals(POWER_FACTOR)){
            return percent.format(answer);
        } else if(unitAnswer.contains("Angle")){
            return NUMBER_FORMAT_SHORT.format(answer) + "\u00B0";
        } else {
            return NUMBER_FORMAT_LONG.format(answer);
        }

    }

    /**
     * Returns a list of standard electrical subunits
     * @return an array of electrical subunits
     */
    public String[] getElectricalUnits(){
        return ElectricalProperties.getElectricalUnits();
    }

    /**
     * Returns a list of power factor quantities
     * @return an array of power factor quantities
     */
    public String[] getPowerFactorQuantities(){
        return ElectricalProperties.getPowerFactorQuantities();
    }

    /**
     * Modifies the electrical quantity list based on the selected quantity to solve for.
     * @param selectedQuantity to solve for
     */
    public ArrayList<String> removeQuantity(String selectedQuantity){

        String[] temp;
        ArrayList<String> newList = new ArrayList<>();

        if(selectedQuantity.equalsIgnoreCase(ANGLE)){

            temp = ElectricalProperties.getPowerFactorUnitMinusAngles();

            for(int index = 0; index < temp.length; index ++){
                newList.add(temp[index]);
            }

        } else {
            temp = ElectricalProperties.getPowerFactorQuantities();

            for(int index = 0; index < temp.length; index ++){

                if(!temp[index].equalsIgnoreCase(selectedQuantity)){
                    newList.add(temp[index]);
                }
            }
        }

        return newList;
    }

    /**
     * Modifies the electrical unit with the user selected quantity to solve for.
     * @param electricalQuantity
     */
    public ArrayList<String> updatedUnitAnswerList(String electricalQuantity){

        String[] electricalUnits = ElectricalProperties.getElectricalUnits();
        String[] powerFactor = ElectricalProperties.getPowerFactorUnits();
        ArrayList<String> newList = new ArrayList<>();

        final String BASE = "Base";

        if(electricalQuantity.equalsIgnoreCase(ANGLE)){

            for(int index = 0; index < powerFactor.length; index ++){
                newList.add(index, powerFactor[index]);
            }

        } else {

            for(int index = 0; index < electricalUnits.length; index ++){

                if(electricalUnits[index].equalsIgnoreCase(BASE)){
                    newList.add(index, electricalQuantity);
                } else {

                    if(electricalQuantity.equalsIgnoreCase(VA) || electricalQuantity.equalsIgnoreCase(VAR)){
                        newList.add(electricalUnits[index] + electricalQuantity);
                    } else {
                        newList.add(electricalUnits[index] + electricalQuantity.toLowerCase());
                    }
                }
            }
        }

        return newList;
    }

    /**
     * Verifies if an angle is selected for the quantity.
     * @param quantity to verify
     * @return true if the value is a angle and false otherwise.
     */
    public boolean isAngleSelected(String quantity){

        String[] powerFactor = ElectricalProperties.getPowerFactorUnits();

        for(int index = 0; index < powerFactor.length; index ++){
            if(quantity.equals(powerFactor[index])){
                return true;
            }
        }

        return false;
    }

    /**
     * Verifies if the Power Factor Quantity is selected.
     * @param quantity to verify
     * @return true if the quantity is Power Factor, false otherwise.
     */
    private boolean isPowerFactorSelected(String quantity){
        if(quantity.equals(POWER_FACTOR)){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Verifies if both quantities are sides.
     * @param quantityOne to verify
     * @param quantityTwo to verify
     * @return boolean if both are sides and false otherwise.
     */
    private boolean areSidesSelected(String quantityOne, String quantityTwo){

        int countSides = 0;

        String[] sides = ElectricalProperties.getPowerFactorUnitMinusAngles();

        for(int index = 0; index < sides.length; index ++){

            if(quantityOne.equals(sides[index])){
                countSides ++;
            }

            if(quantityTwo.equals(sides[index])){
                countSides ++;
            }
        }

        return countSides == 2;
    }

    /**
     * Verifies if the quantities are valid; VA must always be larger than Watts and VAR.
     * @param quantityOne to verify
     * @param valueOne value of quantity one
     * @param quantityTwo to verify
     * @param valueTwo value of quantity two
     * @return true if both values are valid and false otherwise.
     */
    public boolean areQuantitiesValid(String quantityOne, double valueOne, String quantityTwo, double valueTwo){

        boolean valid = false;

        if(areSidesSelected(quantityOne, quantityTwo)){

            if(quantityOne.equals(VA)){

                if(quantityTwo.equals(WATTS) && valueOne > valueTwo){
                    valid = true;
                } else if (quantityTwo.equals(VAR) && valueOne > valueTwo){
                    valid = true;
                }

            } else if(quantityTwo.equals(VA)){

                if(quantityOne.equals(WATTS) && valueTwo > valueOne){
                    valid = true;
                } else if (quantityOne.equals(VAR) && valueTwo > valueOne){
                    valid = true;
                }

            } else {
                valid = true;
            }

        } else {

            valid = true;
        }

        return valid;
    }

    /**
     * Validates if a value is valid based on a specific quantity.
     *
     * @param quantity of the value
     * @param value to validate
     * @return true if value is valid, false otherwise
     */
    public boolean isValueValid(String quantity, double value){

        final int LOW_VALUE = 0;
        final int POWER_FACTOR_LIMIT = 1;
        final int HIGH_VALUE = 90;

        if(isPowerFactorSelected(quantity)){

            if(value <= LOW_VALUE || value > POWER_FACTOR_LIMIT){
                return false;
            } else {
                return true;
            }
        } else if(isAngleSelected(quantity)){

            if(value <= LOW_VALUE || value >= HIGH_VALUE){
                return false;
            } else {
                return true;
            }

        } else {

            if(value <= LOW_VALUE){
                return false;
            } else {
                return true;
            }
        }
    }
}
