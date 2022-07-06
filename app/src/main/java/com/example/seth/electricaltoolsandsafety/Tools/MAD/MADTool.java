package com.example.seth.electricaltoolsandsafety.Tools.MAD;

import com.example.seth.electricaltoolsandsafety.Tools.Tool;

/**
 * Creates a Minimum Approach Distance Tool Object.
 */
public class MADTool extends Tool {

    private int elevation;
    private double kilovolts;
    private String unit;
    private String phaseToGroundAnswer;
    private String phaseToPhaseAnswer;

    /**
     * Default Constructor
     */
    public MADTool(){}

    /**
     * Partial Constructor
     * @param elevation
     * @param kilovolts
     * @param unit
     */
    public MADTool(int elevation, double kilovolts, String unit){

        this.elevation = elevation;
        this.kilovolts = kilovolts;
        this.unit = unit;
    }

    /**
     * Constructor
     *
     * @param elevation in feet or meters, as specified in the units field
     * @param kilovolts for the distance calculation
     * @param unit in either meters or feet
     * @param phaseToGroundAnswer for the calculation
     * @param phaseToPhaseAnswer for the calculation
     */
    public MADTool(long id, String title, String date, String snapshot, String details,
                   int elevation, double kilovolts, String unit, String phaseToGroundAnswer,
                   String phaseToPhaseAnswer) {

        super(id, title, date, snapshot, details);

        this.elevation = elevation;
        this.kilovolts = kilovolts;
        this.unit = unit;
        this.phaseToGroundAnswer = phaseToGroundAnswer;
        this.phaseToPhaseAnswer = phaseToPhaseAnswer;
    }

    /**
     * Returns the current elevation value.
     * @return elevation
     */
    public int getElevation() {
        return elevation;
    }

    /**
     * Sets the current elevation value.
     * @param elevation value to set
     */
    public void setElevation(int elevation) {
        this.elevation = elevation;
    }

    /**
     * Returns the user specified kilovolt value.
     * @return kilovolts
     */
    public double getKilovolts() {
        return kilovolts;
    }

    /**
     * Sets the user specified kilovolts value.
     * @param kilovolts to value to set
     */
    public void setKilovolts(double kilovolts) {
        this.kilovolts = kilovolts;
    }

    /**
     * Returns the distance unit specified by the user, either feet or meters.
     * @return current unit value.
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Sets the distance unit specified by the user, either feet or meters.
     * @param unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * Returns the current Phase-to-Ground MAD answer.
     * @return Phase-to-Ground MAD value
     */
    public String getPhaseToGroundAnswer() {
        return phaseToGroundAnswer;
    }

    /**
     * Sets the Phase-to-Ground answer value.
     * @param phaseToGroundAnswer value to set
     */
    public void setPhaseToGroundAnswer(String phaseToGroundAnswer) {
        this.phaseToGroundAnswer = phaseToGroundAnswer;
    }

    /**
     * Returns the current Phase-to-Phase MAD answer.
     * @return Phase-to-Phase MAD value
     */
    public String getPhaseToPhaseAnswer() {
        return phaseToPhaseAnswer;
    }

    /**
      Sets the Phase-to-Phase answer value.
     * @param phaseToPhaseAnswer value to set
     */
    public void setPhaseToPhaseAnswer(String phaseToPhaseAnswer) {
        this.phaseToPhaseAnswer = phaseToPhaseAnswer;
    }


}
