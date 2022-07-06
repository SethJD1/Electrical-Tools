package com.example.seth.electricaltoolsandsafety.Tools.OhmsLaw;

import com.example.seth.electricaltoolsandsafety.R;
import com.example.seth.electricaltoolsandsafety.Utilities.Utility;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Spinner;
import android.graphics.Color;
import android.text.TextWatcher;
import android.text.Editable;
import java.util.ArrayList;
import android.view.MenuItem;

/**
 * Ohm's Law Fragment calculates an electrical quantity value based off user selections for two other
 * electrical quantities. Unit values are optional for user input, as well as the computed quantity
 * value.
 */
public class OhmsLawFragment extends Fragment {

    private View rootView;
    private OhmsLawImplementation ohmsLawImpl;
    private OhmsLawTool tool;

    private Button wattsButton;
    private Button voltsButton;
    private Button ampsButton;
    private Button ohmsButton;

    private Spinner unitOneList;
    private Spinner unitTwoList;
    private Spinner unitAnswerList;

    private Spinner quantityOneList;
    private Spinner quantityTwoList;

    private EditText valueOneText;
    private EditText valueTwoText;
    private TextView valueAnswerText;

    private ArrayList<String> unitListArray;
    private ArrayList<String> quantityListArray;
    private ArrayList<String> unitAnswerArray;

    private ArrayAdapter<String> unitAdapter;
    private ArrayAdapter<String> quantityAdapter;
    private ArrayAdapter<String> unitAnswerAdapter;

    private String selectedQuantity;
    private final String BLANK = "";

    /**
     * Default Constructor
     */
    public OhmsLawFragment() {}

    /**
     * Setups and Initializes the layout, widgets and listeners.
     * @param inflater - fragment layout inflater
     * @param container - fragment container
     * @param savedInstanceState - fragment Bundle
     * @return main activity view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_ohms_law, container, false);

        getActivity().setTitle("Ohm's Law"); // Set Menu Title
        setHasOptionsMenu(true); // Set Options Menu

        ohmsLawImpl = new OhmsLawImplementation(getContext());
        tool = new OhmsLawTool();

        setupWidgets(); // Setup interface widgets and default values
        setUnitList(); // Setup Lists

        // Initialize Listeners
        wattsButtonListener();
        voltsButtonListener();
        ampsButtonListener();
        ohmsButtonListener();
        oneQuantityListener();
        twoQuantityListener();
        electricalUnitOneListener();
        electricalUnitTwoListener();
        electricalUnitAnswerListener();
        setupValueListener(); // Initialize Value TextWatchers

        setDefaultStartUpValues();

        return rootView;
    }

    /**
     * Creates the Options menu.
     * @param menu to inflate
     * @param inflater menu inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.app_bar_ohms_law, menu);
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
                ohmsLawImpl.addToSavedEquationList();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Setup all fragment widgets and their respective components.
     */
    private void setupWidgets(){

        unitListArray = new ArrayList<>();
        quantityListArray = new ArrayList<>();
        unitAnswerArray = new ArrayList<>();

        // Setup Ohms Law Buttons
        wattsButton = (Button) rootView.findViewById(R.id.watts_button);
        voltsButton = (Button) rootView.findViewById(R.id.volts_button);
        ampsButton = (Button) rootView.findViewById(R.id.amps_button);
        ohmsButton = (Button) rootView.findViewById(R.id.ohms_button);

        // Setup Electrical Units Spinners
        unitOneList = (Spinner) rootView.findViewById(R.id.electrical_units_top);
        unitTwoList = (Spinner) rootView.findViewById(R.id.electrical_units_bottom);
        unitAnswerList = (Spinner) rootView.findViewById(R.id.electrical_units_answer);

        // Setup Electrical Quantity Spinners and TextView
        quantityOneList = (Spinner) rootView.findViewById(R.id.electrical_quantity_top);
        quantityTwoList = (Spinner) rootView.findViewById(R.id.electrical_quantity_bottom);

        // Setup Electrical Quantity Value EditText and TextField
        valueOneText = (EditText) rootView.findViewById(R.id.electrical_value_top);
        valueTwoText = (EditText) rootView.findViewById(R.id.electrical_value_bottom);
        valueAnswerText = (TextView) rootView.findViewById(R.id.electrical_value_answer);

        // Set Unit Adapter
        unitAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, unitListArray);
        unitOneList.setAdapter(unitAdapter);
        unitTwoList.setAdapter(unitAdapter);
        unitAdapter.notifyDataSetChanged();

        // Set Unit Answer Adapter
        unitAnswerAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, unitAnswerArray);
        unitAnswerList.setAdapter(unitAnswerAdapter);
        unitAnswerAdapter.notifyDataSetChanged();

        // Set Quantity Adapter
        quantityAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, quantityListArray);
        quantityOneList.setAdapter(quantityAdapter);
        quantityTwoList.setAdapter(quantityAdapter);
        quantityAdapter.notifyDataSetChanged();

    }

    /**
     * Sets the default values and attributes for the fragment widgets.
     */
    private void setDefaultValues(){

        setQuantityList(); // Update Quantity Lists

        // Set electrical unit drop downs to base unit.
        final int DEFAULT_ELECTRICAL_UNIT = unitAdapter.getPosition("Base");
        unitOneList.setSelection(DEFAULT_ELECTRICAL_UNIT);
        unitTwoList.setSelection(DEFAULT_ELECTRICAL_UNIT);
        unitAnswerList.setSelection(4);

        // Set Electrical Values to Blank Value
        valueOneText.setText(BLANK);
        valueTwoText.setText(BLANK);
        valueAnswerText.setText(BLANK);

        setDefaultButtonTextColor(); // Set All Quantity Buttons to Default Text Color
    }

    /**
     * Watts Button Listener updates the units and quantity lists based on the selection of the
     * Watts Button.
     */
    private void wattsButtonListener(){

        wattsButton.setOnClickListener(new View.OnClickListener() {

            /**
             * Remove the Watts value from the list of possible choices in the electrical quantity
             * list. Sets the all widgets to default values.
             * @param view to monitor.
             */
            public void onClick(View view){

                setDefaultValues();
                wattsButton.setTextColor(Color.RED);
                modifyElectricalQuantityList(wattsButton.getText().toString());
                modifyElectricalUnitAnswerList(wattsButton.getText().toString());
                selectedQuantity = wattsButton.getText().toString();
            }
        });
    }

    /**
     * Volts Button Listener updates the units and quantity lists based on the selection of the
     * Volts Button.
     */
    private void voltsButtonListener(){

        voltsButton.setOnClickListener(new View.OnClickListener() {

            /**
             * Remove the Volts value from the list of possible choices in the electrical quantity
             * list. Sets the all widgets to default values.
             * @param view to monitor.
             */
            public void onClick(View view){

                setDefaultValues();
                voltsButton.setTextColor(Color.RED);
                modifyElectricalQuantityList(voltsButton.getText().toString());
                modifyElectricalUnitAnswerList(voltsButton.getText().toString());
                selectedQuantity = voltsButton.getText().toString();
            }
        });
    }

    /**
     * Amps Button Listener updates the units and quantity lists based on the selection of the
     * Amps Button.
     */
    private void ampsButtonListener(){

        ampsButton.setOnClickListener(new View.OnClickListener() {

            /**
             * Remove the Amps value from the list of possible choices in the electrical quantity
             * list. Sets the all widgets to default values.
             * @param view to monitor.
             */
            public void onClick(View view){

                setDefaultValues();
                ampsButton.setTextColor(Color.RED);
                modifyElectricalQuantityList(ampsButton.getText().toString());
                modifyElectricalUnitAnswerList(ampsButton.getText().toString());
                selectedQuantity = ampsButton.getText().toString();
            }
        });
    }

    /**
     * Ohms Button Listener updates the units and quantity lists based on the selection of the
     * Ohms Button.
     */
    private void ohmsButtonListener(){

        ohmsButton.setOnClickListener(new View.OnClickListener() {

            /**
             * Remove the Ohms value from the list of possible choices in the electrical quantity
             * list. Sets the all widgets to default values.
             * @param view to monitor.
             */
            public void onClick(View view){

                setDefaultValues();
                ohmsButton.setTextColor(Color.RED);
                modifyElectricalQuantityList(ohmsButton.getText().toString());
                modifyElectricalUnitAnswerList(ohmsButton.getText().toString());
                selectedQuantity = ohmsButton.getText().toString();
            }
        });
    }

    /**
     * Recalculates the answer on new top unit selection if all fields are valid.
     */
    private void electricalUnitOneListener(){

        unitOneList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            /**
             * Validates user entry on item selection.
             */
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l){
                validateInput();
            }

            public void onNothingSelected(AdapterView<?> arg0){}
        });
    }

    /**
     * Recalculates the answer on new bottom unit selection if all fields are valid.
     */
    private void electricalUnitTwoListener(){

        unitTwoList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            /**
             * Validates user entry on item selection.
             */
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l){
                validateInput();
            }

            public void onNothingSelected(AdapterView<?> arg0){}
        });
    }

    /**
     * Recalculates the answer on new answer unit selection if all fields are valid.
     */
    private void electricalUnitAnswerListener(){

        unitAnswerList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            /**
             * Validates user entry on item selection.
             */
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l){
                validateInput();
            }

            public void onNothingSelected(AdapterView<?> arg0){}
        });
    }

    /**
     * Validates the electrical one value and calculates the answer if all fields are valid.
     */
    private final TextWatcher oneValidationWatcher = new TextWatcher(){

        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
        public void onTextChanged(CharSequence s, int start, int before, int count){}

        /**
         * Updates the text color, action button and validates input when text is changed in the
         * valueOneText field.
         * @param number to check.
         */
        public void afterTextChanged(Editable number){

            if(Utility.convertStringToDouble(number.toString()) == 0){
                valueOneText.setTextColor(Color.RED);
                valueAnswerText.setText(BLANK);
            } else {
                valueOneText.setTextColor(Color.WHITE);
                validateInput();
            }
        }
    };

    /**
     * Validates the electrical two value and calculates the answer if all fields are valid.
     */
    private final TextWatcher twoValidationWatcher = new TextWatcher(){

        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
        public void onTextChanged(CharSequence s, int start, int before, int count){}

        /**
         * Updates the text color, action button and validates input when text is changed in the
         * valueTwoText field.
         * @param number to check.
         */
        public void afterTextChanged(Editable number){

            if(Utility.convertStringToDouble(number.toString()) == 0){
                valueTwoText.setTextColor(Color.RED);
                valueAnswerText.setText(BLANK);
            } else {
                valueTwoText.setTextColor(Color.WHITE);
                validateInput();
            }
        }
    };

    /**
     * Number one Quantity Listener validates the top quantity by verifying its not the same quantity as
     * the number two value. If it is a match, the text color changes and the answer will not be
     * calculated.
     */
    private void oneQuantityListener(){

        quantityOneList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            /**
             * Validates user entry on item selection.
             */
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l){

                if(quantityOneList.getSelectedItem().toString().equals(quantityTwoList.getSelectedItem().toString())){

                    ((TextView)parent.getChildAt(0)).setTextColor(Color.RED);
                    valueAnswerText.setText(BLANK);

                } else {

                    ((TextView)parent.getChildAt(0)).setTextColor(Color.WHITE);
                    TextView temp = (TextView) quantityTwoList.getChildAt(0);
                    temp.setTextColor(Color.WHITE);
                    validateInput();
                }
            }

            public void onNothingSelected(AdapterView<?> arg0){}
        });
    }

    /**
     * Number Two Quantity Listener validates the bottom quantity by verifying its not the same
     * quantity as the one value. If it is a match, the text color changes and the answer will not be
     * calculated.
     */
    private void twoQuantityListener(){

        quantityTwoList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            /**
             * Validates user entry on item selection.
             */
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l){

                if(quantityTwoList.getSelectedItem().toString().equals(quantityOneList.getSelectedItem().toString())){

                    ((TextView)parent.getChildAt(0)).setTextColor(Color.RED);
                    valueAnswerText.setText(BLANK);

                } else {

                    ((TextView)parent.getChildAt(0)).setTextColor(Color.WHITE);
                    TextView temp = (TextView) quantityOneList.getChildAt(0);
                    temp.setTextColor(Color.WHITE);
                    validateInput();
                }
            }

            public void onNothingSelected(AdapterView<?> arg0){}
        });
    }

    /**
     * Setups and assigns a listener to both electrical value text fields to monitor values
     * less than or equal to 0.
     */
    private void setupValueListener(){

        valueOneText.addTextChangedListener(oneValidationWatcher);
        valueTwoText.addTextChangedListener(twoValidationWatcher);
    }

    /**
     * Validates all four user inputs for electrical quantities and values. If either of the four
     * widgets have invalid values, no calculation will occur.
     */
    private void validateInput(){

        try{

            String unitTemp = unitAnswerList.getSelectedItem().toString();

            tool.setUnitOne(unitOneList.getSelectedItem().toString());
            tool.setUnitTwo(unitTwoList.getSelectedItem().toString());
            tool.setUnitAnswer(unitTemp.replace(selectedQuantity.toLowerCase(), ""));

            tool.setValueOne(Utility.convertStringToDouble(valueOneText.getText().toString()));
            tool.setValueTwo(Utility.convertStringToDouble(valueTwoText.getText().toString()));

            tool.setQuantityOne(quantityOneList.getSelectedItem().toString());
            tool.setQuantityTwo(quantityTwoList.getSelectedItem().toString());
            tool.setQuantityAnswer(selectedQuantity);

            if(ohmsLawImpl.hasValidInputs(tool)){
                ohmsLawImpl.setBaseToolValues(tool);
                valueAnswerText.setText(ohmsLawImpl.getAnswer());
            } else {
                valueAnswerText.setText(BLANK);
            }

        } catch(NullPointerException e){
            valueAnswerText.setText(BLANK);
        }
    }

    /**
     * Modifies the electrical quantity list based on which button the user depresses.
     * @param selectedQuantity to solve for
     */
    private void modifyElectricalQuantityList(String selectedQuantity){

        quantityAdapter.clear();
        quantityAdapter.addAll(ohmsLawImpl.removeQuantity(selectedQuantity));
        quantityAdapter.notifyDataSetChanged();

        quantityOneList.setSelection(0);
        quantityTwoList.setSelection(1);
    }

    /**
     * Modifies the electrical unit with the user selected quantity.
     * @param electricalQuantity
     */
    private void modifyElectricalUnitAnswerList(String electricalQuantity){

        unitAnswerArray.clear();
        unitAnswerAdapter.clear();
        unitAnswerAdapter.addAll(ohmsLawImpl.updatedUnitAnswerList(electricalQuantity));
        unitAnswerAdapter.notifyDataSetChanged();
        unitAnswerList.setSelection(3);
    }

    /**
     * Add all the quantity items to the list and sets the default values.
     */
    private void setQuantityList(){

        quantityListArray.clear();
        quantityAdapter.clear();
        quantityAdapter.addAll(ohmsLawImpl.getElectricalQuantities());
        quantityAdapter.notifyDataSetChanged();
        quantityOneList.setSelection(0);
        quantityTwoList.setSelection(1);
    }

    /**
     * Add all the unit items to the list and sets the default value to base unit.
     */
    private void setUnitList(){

        final String DEFAULT_UNIT = "Base";
        unitAdapter.clear();
        unitAdapter.addAll(ohmsLawImpl.getElectricalUnits());
        unitAdapter.notifyDataSetChanged();
        unitOneList.setSelection(unitAdapter.getPosition(DEFAULT_UNIT));
        unitTwoList.setSelection(unitAdapter.getPosition(DEFAULT_UNIT));
    }

    /**
     * Sets the Ohm's law button text color to black.
     */
    private void setDefaultButtonTextColor(){

        ohmsButton.setTextColor(getResources().getColor(R.color.colorDefaultDarkText));
        ampsButton.setTextColor(getResources().getColor(R.color.colorDefaultDarkText));
        wattsButton.setTextColor(getResources().getColor(R.color.colorDefaultDarkText));
        voltsButton.setTextColor(getResources().getColor(R.color.colorDefaultDarkText));
    }

    private void setDefaultStartUpValues(){

        // Set Default Quantity on Startup
        setDefaultValues();
        wattsButton.setTextColor(Color.RED);
        modifyElectricalQuantityList(wattsButton.getText().toString());
        modifyElectricalUnitAnswerList(wattsButton.getText().toString());
        selectedQuantity = wattsButton.getText().toString();
    }
}
