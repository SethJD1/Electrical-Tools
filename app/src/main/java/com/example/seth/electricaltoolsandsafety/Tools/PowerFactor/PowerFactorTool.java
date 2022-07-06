package com.example.seth.electricaltoolsandsafety.Tools.PowerFactor;

import com.example.seth.electricaltoolsandsafety.Tools.Tool;

/**
 * Creates a Power Factor Tool.
 */
public class PowerFactorTool extends Tool {

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
    public PowerFactorTool(){}

    /**
     * Partial Calculator Constructor
     *
     * @param unitOne electrical subunit
     * @param quantityOne power factor quantity
     * @param valueOne of the power factor quantity and subunit
     * @param unitTwo electrical subunit
     * @param quantityTwo power factor quantity
     * @param valueTwo of the power factor quantity and subunit
     * @param unitAnswer selected to solve for
     * @param quantityAnswer selected to solve for
     */
    public PowerFactorTool(String unitOne, String quantityOne, double valueOne, String unitTwo,
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
     * @param quantityOne power factor quantity
     * @param valueOne of the power factor quantity and subunit
     * @param unitTwo electrical subunit
     * @param quantityTwo power factor quantity
     * @param valueTwo of the power factor quantity and subunit
     * @param unitAnswer selected to solve for
     * @param quantityAnswer selected to solve for
     * @param answer for the selected unit and quantity
     */
    public PowerFactorTool(long id, String title, String date, String snapshot, String details,
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
     * Returns the row one power factor quantity.
     * @return row one power factor quantity
     */
    public String getQuantityOne() {
        return quantityOne;
    }

    /**
     * Sets the row one power factor quantity.
     * @param quantityOne power factor quantity to set
     */
    public void setQuantityOne(String quantityOne) {
        this.quantityOne = quantityOne;
    }

    /**
     * Returns the row one value for the power factor quantity.
     * @return row one power factor quantity
     */
    public double getValueOne() {
        return valueOne;
    }

    /**
     * Sets the row one value for the row power factor quantity.
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
     * Returns the row two power factor quantity.
     * @return row two power factor quantity
     */
    public String getQuantityTwo() {
        return quantityTwo;
    }

    /**
     * Sets the row two power factor quantity.
     * @param quantityTwo power factor quantity to set
     */
    public void setQuantityTwo(String quantityTwo) {
        this.quantityTwo = quantityTwo;
    }

    /**
     * Returns the row two value for the power factor quantity.
     * @return row two power factor quantity
     */
    public double getValueTwo() {
        return valueTwo;
    }

    /**
     * Sets the row two value for the row power factor quantity.
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
     * Returns the power factor quantity unit to solve for.
     * @return the power factor quantity to solve for
     */
    public String getQuantityAnswer() {
        return quantityAnswer;
    }

    /**
     * Sets the power factor quantity to solve for.
     * @param quantityAnswer for the designated power factor quantity
     */
    public void setQuantityAnswer(String quantityAnswer) {
        this.quantityAnswer = quantityAnswer;
    }

    /**
     * Returns the answer for the designated power factor quantity and unit.
     * @return answer for the power factor quantity
     */
    public double getAnswer() {
        return answer;
    }

    /**
     * Sets the answer for the designated power factor quantity and unit.
     * @param answer
     */
    public void setAnswer(double answer) {
        this.answer = answer;
    }

}
