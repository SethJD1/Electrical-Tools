package com.example.seth.electricaltoolsandsafety.Tools;

/**
 * Creates an electrical tool object.
 */
public class Tool {

    protected long id;
    protected String title;
    protected String date;
    protected String snapshot;
    protected String details;

    /**
     * Default Constructor
     */
    public Tool(){}

    /**
     * Constructor
     *
     * @param id of the tool object
     * @param title title of the tool Object
     * @param date date the tool was created
     * @param snapshot of the tool answer
     * @param details of the tool calculation values
     */
    public Tool(long id, String title, String date, String snapshot, String details) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.snapshot = snapshot;
        this.details = details;
    }

    /**
     * Returns the Tool objects unique id;
     * @return unique Tool id
     */
    public long getId(){
        return id;
    }

    /**
     * Sets the current Tool id.
     * @param id to set
     */
    public void setId(long id){
        this.id = id;
    }

    /**
     * Returns the electrical tool's title.
     * @return tool title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the Tool's specific title.
     * @param title to set
     */
    public void setTitle(String title){
        this.title = title;
    }

    /**
     * Returns the date the electrical tool was created.
     * @return the date the electrical tool was created
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the date the tool was created or modified.
     * @param date the electrical tool was created or modified
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Returns a basic description of electrical tool values and answer.
     * @return the electrical tool description answer
     */
    public String getSnapshot() {
        return snapshot;
    }

    /**
     * Sets the basic description of the electrical tool values and answer.
     * @param snapshot of the electrical tool values and answer
     */
    public void setSnapshot(String snapshot) {
        this.snapshot = snapshot;
    }

    /**
     * Returns the details of the electrical tool values and answer.
     * @return a String representation of the electrical tool.
     */
    public String getDetails() {
        return details;
    }

    /**
     * Sets the details of the electrical tool values and answer.
     * @param details of the electrical tool values and answer.
     */
    public void setDetails(String details) {
        this.details = details;
    }

}
