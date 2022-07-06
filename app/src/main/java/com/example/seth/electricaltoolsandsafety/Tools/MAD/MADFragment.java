package com.example.seth.electricaltoolsandsafety.Tools.MAD;

import com.example.seth.electricaltoolsandsafety.Utilities.DistanceConversion;
import com.example.seth.electricaltoolsandsafety.R;
import com.example.seth.electricaltoolsandsafety.Utilities.Utility;

import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Context;
import android.text.InputType;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Calculates the minimum approach distance with user inputs for the kilovolts and elevation. The
 * user has the option to use the internal GPS to automatically get the elevation or the it can be
 * entered manually. Additionally, the inputs and output can either be in meters or feet, depending
 * on the the users preference.
 */
public class MADFragment extends Fragment {

    private View rootView;
    private Context context;
    private MADImplementation madImpl;
    private MADTool tool;

    private EditText kilovoltsText;
    private EditText elevationText;
    private TextView elevationLabel;
    private TextView phaseToGroundExposure;
    private TextView phaseToPhaseExposure;

    private MenuItem enableGPS;

    private LocationData locationData;

    private final int LOWER_LIMIT = 0;
    private final int UPPER_LIMIT = 800;

    private double elevationAnswer;
    private final String ELEVATION_LABEL = "Elevation";
    private final String METER_LABEL = " (m)";
    private final String FEET_LABEL = " (ft.)";
    private final String BLANK = "";

    private boolean gpsEnabled;
    private boolean showGPSIcon;
    private boolean feetEnabled;
    private boolean metersEnabled;

    /**
     * Creates and initializes all components of the minimum approach distance fragment.
     *
     * @param inflater - fragment layout inflater
     * @param container - fragment container
     * @param savedInstanceState - fragment Bundle
     * @return main activity view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_minimum_approach_distance, container, false);
        context = getContext();

        madImpl = new MADImplementation(context);
        tool = new MADTool();

        getActivity().setTitle("MAD");

        // Default Menu Icon States
        gpsEnabled = false;
        showGPSIcon = false; // GPS Icon is invisible unless user allows Location Data.
        feetEnabled = true;
        metersEnabled = false;

        // Setup Widgets, GPS and Listeners
        setupWidgets();
        setUpLocationDataFeed();
        requestLocationDataPermission();

        setHasOptionsMenu(true);

        return rootView;
    }

    /**
     * Creates the Options menu and set the initial icons.
     *
     * @param menu to inflate
     * @param inflater menu inflater.
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.app_bar_minimum_approach_distance, menu);

        enableGPS = menu.findItem(R.id.enable_gps);

        // Color GPS Icon to Default off value
        Utility.tintMenuIcon(getContext(), enableGPS, R.color.action_items_light);
    }

    /**
     * Conditionally selects which option items to display based on the users preferences.
     *
     * @param menu to alter
     */
    @Override
    public void onPrepareOptionsMenu(Menu menu) {

        // Hides or Displays the GPS Icon based of the user Location Data preference
        if(!showGPSIcon) {
            menu.findItem(R.id.enable_gps).setVisible(false);
        } else {
            menu.findItem(R.id.enable_gps).setVisible(true);
        }
    }

    /**
     * Performs actions in the event a options menu item is selected.
     *
     * @param item that is selected
     * @return true is all circumstances
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){

            case R.id.enable_gps: // GPS Icon

                // Performs GPS functions when depressed
                if(!gpsEnabled){

                    // Change Icon color to Accent
                    Utility.tintMenuIcon(context, enableGPS, R.color.colorAccent);
                    gpsEnabled = true; // set new state
                    elevationAnswer = locationData.getElevation();

                    if(feetEnabled){ // Sets Elevation Edit Text to GPS Altitude in Feet

                        elevationText.setText(String.valueOf((int) DistanceConversion.convertMetersToFeet(elevationAnswer)));

                    } else if (metersEnabled) { // Sets Elevation Edit Text to GPS Altitude in Meters

                        elevationText.setText(String.valueOf((int) elevationAnswer));
                    }

                    // Disable Elevation Field Editing
                    elevationText.setInputType(InputType.TYPE_NULL);
                    elevationText.setFocusableInTouchMode(false);

                } else {

                    // Change Icon color to normal
                    Utility.tintMenuIcon(context, enableGPS, R.color.action_items_light);
                    gpsEnabled = false;

                    // Enables Elevation Field Editing
                    elevationText.setText(BLANK);
                    elevationText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    elevationText.setFocusableInTouchMode(true);
                }

                break;

            case R.id.feet: // Feet Text

                // Performs Feet Conversion when depressed
                if(!feetEnabled){

                    feetEnabled = true; // set new state
                    metersEnabled = false; // set new state
                    elevationLabel.setText(ELEVATION_LABEL + FEET_LABEL); // update elevation label

                    if(gpsEnabled){ // convert GPS elevationAnswer to feet and display

                        elevationText.setText(String.valueOf((int) DistanceConversion.convertMetersToFeet(elevationAnswer)));
                        validateInput(); // validate item and calculate values if valid

                    } else {

                        elevationText.setText(BLANK);
                    }
                }

                break;

            case R.id.meters: // Meters Text

                // Performs Meter Conversion when depressed
                if(!metersEnabled) {

                    metersEnabled = true; // set new state
                    feetEnabled = false; // set new state
                    elevationLabel.setText(ELEVATION_LABEL + METER_LABEL); // update elevation label

                    if(gpsEnabled){ // convert gps elevationAnswer to meters and display

                        elevationText.setText(String.valueOf((int) elevationAnswer));
                        validateInput(); // validate item and calculate values if valid

                    } else {

                        elevationText.setText(BLANK);
                    }
                }

                break;

            case R.id.add_to_list:

                madImpl.addToSavedEquationList();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Sets a GPS data feed from the support class.
     */
    private void setUpLocationDataFeed(){

        locationData = new LocationData(getActivity(), new LocationInterface() {

            @Override
            public void onLocationChange(double elevation) {

                if(gpsEnabled){

                    if(feetEnabled){
                        elevationText.setText(String.valueOf((int) DistanceConversion.convertMetersToFeet(elevationAnswer)));
                    } else {
                        elevationText.setText(String.valueOf((int) elevation));
                    }

                    elevationAnswer = elevation;
                }
            }

            @Override
            public void onLocationChange(double longitude, double elevation) {
                //TODO included for future use
            }

            @Override
            public void onLocationChange(double latitude, double longitude, double elevation) {
                //TODO included for future use
            }
        });
    }

    /**
     * Assigns and sets default values for all the Minimum Approach Distance Widgets.
     */
    private void setupWidgets() {

        kilovoltsText = (EditText) rootView.findViewById(R.id.kilovolts_answer);
        kilovoltsText.setText(BLANK);

        elevationText = (EditText) rootView.findViewById(R.id.elevation_answer);
        elevationText.setText(BLANK);

        phaseToPhaseExposure = (TextView) rootView.findViewById(R.id.phase_to_phase_answer);
        phaseToPhaseExposure.setText(BLANK);

        phaseToGroundExposure = (TextView) rootView.findViewById(R.id.phase_to_ground_answer);
        phaseToGroundExposure.setText(BLANK);

        elevationLabel = (TextView) rootView.findViewById(R.id.elevation_label);
        elevationLabel.setText(ELEVATION_LABEL + FEET_LABEL);

        kilovoltsText.addTextChangedListener(kilovoltsWatcher);
        elevationText.addTextChangedListener(elevationWatcher);
    }

    /**
     * Requests Location Data use from the user gather elevation data for the calcuation.
     */
    private void requestLocationDataPermission(){

        if(locationData.checkLocationDataPermission()){
            showGPSIcon = true; // Display GPS Icon
        }
    }

    /**
     * Implements a text watcher in the kilovolts field. Value must be in the designated range in
     * order for calculations to be performed.
     */
    private final TextWatcher kilovoltsWatcher = new TextWatcher(){

        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
        public void onTextChanged(CharSequence s, int start, int before, int count){}

        /**
         * Updates the text color and calculates the minimum approach distances if the fields are
         * valid.
         * @param number to validate
         */
        public void afterTextChanged(Editable number){

            if(Utility.convertStringToDouble(number.toString()) == LOWER_LIMIT
                    || Utility.convertStringToDouble(number.toString()) > UPPER_LIMIT){

                kilovoltsText.setTextColor(Color.RED);
                phaseToGroundExposure.setText(BLANK);
                phaseToPhaseExposure.setText(BLANK);

            } else {

                kilovoltsText.setTextColor(Color.WHITE);
                validateInput();
            }
        }
    };

    /**
     * Implements a text watcher in the elevation field. Value must be in the designated range in
     * order for calculations to be performed.
     */
    private final TextWatcher elevationWatcher = new TextWatcher(){

        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
        public void onTextChanged(CharSequence s, int start, int before, int count){}

        /**
         * Updates the text color and calculates the minimum approach distances if the fields are
         * valid.
         * @param number to validate
         */
        public void afterTextChanged(Editable number){

            if(number.toString().equals(BLANK)){

                phaseToGroundExposure.setText(BLANK);
                phaseToPhaseExposure.setText(BLANK);

            } else {

                validateInput();
            }
        }
    };

    /**
     * Validates the user inputs and performs the minimum approach distance calculation if the values
     * are valid.
     */
    private void validateInput(){

        final String FEET = "Feet";
        final String METERS = "Meters";

        tool.setElevation(Utility.convertStringToInt(elevationText.getText().toString()));
        tool.setKilovolts(Utility.convertStringToDouble(kilovoltsText.getText().toString()));

        if(feetEnabled){
            tool.setUnit(FEET);
        } else if(metersEnabled){
            tool.setUnit(METERS);
        }

        if(madImpl.hasValidInputs(tool)){

            madImpl.setBaseToolValues(tool);
            phaseToGroundExposure.setText(madImpl.getPhaseToGroundAnswer());
            phaseToPhaseExposure.setText(madImpl.getPhaseToPhaseAnswer());

        } else { // Clear fields if not valid

            phaseToGroundExposure.setText(BLANK);
            phaseToPhaseExposure.setText(BLANK);
        }
    }
}
