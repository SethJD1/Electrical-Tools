package com.example.seth.electricaltoolsandsafety.Tools.Conversions;

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
import android.widget.Spinner;
import android.widget.TextView;

import com.example.seth.electricaltoolsandsafety.R;
import com.example.seth.electricaltoolsandsafety.Utilities.Utility;

import java.util.ArrayList;

/**
 * User interface for converting various electrical, distance and pressure conversions.
 */
public class ConversionsFragment extends Fragment {

    private View rootView;

    private ConversionsImplementation conversionsImpl;
    private ConversionsTool tool;

    private final String CONVERSIONS_TITLE = "Conversions";
    private final String BLANK = "";

    private EditText amount;
    private TextView answer;
    private TextView answerDetails;

    private Spinner conversionType;
    private Spinner fromUnit;
    private Spinner toUnit;

    private ArrayList<String> unitList;

    private ArrayAdapter<String> conversionTypeAdapter;
    private ArrayAdapter<String> unitAdapter;

    public ConversionsFragment() {} // Default Constructor

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

        rootView = inflater.inflate(R.layout.fragment_electrical_conversions, container, false);

        getActivity().setTitle(CONVERSIONS_TITLE); // Set Title
        setHasOptionsMenu(true); // Set Options Menu

        conversionsImpl = new ConversionsImplementation(getContext());
        tool = new ConversionsTool();

        setupWidgets(); // Setup interface widgets and default values

        // Initialize Listeners
        setupConversionTypeListener();
        setupFromUnitListener();
        setupToUnitListener();
        amount.addTextChangedListener(amountWatcher);

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
        inflater.inflate(R.menu.app_bar_conversions, menu);
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
                conversionsImpl.addToSavedEquationList();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Setup all fargment widgets and their respective components.
     */
    private void setupWidgets(){

        conversionType = (Spinner) rootView.findViewById(R.id.type);
        fromUnit = (Spinner) rootView.findViewById(R.id.from_quantity);
        toUnit = (Spinner) rootView.findViewById(R.id.to_quantity);

        amount = (EditText) rootView.findViewById(R.id.amount);

        answer = (TextView) rootView.findViewById(R.id.result);
        answerDetails = (TextView) rootView.findViewById(R.id.result_details);

        setConversionTypeList();
        setFromAndToUnitLists();
    }

    /**
     * Sets the default values on startup.
     */
    private void setDefaultStartUpValues(){
        conversionType.setSelection(0); // Set to Electrical Conversions
        amount.setText(BLANK);
        answer.setText(BLANK);
        answerDetails.setText(BLANK);
    }

    /**
     * Sets up the conversion type listener.
     */
    private void setupConversionTypeListener(){

        conversionType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            /**
             * Resets the unit lists, amount and answer.
             */
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selected = conversionType.getSelectedItem().toString();
                changeFromAndToUnitLists(selected);
                amount.setText(BLANK);
                answer.setText(BLANK);
                answerDetails.setText(BLANK);
            }

            public void onNothingSelected(AdapterView<?> parent){}
        });
    }

    /**
     * sets up the from unit listener.
     */
    private void setupFromUnitListener(){

        fromUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            /**
             * Validates input on selection.
             */
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                validateInput();
            }

            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setupToUnitListener(){

        toUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            /**
             * Validates input on selection.
             */
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                validateInput();
            }

            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    /**
     * Validates the amount and calculates the answer if valid.
     */
    private final TextWatcher amountWatcher = new TextWatcher(){

        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
        public void onTextChanged(CharSequence s, int start, int before, int count){}

        /**
         * Validate the amount entered. If the value is greater than zero, the answer is calculated,
         * otherwise the answer is set to blank.
         * @param number to check.
         */
        public void afterTextChanged(Editable number){

            if(Utility.convertStringToDouble(number.toString()) <= 0){
                amount.setTextColor(Color.RED);
                answer.setText(BLANK);
                answerDetails.setText(BLANK);
            } else {
                amount.setTextColor(Color.WHITE);
                validateInput();
            }
        }
    };

    /**
     * Validates all user inputs and calculates the equation if inputs are valid.
     */
    private void validateInput(){

        try{

            tool.setConversionType(conversionType.getSelectedItem().toString());
            tool.setAmount(Utility.convertStringToDouble(amount.getText().toString()));
            tool.setFromUnit(fromUnit.getSelectedItem().toString());
            tool.setToUnit(toUnit.getSelectedItem().toString());

            if(conversionsImpl.hasValidInputs(tool)){
                conversionsImpl.setBaseToolValues(tool);
                answer.setText(conversionsImpl.getAnswer());
                answerDetails.setText(conversionsImpl.getAnswerDetails());
            } else {
                answer.setText(BLANK);
                answerDetails.setText(BLANK);
            }
        } catch(NullPointerException e){
            answer.setText(BLANK);
            answerDetails.setText(BLANK);
        }
    }

    /**
     * Sets the conversion type list.
     */
    private void setConversionTypeList(){

        conversionTypeAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.conversion_type_custom_spinner, conversionsImpl.getConversionTypes());
        conversionType.setAdapter(conversionTypeAdapter);
        conversionTypeAdapter.notifyDataSetChanged();
    }

    /**
     * Sets the unit list for both the from and to lists.
     */
    private void setFromAndToUnitLists(){

        unitList = new ArrayList<>();
        unitAdapter = new ArrayAdapter<>(getActivity(),R.layout.conversion_spinner_centered_text,
                unitList);

        fromUnit.setAdapter(unitAdapter);
        toUnit.setAdapter(unitAdapter);

        fromUnit.setSelection(conversionsImpl.getDefaultUnitPosition());
        toUnit.setSelection(conversionsImpl.getDefaultUnitPosition());

        unitAdapter.notifyDataSetChanged();
    }

    /**
     * Changes the unit list to a different list, based on the conversion type.
     * @param conversion type selected.
     */
    private void changeFromAndToUnitLists(String conversion){

        unitAdapter.clear();
        unitAdapter.addAll(conversionsImpl.modifyUnitList(conversion));
        unitAdapter.notifyDataSetChanged();

        fromUnit.setSelection(conversionsImpl.getDefaultUnitPosition());
        toUnit.setSelection(conversionsImpl.getDefaultUnitPosition());
    }
}
