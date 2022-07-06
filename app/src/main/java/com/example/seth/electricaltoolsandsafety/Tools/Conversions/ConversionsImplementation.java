package com.example.seth.electricaltoolsandsafety.Tools.Conversions;

import android.content.Context;

import com.example.seth.electricaltoolsandsafety.Tools.ToolInterface;
import com.example.seth.electricaltoolsandsafety.Utilities.DatabaseManager;
import com.example.seth.electricaltoolsandsafety.Utilities.DistanceConversion;
import com.example.seth.electricaltoolsandsafety.Utilities.ElectricalProperties;
import com.example.seth.electricaltoolsandsafety.Utilities.PressureConversion;
import com.example.seth.electricaltoolsandsafety.Utilities.Utility;

import java.text.DecimalFormat;

/**
 * Manages the Conversion Fragment data.
 */
public class ConversionsImplementation implements ToolInterface {

    private Context context;
    private ConversionsTool tool;

    private String conversionType;
    private String fromUnit;
    private String toUnit;

    private double amount;
    private double answer;

    private final String PATTERN = "#.###########";
    private final DecimalFormat NUMBER_FORMAT = new DecimalFormat(PATTERN);

    private String ELECTRICAL = "Electrical";
    private String DISTANCE = "Distance";
    private String PRESSURE = "Pressure";

    private int defaultUnitPosition;

    /**
     * Defualt Constructor
     * @param context
     */
    public ConversionsImplementation(Context context){
        this.context = context;
        tool = new ConversionsTool();
    }

    /**
     * Sets the base Conversion Tool values from the user interface.
     * @param obj to set
     */
    @Override
    public void setBaseToolValues(Object obj) {

        tool = (ConversionsTool) obj;

        conversionType = tool.getConversionType();
        fromUnit = tool.getFromUnit();
        toUnit = tool.getToUnit();
        amount = tool.getAmount();

        setCalculatedToolValues();
    }

    /**
     * Validates uer inputs for the conversion equation; amount must be greater than zero.
     * @param obj to check for valid value
     * @return true if the values are valid and false otherwise
     */
    @Override
    public boolean hasValidInputs(Object obj) {

        ConversionsTool tool = (ConversionsTool) obj;

        if(tool.getAmount() > 0){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns the current Conversion Tool.
     * @return current conversion tool
     */
    @Override
    public Object getTool() {
        return tool;
    }

    /**
     * Adds the Conversion tool to the database.
     */
    @Override
    public void addToSavedEquationList() {

        DatabaseManager databaseManager = new DatabaseManager(context);
        databaseManager.addTool(tool);
    }

    /**
     * Calculates and sets the Conversion Tool values.
     */
    public void setCalculatedToolValues(){

        final String TITLE = "Conversion";
        String date = Utility.getCurrentDate();

        tool.setTitle(TITLE + "    " + date);
        tool.setDate(date);

        answer = ConversionsCalculator.convert(conversionType, fromUnit, toUnit, amount);

        tool.setSnapshot(getAnswerDetails());

        tool.setDetails(conversionType + "," + fromUnit + "," + toUnit + "," + amount + ","
                + answer);
    }

    /**
     * Returns the current Conversion answer.
     * @return answer to the conversion
     */
    public String getAnswer(){
        return NUMBER_FORMAT.format(answer);
    }

    /**
     * Returns a snapshot of the answer including the user selected variables.
     * @return answer snapshot
     */
    public String getAnswerDetails(){

        String answerDetails = "";

        if(conversionType.equals(ELECTRICAL)){

            answerDetails = amount + " " + fromUnit + " = " + NUMBER_FORMAT.format(answer) + " " + toUnit;

        } else if(conversionType.equals(DISTANCE)){

            if(amount == 1){

                if(fromUnit.equals("Feet")){
                    answerDetails = amount + " " + "Foot";
                } else {
                    String temp = fromUnit.substring(0, fromUnit.length() - 1);
                    answerDetails = amount + " " + temp;
                }
            }

            else {
                answerDetails = amount + " " + fromUnit;
            }

            answerDetails = answerDetails + " = ";

            if(answer == 1){

                if(toUnit.equals("Feet")){
                    answerDetails = answerDetails + answer + " " + "Foot";
                } else {
                    String temp = fromUnit.substring(0, fromUnit.length() - 1);
                    answerDetails = answerDetails + answer + " " + temp;
                }
            } else {
                answerDetails = answerDetails + answer + " " + toUnit;
            }

        } else if(conversionType.equals(PRESSURE)){
            answerDetails = amount + " " + fromUnit + " = " + NUMBER_FORMAT.format(answer) + " " + toUnit;
        }

        return answerDetails;
    }

    /**
     * Returns a list of the Conversion Types.
     * @return array of conversion types
     */
    public String[] getConversionTypes(){
        return ConversionsCalculator.getConversionTypes();
    }

    /**
     * Modifies the unit list based on which conversion type is provided.
     * @param conversionType selected
     * @return modified unit lists
     */
    public String[] modifyUnitList(String conversionType){

        String[] units = null;

        if(conversionType.equals(ELECTRICAL)){

            units = ElectricalProperties.getElectricalUnits();
            defaultUnitPosition = 3;

        } else if(conversionType.equals(DISTANCE)){

            units = DistanceConversion.getDistanceUnits();
            defaultUnitPosition = 5;

        } else if(conversionType.equals(PRESSURE)){
            units = PressureConversion.getPressureUnits();
            defaultUnitPosition = 1;
        }

        return units;
    }

    /**
     * Returns the current default position for each of the unit lists.
     * @return int value for the base conversion unit
     */
    public int getDefaultUnitPosition(){
        return defaultUnitPosition;
    }

}
