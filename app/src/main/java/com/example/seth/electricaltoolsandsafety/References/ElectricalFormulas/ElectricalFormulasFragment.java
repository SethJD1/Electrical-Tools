package com.example.seth.electricaltoolsandsafety.References.ElectricalFormulas;

import com.example.seth.electricaltoolsandsafety.R;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;
import android.view.ViewGroup.LayoutParams;

/**
 * Displays a list of electrical formula drawings that the user can navigate through by buttons or
 * a drop down menu.
 */
public class ElectricalFormulasFragment extends Fragment {

    private View rootView;

    private ImageSwitcher formulaView;
    private TextView formulaLabel;
    private Button previousButton;
    private Button nextButton;

    private final int[] IMAGE_FILE_ID = {R.drawable.dc_series, R.drawable.dc_parallel,
            R.drawable.ac_series_capacitive, R.drawable.ac_series_inductive,
            R.drawable.ac_series_combined, R.drawable.ac_parallel_capacitive,
            R.drawable.ac_parallel_inductive, R.drawable.ac_parallel_combined,
            R.drawable.inductors, R.drawable.capacitors, R.drawable.power_factor,
            R.drawable.ac_values, R.drawable.ohms_law};

    private final String[] IMAGE_TEXT = {"DC Series Circuit", "DC Parallel Circuit",
            "AC Series Capacitive Circuit", "AC Series Inductive Circuit",
            "AC Series Combined Circuit", "AC Parallel Capacitive Circuit",
            "AC Parallel Inductive Circuit", "AC Parallel Combined Circuit", "Inductors",
            "Capacitors", "Power Factor", "AC Values", "Ohm's Law"};

    private int imageIndex = 0; // Current Index Number of the Image being displayed
    private int numberOfImageFiles = IMAGE_FILE_ID.length;

    /**
     * Default Constructor
     */
    public ElectricalFormulasFragment() {}

    /**
     * Creates and initializes all components of the electrical Formula fragment.
     *
     * @param inflater - fragment layout inflater
     * @param container - fragment container
     * @param savedInstanceState - fragment Bundle
     * @return main activity view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_electrical_formulas, container, false);

        // Sets Options Menu
        setHasOptionsMenu(true);
        getActivity().setTitle("Formulas");

        setupWidgets();

        setupFormulaViewer();
        formulaView.setOutAnimation(getContext(), android.R.anim.fade_out);
        formulaView.setInAnimation(getContext(), android.R.anim.fade_in);

        setPreviousButtonListener();
        setNextButtonListener();

        // Set Default Formula to display
        formulaView.setImageResource(IMAGE_FILE_ID[imageIndex]);
        formulaLabel.setText(IMAGE_TEXT[imageIndex]);

        return rootView;
    }

    /**
     * Setups the class widgets.
     */
    private void setupWidgets(){

        formulaLabel = (TextView) rootView.findViewById(R.id.formula_label);
        formulaView = (ImageSwitcher) rootView.findViewById(R.id.formula_image);
        previousButton = (Button) rootView.findViewById(R.id.previous);
        nextButton = (Button) rootView.findViewById(R.id.next);
    }

    /**
     * Creates the Options menu and set the initial icons.
     *
     * @param menu to inflate
     * @param inflater menu inflater.
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.app_bar_electrical_formulas, menu);
    }

    /**
     * Handles the menu item clicks.
     * @param item selected.
     * @return true
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()) {

            case R.id.dc_series:
                setSpecificImage(0);
                return true;
            case R.id.dc_parallel:
                setSpecificImage(1);
                return true;
            case R.id.ac_series_capacitive:
                setSpecificImage(2);
                return true;
            case R.id.ac_series_inductive:
                setSpecificImage(3);
                return true;
            case R.id.ac_series_combined:
                setSpecificImage(4);
                return true;
            case R.id.ac_parallel_capacitive:
                setSpecificImage(5);
                return true;
            case R.id.ac_parallel_inductive:
                setSpecificImage(6);
                return true;
            case R.id.ac_parallel_combined:
                setSpecificImage(7);
                return true;
            case R.id.inductors:
                setSpecificImage(8);
                return true;
            case R.id.capacitors:
                setSpecificImage(9);
                return true;
            case R.id.power_factor:
                setSpecificImage(10);
                return true;
            case R.id.ac_values:
                setSpecificImage(11);
                return true;
            case R.id.ohms_law:
                setSpecificImage(12);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Sets a specific formula image to display.
     */
    private void setSpecificImage(int imageIndex){

        this.imageIndex = imageIndex;

        formulaView.setImageResource(IMAGE_FILE_ID[imageIndex]);
        formulaLabel.setText(IMAGE_TEXT[imageIndex]);
        modifyButtonActions();

    }

    /**
     * Initialises the Formula viewer (ImageSwitcher) and sets the
     * default layout values.
     */
    private void setupFormulaViewer(){

        formulaView.setFactory(new ViewFactory(){

            @Override
            public View makeView(){

                ImageView switchImageView = new ImageView(getContext());
                switchImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                switchImageView.setLayoutParams(
                        new ImageSwitcher.LayoutParams(LayoutParams.MATCH_PARENT,
                                LayoutParams.MATCH_PARENT));

                return switchImageView;
            }
        });
    }

    /**
     * Setups the previous button listener. On each click, the previous button changes the image
     * to a lower index number in the array unless it reaches the beginning.
     */
    private void setPreviousButtonListener(){

        previousButton.setOnClickListener(new View.OnClickListener() {

            /**
             * @param view to monitor.
             */
            public void onClick(View view){

                if(imageIndex != 0){
                    imageIndex --;

                    formulaView.setImageResource(IMAGE_FILE_ID[imageIndex]);
                    formulaLabel.setText(IMAGE_TEXT[imageIndex]);
                }

                modifyButtonActions();
            }
        });
    }

    /**
     * Setups the next button listener. On each click, the next button changes the image
     * to a higher index number in the array unless it reaches the end.
     */
    private void setNextButtonListener(){

        nextButton.setOnClickListener(new View.OnClickListener() {

            /**
             * @param view to monitor.
             */
            public void onClick(View view){

                if(imageIndex < (numberOfImageFiles - 1)){
                    imageIndex ++;

                    formulaView.setOutAnimation(getContext(), android.R.anim.fade_out);
                    formulaView.setInAnimation(getContext(), android.R.anim.fade_in);


                    formulaView.setImageResource(IMAGE_FILE_ID[imageIndex]);
                    formulaLabel.setText(IMAGE_TEXT[imageIndex]);
                }

                modifyButtonActions();
            }
        });
    }

    /**
     * Changes the next and previous button states when based on the current Image that is
     * being displayed.
     */
    private void modifyButtonActions(){

        previousButton.setEnabled(imageIndex != 0);
        nextButton.setEnabled(imageIndex < numberOfImageFiles - 1);
    }
}

























