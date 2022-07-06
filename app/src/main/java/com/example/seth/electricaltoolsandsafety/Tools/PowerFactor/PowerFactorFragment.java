package com.example.seth.electricaltoolsandsafety.Tools.PowerFactor;

import com.example.seth.electricaltoolsandsafety.R;
import com.example.seth.electricaltoolsandsafety.Utilities.Utility;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import android.view.View.OnClickListener;
import java.util.ArrayList;

/**
 * Power Factor Fragment calculates a quantity based off the user selection for two other power
 * factor electrical quantities. Unit values are optional for user input, as well as, computed
 * quantity value.
 */
public class PowerFactorFragment extends Fragment {

    private View rootView;

    private PowerFactorImplementation powerFactorImpl;
    private PowerFactorTool tool;

    private ViewSwitcher viewSwitcher;

    private Button varButtonSmall;
    private Button varButtonLarge;
    private Button vaButtonSmall;
    private Button vaButtonLarge;
    private Button wattsButtonSmall;
    private Button wattsButtonLarge;
    private Button anglesButtonSmall;
    private Button anglesButtonLarge;

    private OnClickListener buttonOnClickListener;
    private AdapterView.OnItemSelectedListener listOnItemSelectedListener;

    private ImageView powerFactorTriangle;

    private Spinner unitOne;
    private Spinner unitTwo;
    private Spinner quantityOne;
    private Spinner quantityTwo;
    private EditText valueOne;
    private EditText valueTwo;
    private Spinner unitAnswer;
    private TextView answer;

    private ArrayList<String> unitArray;
    private ArrayAdapter<String> unitAdapter;

    private ArrayList<String> quantityArray;
    private ArrayAdapter<String> quantityAdapter;

    private ArrayList<String> unitAnswerArray;
    private ArrayAdapter<String> unitAnswerAdapter;

    private String selectedQuantity;
    private int currentView;

    private final String BLANK = "";
    private final String DEFAULT_UNIT = "Base";
    private final String POWER_FACTOR = "Power Factor";
    private final String ANGLE = "Angle";

    /**
     * Default Constructor
     */
    public PowerFactorFragment() {}

    /**
     * Setups and Initializes the the layout, widgets and listeners.
     * @param inflater - fragment layout inflater
     * @param container - fragment container
     * @param savedInstanceState - fragment Bundle
     * @return main activity view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_power_factor, container, false);

        getActivity().setTitle(POWER_FACTOR); // Set Menu Title
        setHasOptionsMenu(true); // Set Options Menu

        powerFactorImpl = new PowerFactorImplementation(getContext());
        tool = new PowerFactorTool();

        setupWidgets(); // Setup interface widgets and default values
        setUnitList(); // Setup Lists

        // Initialize Listeners
        buttonListener();
        unitListener();
        valueOne.addTextChangedListener(oneValidationWatcher);
        valueTwo.addTextChangedListener(twoValidationWatcher);
        oneQuantityListener();
        twoQuantityListener();

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
        inflater.inflate(R.menu.app_bar_power_factor, menu);
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

            case R.id.swap_view:
                if(currentView == 0){
                    viewSwitcher.showNext();
                    currentView = 1;
                } else {
                    viewSwitcher.showPrevious();
                    currentView = 0;
                }
                break;

            case R.id.add_to_list:
                powerFactorImpl.addToSavedEquationList();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Setups the unit spinner listener.
     */
    private void unitListener(){

        listOnItemSelectedListener = new AdapterView.OnItemSelectedListener() {

            /**
             * Validates user entries on item selection.
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                validateInput();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        };

        // Add unit listeners to each unit list
        unitOne.setOnItemSelectedListener(listOnItemSelectedListener);
        unitTwo.setOnItemSelectedListener(listOnItemSelectedListener);
        unitAnswer.setOnItemSelectedListener(listOnItemSelectedListener);
    }

    /**
     * Setups and button listener for each of the four electrical quantities.
     */
    private void buttonListener(){

        final String VAR = "VAR";
        final String VA = "VA";
        final String WATTS = "Watts";
        final String ANGLE = "Angle";

        buttonOnClickListener = new OnClickListener() {

            /**
             * Updates the units, quantities, and picture based off the button selection.
             * @param v
             */
            @Override
            public void onClick(View v) {

                Button temp = (Button) v.findViewById(v.getId());
                String text = temp.getText().toString();

                switch(text){

                    case VA:

                        if(!selectedQuantity.equals(text)){

                            setDefaultValues();
                            modifySelectableOptions(text);
                            vaButtonSmall.setTextColor(Color.RED);
                            vaButtonLarge.setTextColor(Color.RED);
                            powerFactorTriangle.setImageResource(R.drawable.power_factor_triangle_va);
                        }
                        break;

                    case VAR:

                        if(!selectedQuantity.equals(varButtonSmall.getText().toString())){

                            setDefaultValues();
                            modifySelectableOptions(text);
                            varButtonSmall.setTextColor(Color.RED);
                            varButtonLarge.setTextColor(Color.RED);
                            powerFactorTriangle.setImageResource(R.drawable.power_factor_triangle_var);
                        }
                        break;

                    case WATTS:

                        if(!selectedQuantity.equals(wattsButtonSmall.getText().toString())){

                            setDefaultValues();
                            modifySelectableOptions(text);
                            wattsButtonSmall.setTextColor(Color.RED);
                            wattsButtonLarge.setTextColor(Color.RED);
                            powerFactorTriangle.setImageResource(R.drawable.power_factor_triangle_watts);
                        }
                        break;

                    case ANGLE:

                        if(!selectedQuantity.equals(anglesButtonSmall.getText().toString())){

                            setDefaultValues();
                            modifySelectableOptions(text);
                            anglesButtonSmall.setTextColor(Color.RED);
                            anglesButtonLarge.setTextColor(Color.RED);
                            powerFactorTriangle.setImageResource(R.drawable.power_factor_triangle_angle);
                        }
                        break;
                }
            }
        };

        // Sets the Button Listener to each of the buttons
        varButtonSmall.setOnClickListener(buttonOnClickListener);
        varButtonLarge.setOnClickListener(buttonOnClickListener);
        vaButtonSmall.setOnClickListener(buttonOnClickListener);
        vaButtonLarge.setOnClickListener(buttonOnClickListener);
        wattsButtonSmall.setOnClickListener(buttonOnClickListener);
        wattsButtonLarge.setOnClickListener(buttonOnClickListener);
        anglesButtonSmall.setOnClickListener(buttonOnClickListener);
        anglesButtonLarge.setOnClickListener(buttonOnClickListener);
    }

    /**
     * Validates the top quantity value and calculates the answer if all fields are valid.
     */
    private final TextWatcher oneValidationWatcher = new TextWatcher(){

        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
        public void onTextChanged(CharSequence s, int start, int before, int count){}


        public void afterTextChanged(Editable number){

            double value = Utility.convertStringToDouble(valueOne.getText().toString());
            String quantity = quantityOne.getSelectedItem().toString();

            if(powerFactorImpl.isValueValid(quantity, value)
                    && powerFactorImpl.areQuantitiesValid(quantity, value,
                    quantityTwo.getSelectedItem().toString(),
                    Utility.convertStringToDouble(valueTwo.getText().toString()))){

                valueOne.setTextColor(Color.WHITE);
                validateInput();

            } else {
                valueOne.setTextColor(Color.RED);
                answer.setText(BLANK);
            }
        }
    };

    /**
     * Validate the bottom quantity value and calculates the answer if all fields are valid.
     */
    private final TextWatcher twoValidationWatcher = new TextWatcher(){

        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
        public void onTextChanged(CharSequence s, int start, int before, int count){}

        public void afterTextChanged(Editable number){

            double value = Utility.convertStringToDouble(number.toString());
            String quantity = quantityTwo.getSelectedItem().toString();

            if(powerFactorImpl.isValueValid(quantity, value)
                    && powerFactorImpl.areQuantitiesValid(quantity, value,
                    quantityOne.getSelectedItem().toString(),
                    Utility.convertStringToDouble(valueOne.getText().toString()))){

                valueTwo.setTextColor(Color.WHITE);
                validateInput();

            } else {
                valueTwo.setTextColor(Color.RED);
                answer.setText(BLANK);
            }
        }
    };

    /**
     * Setups and the top quantity listener and validates the quantity by verifying its not the same
     * as the bottom quantity, they are not both angles, and whether the value is valid.
     */
    private void oneQuantityListener(){

        quantityOne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            /**
             * Validates user entry on item selection.
             */
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l){

                String quantityOneString = quantityOne.getSelectedItem().toString();

                // Changes the Layout if the Quantity is an Angle
                if(powerFactorImpl.isAngleSelected(quantityOneString)){
                    unitOne.setVisibility(View.GONE);
                    unitOne.setSelection(unitAdapter.getPosition(DEFAULT_UNIT));
                } else {
                    unitOne.setVisibility(View.VISIBLE);
                }

                // Validates if the bottom and top quantity are not both angles.
                if(quantityOne.getSelectedItem().toString().equals(quantityTwo.getSelectedItem().toString())){

                    ((TextView)parent.getChildAt(0)).setTextColor(Color.RED);
                    answer.setText(BLANK);

                } else if (quantityOne.getSelectedItem().toString().contains(ANGLE)
                        && quantityTwo.getSelectedItem().toString().contains(ANGLE)) {

                    ((TextView)parent.getChildAt(0)).setTextColor(Color.RED);
                    answer.setText(BLANK);

                } else if (quantityOne.getSelectedItem().toString().contains(POWER_FACTOR)
                        && quantityTwo.getSelectedItem().toString().contains(ANGLE)){

                    ((TextView)parent.getChildAt(0)).setTextColor(Color.RED);
                    answer.setText(BLANK);

                } else if (quantityOne.getSelectedItem().toString().contains(ANGLE)
                        && quantityTwo.getSelectedItem().toString().contains(POWER_FACTOR)){

                    ((TextView)parent.getChildAt(0)).setTextColor(Color.RED);
                    answer.setText(BLANK);

                } else {
                    ((TextView)parent.getChildAt(0)).setTextColor(Color.WHITE);
                    TextView temp = (TextView) quantityTwo.getChildAt(0);
                    temp.setTextColor(Color.WHITE);
                }

                // Validates if the quantity is valid for the specific value.
                if(powerFactorImpl.isValueValid(quantityOne.getSelectedItem().toString(),
                        Utility.convertStringToDouble(valueOne.getText().toString()))){
                    valueOne.setTextColor(Color.WHITE);
                    validateInput();
                } else {
                    valueOne.setTextColor(Color.RED);
                    answer.setText(BLANK);
                }
            }

            public void onNothingSelected(AdapterView<?> arg0){}
        });
    }

    /**
     * Setups the bottom quantity listener and validates the quantity by verifying its not the same
     * as the top quantity, they are not both angles, and whether the value is valid.
     */
    private void twoQuantityListener(){

        quantityTwo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            /**
             * Validates user entry on item selection.
             */
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l){

                String quantityTwoString = quantityTwo.getSelectedItem().toString();

                // Changes the Layout if the Quantity is an Angle
                if(powerFactorImpl.isAngleSelected(quantityTwoString)){
                    unitTwo.setVisibility(View.GONE);
                    unitTwo.setSelection(unitAdapter.getPosition(DEFAULT_UNIT));
                } else {
                    unitTwo.setVisibility(View.VISIBLE);
                }

                // Validates if the bottom and top quantity are not both angles.
                if(quantityOne.getSelectedItem().toString().equals(quantityTwo.getSelectedItem().toString())){

                    ((TextView)parent.getChildAt(0)).setTextColor(Color.RED);
                    answer.setText(BLANK);

                } else if (quantityOne.getSelectedItem().toString().contains(ANGLE)
                        && quantityTwo.getSelectedItem().toString().contains(ANGLE)) {

                    ((TextView)parent.getChildAt(0)).setTextColor(Color.RED);
                    answer.setText(BLANK);

                } else if (quantityOne.getSelectedItem().toString().contains(POWER_FACTOR)
                        && quantityTwo.getSelectedItem().toString().contains(ANGLE)){

                    ((TextView)parent.getChildAt(0)).setTextColor(Color.RED);
                    answer.setText(BLANK);

                } else if (quantityOne.getSelectedItem().toString().contains(ANGLE)
                        && quantityTwo.getSelectedItem().toString().contains(POWER_FACTOR)){

                    ((TextView)parent.getChildAt(0)).setTextColor(Color.RED);
                    answer.setText(BLANK);

                } else {
                    ((TextView)parent.getChildAt(0)).setTextColor(Color.WHITE);
                    TextView temp = (TextView) quantityTwo.getChildAt(0);
                    temp.setTextColor(Color.WHITE);
                }

                // Validates if the quantity is valid for the specific value.
                if(powerFactorImpl.isValueValid(quantityTwo.getSelectedItem().toString(),
                        Utility.convertStringToDouble(valueTwo.getText().toString()))){
                    valueTwo.setTextColor(Color.WHITE);
                    validateInput();
                } else {
                    valueTwo.setTextColor(Color.RED);
                    answer.setText(BLANK);
                }
            }

            public void onNothingSelected(AdapterView<?> arg0){}
        });
    }

    /**
     * Initializes and setups up the user interface widgets.
     */
    private void setupWidgets(){

        viewSwitcher = (ViewSwitcher) rootView.findViewById(R.id.view_switcher);

        unitArray = new ArrayList<>();
        quantityArray = new ArrayList<>();
        unitAnswerArray = new ArrayList<>();

        powerFactorTriangle = (ImageView) rootView.findViewById(R.id.power_factor_image);

        varButtonSmall = (Button) rootView.findViewById(R.id.var_button_small);
        vaButtonSmall = (Button) rootView.findViewById(R.id.va_button_small);
        wattsButtonSmall = (Button) rootView.findViewById(R.id.watts_button_small);
        anglesButtonSmall = (Button) rootView.findViewById(R.id.angle_button_small);

        varButtonLarge = (Button) rootView.findViewById(R.id.var_button_large);
        vaButtonLarge = (Button) rootView.findViewById(R.id.va_button_large);
        wattsButtonLarge = (Button) rootView.findViewById(R.id.watts_button_large);
        anglesButtonLarge = (Button) rootView.findViewById(R.id.angle_button_large);

        unitOne = (Spinner) rootView.findViewById(R.id.unit_one);
        unitTwo = (Spinner) rootView.findViewById(R.id.unit_two);
        quantityOne = (Spinner) rootView.findViewById(R.id.quantity_one);
        quantityTwo = (Spinner) rootView.findViewById(R.id.quantity_two);

        valueOne = (EditText) rootView.findViewById(R.id.value_one);
        valueTwo = (EditText) rootView.findViewById(R.id.value_two);

        unitAnswer = (Spinner) rootView.findViewById(R.id.unit_answer);
        answer = (TextView) rootView.findViewById(R.id.answer);

        // Set Unit Adapter
        unitAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, unitArray);
        unitOne.setAdapter(unitAdapter);
        unitTwo.setAdapter(unitAdapter);
        unitAdapter.notifyDataSetChanged();

        // Set Unit Answer Adapter
        unitAnswerAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, unitAnswerArray);
        unitAnswer.setAdapter(unitAnswerAdapter);
        unitAnswerAdapter.notifyDataSetChanged();

        // Set Quantity Adapter
        quantityAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, quantityArray);
        quantityOne.setAdapter(quantityAdapter);
        quantityTwo.setAdapter(quantityAdapter);
        quantityAdapter.notifyDataSetChanged();
    }

    /**
     * Sets the default UI values.
     */
    private void setDefaultValues(){

        setQuantityList();

        // Set unit drop downs to base unit.
        final int DEFAULT_ELECTRICAL_UNIT = unitAdapter.getPosition("Base");
        unitOne.setSelection(DEFAULT_ELECTRICAL_UNIT);
        unitTwo.setSelection(DEFAULT_ELECTRICAL_UNIT);

        // Set values to blank
        valueOne.setText(BLANK);
        valueTwo.setText(BLANK);
        answer.setText(BLANK);

        setDefaultButtonTextColor();
    }

    /**
     * Modifies the the UI Lists based on the user selected quantity.
     * @param selectedQuantity
     */
    private void modifySelectableOptions(String selectedQuantity){

        this.selectedQuantity = selectedQuantity;

        modifyPowerFactorQuantityList(selectedQuantity);
        modifyUnitAnswerList(selectedQuantity);

    }

    /**
     * Sets the default calculation value on startup.
     */
    private void setDefaultStartUpValues(){

        // Set Default Quantity on Startup
        setDefaultValues();
        selectedQuantity = varButtonSmall.getText().toString();
        varButtonSmall.setTextColor(Color.RED);
        varButtonLarge.setTextColor(Color.RED);
        powerFactorTriangle.setImageResource(R.drawable.power_factor_triangle_var);
        modifyPowerFactorQuantityList(varButtonSmall.getText().toString());
        modifyUnitAnswerList(varButtonSmall.getText().toString());

    }

    /**
     * Validates all four user inputs for power factor quantities and values. If either of the four
     * widgets have invalid values, no calculation will occur.
     */
    private void validateInput(){

        try{

            String unitTemp = unitAnswer.getSelectedItem().toString();

            tool.setUnitOne(unitOne.getSelectedItem().toString());
            tool.setUnitTwo(unitTwo.getSelectedItem().toString());

            if(selectedQuantity.equals(ANGLE)){
                tool.setUnitAnswer(unitTemp);
            } else if(selectedQuantity.equals(unitTemp)){
                tool.setUnitAnswer(unitTemp);
            } else if(selectedQuantity.equals("Watts")){
                tool.setUnitAnswer(unitTemp.replace(selectedQuantity.toLowerCase(), ""));
            } else {
                tool.setUnitAnswer(unitTemp.replaceAll(selectedQuantity, BLANK));
            }

            tool.setValueOne(Utility.convertStringToDouble(valueOne.getText().toString()));
            tool.setValueTwo(Utility.convertStringToDouble(valueTwo.getText().toString()));

            tool.setQuantityOne(quantityOne.getSelectedItem().toString());
            tool.setQuantityTwo(quantityTwo.getSelectedItem().toString());
            tool.setQuantityAnswer(selectedQuantity);

            if(powerFactorImpl.hasValidInputs(tool)){
                powerFactorImpl.setBaseToolValues(tool);
                answer.setText(powerFactorImpl.getAnswer());
            } else {
                answer.setText(BLANK);
            }

        } catch(NullPointerException e){
            answer.setText(BLANK);
        }
    }

    /**
     * Add all the unit items to the list and sets the default value to base unit.
     */
    private void setUnitList(){

        unitAdapter.clear();
        unitAdapter.addAll(powerFactorImpl.getElectricalUnits());
        unitAdapter.notifyDataSetChanged();
        unitOne.setSelection(unitAdapter.getPosition(DEFAULT_UNIT));
        unitTwo.setSelection(unitAdapter.getPosition(DEFAULT_UNIT));
    }


    /**
     * Add all the quantity items to the list and sets the default values.
     */
    private void setQuantityList(){

        quantityArray.clear();
        quantityAdapter.clear();
        quantityAdapter.addAll(powerFactorImpl.getPowerFactorQuantities());
        quantityAdapter.notifyDataSetChanged();
        quantityOne.setSelection(0);
        quantityTwo.setSelection(1);
    }

    /**
     * Modifies the power factor quantity list based on which button the user depresses.
     * @param selectedQuantity to solve for
     */
    private void modifyPowerFactorQuantityList(String selectedQuantity){

        quantityArray.clear();
        quantityAdapter.clear();
        quantityAdapter.addAll(powerFactorImpl.removeQuantity(selectedQuantity));
        quantityAdapter.notifyDataSetChanged();

        quantityOne.setSelection(0);
        quantityTwo.setSelection(1);
    }

    /**
     * Modifies the electrical unit with the user selected quantity.
     * @param powerFactorQuantity
     */
    private void modifyUnitAnswerList(String powerFactorQuantity){

        unitAnswerAdapter.clear();
        unitAnswerAdapter.addAll(powerFactorImpl.updatedUnitAnswerList(powerFactorQuantity));
        unitAnswerAdapter.notifyDataSetChanged();

        if(powerFactorQuantity.contains(ANGLE)){
            unitAnswer.setSelection(0);
        } else {
            unitAnswer.setSelection(3);
        }

        modifyPowerFactorQuantityList(selectedQuantity);

    }

    /**
     * Sets the power factor button text color to default color.
     */
    private void setDefaultButtonTextColor(){

        vaButtonSmall.setTextColor(getResources().getColor(R.color.colorDefaultDarkText));
        varButtonSmall.setTextColor(getResources().getColor(R.color.colorDefaultDarkText));
        wattsButtonSmall.setTextColor(getResources().getColor(R.color.colorDefaultDarkText));
        anglesButtonSmall.setTextColor(getResources().getColor(R.color.colorDefaultDarkText));

        vaButtonLarge.setTextColor(getResources().getColor(R.color.colorDefaultDarkText));
        varButtonLarge.setTextColor(getResources().getColor(R.color.colorDefaultDarkText));
        wattsButtonLarge.setTextColor(getResources().getColor(R.color.colorDefaultDarkText));
        anglesButtonLarge.setTextColor(getResources().getColor(R.color.colorDefaultDarkText));
    }
}
