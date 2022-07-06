package com.example.seth.electricaltoolsandsafety.Tools.Rigging;

import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.seth.electricaltoolsandsafety.R;
import com.example.seth.electricaltoolsandsafety.Utilities.Utility;

import java.util.ArrayList;

/**
 * User interface for finding the weight applied to each sling on a load.
 */
public class RiggingFragment extends Fragment {

    private View rootView;

    private RiggingImplementation riggingImpl;
    private RiggingTool tool;

    private final String RIGGING_TITLE = "Rigging";
    private final String BLANK = "";

    private ImageView slingImage;
    private Spinner numberOfSlings;
    private EditText slingLength;
    private EditText slingHeight;
    private EditText loadWeight;
    private TextView loadPerSling;

    private ArrayList<Integer> slingNumbersArray;
    private ArrayAdapter<Integer> slingNumberAdapter;

    public RiggingFragment(){}

    /**
     * Setups and Initializes the layout, widgets abnd listeners.
     * @param inflater - fragment layout inflater
     * @param container - fragment container
     * @param savedInstanceState - fragment Bundle
     * @return main activity view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_rigging_lifting, container, false);

        getActivity().setTitle(RIGGING_TITLE); // Set Menu Title
        setHasOptionsMenu(true); // Set Options Menu

        riggingImpl = new RiggingImplementation(getContext());
        tool = new RiggingTool();

        setupWidgets(); // Setup interface widgets and default values
        setSlingAdapterList();

        // Initialize Listeners
        setupNumberOfSlingsListener();
        slingLength.addTextChangedListener(slingLengthWatcher);
        slingHeight.addTextChangedListener(slingHeightWatcher);
        loadWeight.addTextChangedListener(loadWeightWatcher);

        setDefaultStartUpValues(); // Set Default Quantity on Startup

        return rootView;
    }

    /**
     * Creates the Options menu.
     * @param menu to inflate
     * @param inflater menu inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.app_bar_rigging, menu);
    }

    /**
     * Performs the specific action when the user clicks a menu icon.
     * @param item clicked
     * @return boolean value if item is clicked
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch(id) {

            case R.id.add_to_list:
                riggingImpl.addToSavedEquationList();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Setups up the Number of Sling Listener.
     */
    private void setupNumberOfSlingsListener(){

        numberOfSlings.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            /**
             * Sets an image of the rigging configuration based off the user selection.
             */
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                final int TWO_SLINGS = 0;
                final int THREE_SLINGS = 1;
                final int FOUR_SLINGS = 2;

                switch(position){
                    case TWO_SLINGS:
                        slingImage.setImageResource(R.drawable.two_slings);
                        break;
                    case THREE_SLINGS:
                        slingImage.setImageResource(R.drawable.three_slings);
                        break;
                    case FOUR_SLINGS:
                        slingImage.setImageResource(R.drawable.four_slings);
                }

                validateInput();
            }

            public void onNothingSelected(AdapterView<?> arg0){}
        });
    }

    /**
     * Validates the sling length value.
     */
    private final TextWatcher slingLengthWatcher = new TextWatcher(){

        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
        public void onTextChanged(CharSequence s, int start, int before, int count){}

        /**
         * Validates the sling length value; must be greater than zero to be valid.
         * @param number
         */
        public void afterTextChanged(Editable number){

            final int LOW_VALUE = 0;
            double value = Utility.convertStringToDouble(slingLength.getText().toString());

            if(value > LOW_VALUE){
                slingLength.setTextColor(Color.WHITE);
                validateInput();

            } else {
                slingLength.setTextColor(Color.RED);
                loadPerSling.setText(BLANK);
            }
        }
    };

    /**
     * Validates the sling height value.
     */
    private final TextWatcher slingHeightWatcher = new TextWatcher(){

        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
        public void onTextChanged(CharSequence s, int start, int before, int count){}

        /**
         * Validates the sling height value; must be greater than zero and must be less than the
         * sling length.
         * @param number to validate.
         */
        public void afterTextChanged(Editable number){

            final int LOW_VALUE = 0;
            double length = Utility.convertStringToDouble(slingLength.getText().toString());
            double height = Utility.convertStringToDouble(slingHeight.getText().toString());

            if(height > LOW_VALUE && height < length){
                slingHeight.setTextColor(Color.WHITE);
                validateInput();

            } else {
                slingHeight.setTextColor(Color.RED);
                loadPerSling.setText(BLANK);
            }
        }
    };

    /**
     * Validate the load weight value.
     */
    private final TextWatcher loadWeightWatcher = new TextWatcher(){

        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
        public void onTextChanged(CharSequence s, int start, int before, int count){}

        /**
         * Validates the load weight value; must be greater than zero.
         * @param number to validate
         */
        public void afterTextChanged(Editable number){

            final int LOW_VALUE = 0;
            double value = Utility.convertStringToDouble(number.toString());

            if(value > LOW_VALUE){
                loadWeight.setTextColor(Color.WHITE);
                validateInput();

            } else {
                loadWeight.setTextColor(Color.RED);
                loadPerSling.setText(BLANK);
            }
        }
    };

    /**
     * Verifies all values are valid. If all values are valid, the answer if calculated and
     * displayed. If not, the answer is reset.
     */
    private void validateInput(){

        try{

            tool.setNumberOfSlings((int) numberOfSlings.getSelectedItem());
            tool.setSlingLength(Utility.convertStringToDouble(slingLength.getText().toString()));
            tool.setSlingHeight(Utility.convertStringToDouble(slingHeight.getText().toString()));
            tool.setLoadWeight(Utility.convertStringToDouble(loadWeight.getText().toString()));

            if(riggingImpl.hasValidInputs(tool)){
                riggingImpl.setBaseToolValues(tool);
                loadPerSling.setText(riggingImpl.getAnswer());
            } else {
                loadPerSling.setText(BLANK);
            }

        } catch(NullPointerException e){
            loadPerSling.setText(BLANK);
        }
    }

    /**
     * Setups all the user interface widgets.
     */
    private void setupWidgets(){

        slingNumbersArray = new ArrayList<>();

        slingLength = (EditText) rootView.findViewById(R.id.sling_length);
        slingHeight = (EditText) rootView.findViewById(R.id.sling_height);
        loadWeight = (EditText) rootView.findViewById(R.id.load_weight);
        loadPerSling = (TextView) rootView.findViewById(R.id.load_per_sling);
        slingImage = (ImageView) rootView.findViewById(R.id.sling_image);

        numberOfSlings = (Spinner) rootView.findViewById(R.id.number_of_slings);

        // Set Sling Number Adapter
        slingNumberAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.rigging_spinner_centered_text, slingNumbersArray);
        numberOfSlings.setAdapter(slingNumberAdapter);
        slingNumberAdapter.notifyDataSetChanged();
    }

    /**
     * Setup all the sling values.
     */
    private void setSlingAdapterList(){

        slingNumberAdapter.addAll(RiggingCalculator.getSlingNumbers());
        slingNumberAdapter.notifyDataSetChanged();
    }

    /**
     * Sets all the values on startup.
     */
    private void setDefaultStartUpValues(){

        slingImage.setImageResource(R.drawable.two_slings);

        numberOfSlings.setSelection(0);

        // Set all values to blank
        slingLength.setText(BLANK);
        slingHeight.setText(BLANK);
        loadWeight.setText(BLANK);
        loadPerSling.setText(BLANK);
    }
}
