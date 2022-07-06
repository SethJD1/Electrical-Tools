package com.example.seth.electricaltoolsandsafety.Tools.MAD;

import com.example.seth.electricaltoolsandsafety.Tools.ToolInterface;
import com.example.seth.electricaltoolsandsafety.Utilities.DatabaseManager;
import com.example.seth.electricaltoolsandsafety.Utilities.Utility;

import android.content.Context;

/**
 * Manages the Minimum Approach Distance data.
 */
public class MADImplementation implements ToolInterface {

    private Context context;
    private MADTool tool;

    private int elevation;
    private double kilovolts;
    private String unit;
    private String phaseToGroundAnswer;
    private String phaseToPhaseAnswer;

    /**
     * Default Constructor.
     */
    public MADImplementation(Context context){

        this.context = context;
        tool = new MADTool();
    }

    /**
     * Sets the base MAD Tool values from the user interface.
     * @param obj to set.
     */
    @Override
    public void setBaseToolValues(Object obj) {

        tool = (MADTool) obj;

        elevation = tool.getElevation();
        kilovolts = tool.getKilovolts();
        unit = tool.getUnit();

        setCalculatedToolValues();
    }

    /**
     * Validates user inputs for the MAD equation; elevation must not be blank or less than one,
     * kilovolts must not be blank and great than zero and less than 801;
     * @param obj to check for valid values
     * @return boolean value if the the inputs are value and false otherwise
     */
    @Override
    public boolean hasValidInputs(Object obj) {

        MADTool tool = (MADTool) obj;

        final int LOWER_LIMIT = 0;
        final int UPPER_LIMIT = 800;

        if(tool.getElevation() < LOWER_LIMIT || tool.getKilovolts() <= LOWER_LIMIT
                || tool.getKilovolts() > UPPER_LIMIT){
            return false;
        } else {
            return true;
        }
    }

    /**
     * Returns the current MAD tool.
     * @return a MAD tool
     */
    @Override
    public Object getTool() {
        return tool;
    }

    /**
     * Adds an Equation to the Saved Equation List.
     */
    public void addToSavedEquationList(){

        DatabaseManager databaseManager = new DatabaseManager(context);
        databaseManager.addTool(tool);
    }

    /**
     * Calculates and sets the Minimum Approach Distance Tool values.
     */
    private void setCalculatedToolValues() {

        final String TITLE = "MAD";
        String date = Utility.getCurrentDate();

        tool.setTitle(TITLE + "    " + date);
        tool.setDate(date);

        phaseToGroundAnswer = MADCalculator.phaseToGroundMAD(kilovolts, elevation, unit);
        phaseToPhaseAnswer = MADCalculator.phaseToPhaseMAD(kilovolts, elevation, unit);

        tool.setSnapshot("Phase-Ground = " + phaseToGroundAnswer + " / " + "Phase-Phase = " + phaseToPhaseAnswer);
        tool.setDetails(elevation + "," + kilovolts + "," + phaseToGroundAnswer + "," + phaseToPhaseAnswer);
    }

    /**
     * Returns the current Phase-to-Ground answer.
     * @return string representation of the phase-to-ground answer
     */
    public String getPhaseToGroundAnswer(){
        return phaseToGroundAnswer;
    }

    /**
     * Returns the current Phase-to-Phase answer.
     * @return string representation of the phase-to-phase answer
     */
    public String getPhaseToPhaseAnswer(){
        return phaseToPhaseAnswer;
    }
}
