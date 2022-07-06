package com.example.seth.electricaltoolsandsafety.Tools.Rigging;

import android.content.Context;

import com.example.seth.electricaltoolsandsafety.Tools.ToolInterface;
import com.example.seth.electricaltoolsandsafety.Utilities.DatabaseManager;
import com.example.seth.electricaltoolsandsafety.Utilities.Utility;

import java.text.DecimalFormat;

/**
 * Manages the Rigging Fragment data.
 */
public class RiggingImplementation implements ToolInterface {

    private RiggingTool tool;
    private Context context;
    private final String RIGGING_TITLE = "Rigging";

    private int numberOfSlings;
    private double slingLength;
    private double slingHeight;
    private double loadWeight;
    private double loadPerSling;

    private final String PATTERN = "#.##";

    private final DecimalFormat NUMBER_FORMAT = new DecimalFormat(PATTERN);

    /**
     * Default Constructor
     * @param context
     */
    public RiggingImplementation(Context context){
        this.context = context;
        tool = new RiggingTool();
    }

    /**
     * Sets the base Rigging Tool values from the user interface.
     * @param obj to set
     */
    @Override
    public void setBaseToolValues(Object obj) {

        tool = (RiggingTool) obj;

        numberOfSlings = tool.getNumberOfSlings();
        slingLength = tool.getSlingLength();
        slingHeight = tool.getSlingHeight();
        loadWeight = tool.getLoadWeight();

        setCalculatedToolValues();
    }

    /**
     * Validates uer inputs for the rigging equation; amount must be greater than zero and sling
     * length must be greater than the sling height.
     * @param obj to check for valid value
     * @return true if the values are valid and false otherwise
     */
    @Override
    public boolean hasValidInputs(Object obj) {

        RiggingTool tool = (RiggingTool) obj;
        final int LOW_VALUE = 0;

        if(tool.getSlingLength() > LOW_VALUE && tool.getSlingHeight() > LOW_VALUE
                && tool.getLoadWeight() > LOW_VALUE && tool.getSlingLength() > tool.getSlingHeight()){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns the current Rigging Tool.
     * @return current rigging tool
     */
    @Override
    public Object getTool() {
        return tool;
    }

    /**
     * Adds the rigging tool to the database.
     */
    @Override
    public void addToSavedEquationList() {
        DatabaseManager databaseManager = new DatabaseManager(context);
        databaseManager.addTool(tool);
    }

    /**
     * Calculates and sets the Rigging Tool values.
     */
    private void setCalculatedToolValues(){

        String date = Utility.getCurrentDate();

        tool.setTitle(RIGGING_TITLE + "    " + date);
        tool.setDate(date);

        loadPerSling = RiggingCalculator.riggingCalculator(numberOfSlings, slingLength, slingHeight, loadWeight);

        tool.setSnapshot("Load per Sling" + " = " + NUMBER_FORMAT.format(loadPerSling));

        tool.setDetails(numberOfSlings + "," + slingLength + "," + slingHeight + "," + loadWeight + ","
                + loadPerSling);
    }

    /**
     * Returns the rigging tool calculation answer.
     * @return
     */
    public String getAnswer(){
        return NUMBER_FORMAT.format(loadPerSling);
    }
}
