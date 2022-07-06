package com.example.seth.electricaltoolsandsafety.Tools.Rigging;

import com.example.seth.electricaltoolsandsafety.Tools.Tool;

/**
 * Creates a Rigging Tool.
 */
public class RiggingTool extends Tool {

    private int numberOfSlings;
    private double slingLength;
    private double slingHeight;
    private double loadWeight;
    private double loadPerSling;

    /**
     * Default Constructor
     */
    public RiggingTool(){}

    /**
     * Partial Constructor
     *
     * @param numberOfSlings for the rigged for the lift
     * @param slingLength for each of the rigged slings
     * @param slingHeight for each of the rigged slings
     * @param loadWeight total load weight
     * @param loadPerSling calculated load per sling
     */
    public RiggingTool(int numberOfSlings, double slingLength, double slingHeight,
                       double loadWeight, double loadPerSling) {

        this.numberOfSlings = numberOfSlings;
        this.slingLength = slingLength;
        this.slingHeight = slingHeight;
        this.loadWeight = loadWeight;
        this.loadPerSling = loadPerSling;
    }

    /**
     * Complete Constructor
     *
     * @param numberOfSlings for the rigged for the lift
     * @param slingLength for each of the rigged slings
     * @param slingHeight for each of the rigged slings
     * @param loadWeight total load weight
     * @param loadPerSling calculated load per sling
     */
    public RiggingTool(long id, String title, String date, String snapshot, String details,
                       int numberOfSlings, double slingLength, double slingHeight,
                       double loadWeight, double loadPerSling) {

        super(id, title, date, snapshot, details);
        this.numberOfSlings = numberOfSlings;
        this.slingLength = slingLength;
        this.slingHeight = slingHeight;
        this.loadWeight = loadWeight;
        this.loadPerSling = loadPerSling;
    }

    /**
     * Returns the current number of slings.
     * @return number of slings.
     */
    public int getNumberOfSlings() {
        return numberOfSlings;
    }

    /**
     * Sets the number of slings.
     * @param numberOfSlings to set
     */
    public void setNumberOfSlings(int numberOfSlings) {
        this.numberOfSlings = numberOfSlings;
    }

    /**
     * Returns the sling length for each of the slings.
     * @return sling length
     */
    public double getSlingLength() {
        return slingLength;
    }

    /**
     * Sets the number of slings.
     * @param slingLength to set
     */
    public void setSlingLength(double slingLength) {
        this.slingLength = slingLength;
    }

    /**
     * Returns the sling height for each of the slings.
     * @return sling height
     */
    public double getSlingHeight() {
        return slingHeight;
    }

    /**
     * Sets the sling height.
     * @param slingHeight to set
     */
    public void setSlingHeight(double slingHeight) {
        this.slingHeight = slingHeight;
    }

    /**
     * Returns the total load weight.
     * @return load weight
     */
    public double getLoadWeight() {
        return loadWeight;
    }

    /**
     * Sets the total load weight.
     * @param loadWeight to set
     */
    public void setLoadWeight(double loadWeight) {
        this.loadWeight = loadWeight;
    }

    /**
     * Returns the total applied load on each sling.
     * @return load per sling
     */
    public double getLoadPerSling() {
        return loadPerSling;
    }

    /**
     * Sets the load per sling.
     * @param loadPerSling to set
     */
    public void setLoadPerSling(double loadPerSling) {
        this.loadPerSling = loadPerSling;
    }
}
