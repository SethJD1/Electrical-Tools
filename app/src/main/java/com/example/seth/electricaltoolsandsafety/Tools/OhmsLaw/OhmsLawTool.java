package com.example.seth.electricaltoolsandsafety.Tools.OhmsLaw;

import com.example.seth.electricaltoolsandsafety.Tools.Tool;

/**
 * Creates an Ohm's Law Object.
 */
public class OhmsLawTool extends Tool {

    private String unitOne;
    private String quantityOne;
    private double valueOne;

    private String unitTwo;
    private String quantityTwo;
    private double valueTwo;

    private String unitAnswer;
    private String quantityAnswer;
    private double answer;

    /**
     * Default Constructor
     */
    public OhmsLawTool(){}

    /**
     * Partial Calculator Constructor
     *
     * @param unitOne electrical subunit
     * @param quantityOne electrical quantity
     * @param valueOne of the electrical quantity and subunit
     * @param unitTwo electrical subunit
     * @param quantityTwo electrical quantity
     * @param valueTwo of the electrical quantity and subunit
     * @param unitAnswer selected to solve for
     * @param quantityAnswer selected to solve for
     */
    public OhmsLawTool(String unitOne, String quantityOne, double valueOne, String unitTwo,
                       String quantityTwo, double valueTwo, String unitAnswer,
                       String quantityAnswer) {

        this.unitOne = unitOne;
        this.quantityOne = quantityOne;
        this.valueOne = valueOne;
        this.unitTwo = unitTwo;
        this.quantityTwo = quantityTwo;
        this.valueTwo = valueTwo;
        this.unitAnswer = unitAnswer;
        this.quantityAnswer = quantityAnswer;
    }

    /**
     * Complete Constructor
     *
     * @param unitOne electrical subunit
     * @param quantityOne electrical quantity
     * @param valueOne of the electrical quantity and subunit
     * @param unitTwo electrical subunit
     * @param quantityTwo electrical quantity
     * @param valueTwo of the electrical quantity and subunit
     * @param unitAnswer selected to solve for
     * @param quantityAnswer selected to solve for
     * @param answer for the selected unit and quantity
     */
    public OhmsLawTool(long id, String title, String date, String snapshot, String details,
                       String unitOne, String quantityOne, double valueOne, String unitTwo,
                       String quantityTwo, double valueTwo, String unitAnswer,
                       String quantityAnswer, double answer) {

        super(id, title, date, snapshot, details);

        this.unitOne = unitOne;
        this.quantityOne = quantityOne;
        this.valueOne = valueOne;
        this.unitTwo = unitTwo;
        this.quantityTwo = quantityTwo;
        this.valueTwo = valueTwo;
        this.unitAnswer = unitAnswer;
        this.quantityAnswer = quantityAnswer;
        this.answer = answer;
    }

    /**
     * Returns the row one electrical subunit.
     * @return the row one electrical subunit
     */
    public String getUnitOne() {
        return unitOne;
    }

    /**
     * Sets the row one electrical subunits.
     * @param unitOne electrical subunit to set
     */
    public void setUnitOne(String unitOne) {
        this.unitOne = unitOne;
    }

    /**
     * Returns the row one electrical quantity.
     * @return row one electrical quantity
     */
    public String getQuantityOne() {
        return quantityOne;
    }

    /**
     * Sets the row one electrical quantity.
     * @param quantityOne electrical quantity to set
     */
    public void setQuantityOne(String quantityOne) {
        this.quantityOne = quantityOne;
    }

    /**
     * Returns the row one value for the electrical quantity.
     * @return row one electrical quantity
     */
    public double getValueOne() {
        return valueOne;
    }

    /**
     * Sets the row one value for the row electrical quantity.
     * @param valueOne
     */
    public void setValueOne(double valueOne) {
        this.valueOne = valueOne;
    }

    /**
     * Returns the row two electrical subunit.
     * @return the row two electrical subunit
     */
    public String getUnitTwo() {
        return unitTwo;
    }

    /**
     * Sets the row two electrical subunits.
     * @param unitTwo electrical subunit to set
     */
    public void setUnitTwo(String unitTwo) {
        this.unitTwo = unitTwo;
    }

    /**
     * Returns the row two electrical quantity.
     * @return row two electrical quantity
     */
    public String getQuantityTwo() {
        return quantityTwo;
    }

    /**
     * Sets the row two electrical quantity.
     * @param quantityTwo electrical quantity to set
     */
    public void setQuantityTwo(String quantityTwo) {
        this.quantityTwo = quantityTwo;
    }

    /**
     * Returns the row two value for the electrical quantity.
     * @return row two electrical quantity
     */
    public double getValueTwo() {
        return valueTwo;
    }

    /**
     * Sets the row two value for the row electrical quantity.
     * @param valueTwo
     */
    public void setValueTwo(double valueTwo) {
        this.valueTwo = valueTwo;
    }

    /**
     * Returns the electrical unit to solve for.
     * @return the electrical unit to solve for
     */
    public String getUnitAnswer() {
        return unitAnswer;
    }

    /**
     * Sets the electrical unit to solve for.
     * @param unitAnswer electrical unit.
     */
    public void setUnitAnswer(String unitAnswer) {
        this.unitAnswer = unitAnswer;
    }

    /**
     * Returns the electrical quantity unit to solve for.
     * @return the electrical quantity to solve for
     */
    public String getQuantityAnswer() {
        return quantityAnswer;
    }

    /**
     * Sets the electrical quantity to solve for.
     * @param quantityAnswer for the designated electrical quantity
     */
    public void setQuantityAnswer(String quantityAnswer) {
        this.quantityAnswer = quantityAnswer;
    }

    /**
     * Returns the answer for the designated electrical quantity and unit.
     * @return answer for the electrical quantity
     */
    public double getAnswer() {
        return answer;
    }

    /**
     * Sets the answer for the designated electrical quantity and unit.
     * @param answer
     */
    public void setAnswer(double answer) {
        this.answer = answer;
    }


}
