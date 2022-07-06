package com.example.seth.electricaltoolsandsafety.Tools.OhmsLaw;

import com.example.seth.electricaltoolsandsafety.Tools.ToolInterface;
import com.example.seth.electricaltoolsandsafety.Utilities.DatabaseManager;
import com.example.seth.electricaltoolsandsafety.Utilities.ElectricalProperties;
import com.example.seth.electricaltoolsandsafety.Utilities.Utility;

import android.content.Context;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Manages the Ohms Law Fragment data.
 */
public class OhmsLawImplementation implements ToolInterface {

    private OhmsLawTool tool;
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

    private final String PATTERN = "#.###########";
    private final DecimalFormat NUMBER_FORMAT = new DecimalFormat(PATTERN);

    /**
     * Constructor
     * @param context
     */
    public OhmsLawImplementation(Context context) {

        this.context = context;
        tool = new OhmsLawTool();
    }

    /**
     * Sets the base Ohms Law Tool values from the user interface.
     * @param obj to set.
     */
    @Override
    public void setBaseToolValues(Object obj) {

        tool = (OhmsLawTool) obj;

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
     * Returns the current Ohms Law tool.
     * @return an Ohm's law tool
     */
    @Override
    public Object getTool() {
        return tool;
    }

    /**
     * Validates user inputs for the ohms law equation; quantities must not be the same and values
     * must not be zero.
     * @param obj to check for valid values
     * @return true if the the inputs are valid and false otherwise
     */
    @Override
    public boolean hasValidInputs(Object obj) {

        OhmsLawTool tool = (OhmsLawTool) obj;

        if(!tool.getQuantityOne().equals(tool.getQuantityTwo()) && tool.getValueOne() > 0
                && tool.getValueTwo() > 0){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Add a Ohms Law equation to the saved equation list.
     */
    @Override
    public void addToSavedEquationList() {

        DatabaseManager databaseManager = new DatabaseManager(context);
        databaseManager.addTool(tool);
    }

    /**
     * Calculates and sets the Ohms Law Tool values.
     */
    private void setCalculatedToolValues() {

        final String TITLE = "Ohm's Law";
        String date = Utility.getCurrentDate();

        tool.setTitle(TITLE + "    " + date);
        tool.setDate(date);

        answer = OhmsLawCalculator.ohmsLawCalculator(unitOne, quantityOne, valueOne, unitTwo,
                quantityTwo, valueTwo, unitAnswer, quantityAnswer);

        tool.setAnswer(answer);

        // Remove the unit value if its the base unit
        if(unitAnswer.equalsIgnoreCase(quantityAnswer)){
            tool.setSnapshot(quantityAnswer + " = " + NUMBER_FORMAT.format(answer));
        } else {
            tool.setSnapshot(unitAnswer + quantityAnswer.toLowerCase()
                    + " = " + NUMBER_FORMAT.format(answer));
        }

        tool.setDetails(unitOne + "," + quantityOne + "," + valueOne + "," + unitTwo + ","
                + quantityTwo + "," + valueTwo + "," + unitAnswer + "," + quantityAnswer
                + "," + answer);
    }

    /**
     * Returns the Ohm's Law Answer.
     * @return formatted string representation of the answer number value
     */
    public String getAnswer() {
        return NUMBER_FORMAT.format(answer);
    }

    /**
     * Returns a list of standard electrical subunits
     * @return an array of electrical subunits
     */
    public String[] getElectricalUnits(){
        return ElectricalProperties.getElectricalUnits();
    }

    /**
     * Returns a list of electrical quantities
     * @return an array of electrical quantities
     */
    public String[] getElectricalQuantities(){
        return ElectricalProperties.getElectricalQuantities();
    }

    /**
     * Modifies the electrical quantity list based on the selected quantity to solve for.
     * @param selectedQuantity to solve for
     */
    public ArrayList<String> removeQuantity(String selectedQuantity){

        String[] temp = ElectricalProperties.getElectricalQuantities();
        ArrayList<String> newList = new ArrayList<>();

        for(int index = 0; index < temp.length; index ++){

            if(!temp[index].equalsIgnoreCase(selectedQuantity)){
                newList.add(temp[index]);
            }
        }

        return newList;
    }

    /**
     * Modifies the electrical unit with the user selected quantity to solve for.
     * @param electricalQuantity
     */
    public ArrayList<String> updatedUnitAnswerList(String electricalQuantity){

        String[] temp = ElectricalProperties.getElectricalUnits();
        ArrayList<String> newList = new ArrayList<>();

        for(int index = 0; index < temp.length; index ++){

            if(temp[index].equalsIgnoreCase("Base")){
                newList.add(index, electricalQuantity);
            } else {
                newList.add(temp[index] + electricalQuantity.toLowerCase());
            }
        }

        return newList;
    }
}
