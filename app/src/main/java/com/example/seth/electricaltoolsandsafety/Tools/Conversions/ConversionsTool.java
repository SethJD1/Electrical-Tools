package com.example.seth.electricaltoolsandsafety.Tools.Conversions;

import com.example.seth.electricaltoolsandsafety.Tools.Tool;

/**
 * Creates a Conversion Tool Object.
 */
public class ConversionsTool extends Tool{

    private String conversionType;
    private String fromUnit;
    private String toUnit;
    private double amount;
    private double answer;

    /**
     * Default Constructor
     */
    public ConversionsTool(){}

    /**
     * Partial Conversion Constructor
     *
     * @param conversionType for the calculation
     * @param fromUnit unit to convert from
     * @param toUnit unit to convert to
     * @param amount to convert
     * @param answer for the conversion
     */
    public ConversionsTool(String conversionType, String fromUnit, String toUnit, double amount, double answer) {

        this.conversionType = conversionType;
        this.fromUnit = fromUnit;
        this.toUnit = toUnit;
        this.amount = amount;
        this.answer = answer;
    }

    /**
     * Complete Constructor
     *
     * @param conversionType for the calculation
     * @param fromUnit unit to convert from
     * @param toUnit unit to convert to
     * @param amount to convert
     * @param answer for the conversion
     */
    public ConversionsTool(long id, String title, String date, String snapshot, String details,
                           String conversionType, String fromUnit, String toUnit, double amount,
                           double answer){

        super(id, title, date, snapshot, details);

        this.conversionType = conversionType;
        this.fromUnit = fromUnit;
        this.toUnit = toUnit;
        this.amount = amount;
        this.answer = answer;
    }

    /**
     * Returns the type of Conversion.
     * @return conversion type
     */
    public String getConversionType() {
        return conversionType;
    }

    /**
     * Sets the type of conversion.
     * @param conversionType to set
     */
    public void setConversionType(String conversionType) {
        this.conversionType = conversionType;
    }

    /**
     * Returns the unit to convert from.
     * @return unit to convert from
     */
    public String getFromUnit() {
        return fromUnit;
    }

    /**
     * Sets the unit to convert from.
     * @param fromUnit to convert from
     */
    public void setFromUnit(String fromUnit) {
        this.fromUnit = fromUnit;
    }

    /**
     * Returns the unit to convert to.
     * @return unit to convert to
     */
    public String getToUnit() {
        return toUnit;
    }

    /**
     * Sets the unit to convert to.
     * @param toUnit to convert to
     */
    public void setToUnit(String toUnit) {
        this.toUnit = toUnit;
    }

    /**
     * Returns the amount to convert.
     * @return amount to convert
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the amount to convert.
     * @param amount to convert
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Returns the conversion answer.
     * @return conversion answer
     */
    public double getAnswer() {
        return answer;
    }

    /**
     * Sets the conversion answer.
     * @param answer for the conversion
     */
    public void setAnswer(double answer) {
        this.answer = answer;
    }
}
