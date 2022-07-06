package com.example.seth.electricaltoolsandsafety.Tools;

/**
 * Tool Implementation functions.
 */
public interface ToolInterface {

    void setBaseToolValues(Object obj);

    boolean hasValidInputs(Object obj);

    Object getTool();

    void addToSavedEquationList();
}
