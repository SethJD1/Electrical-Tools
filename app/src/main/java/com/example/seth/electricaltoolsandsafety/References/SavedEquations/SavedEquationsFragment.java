package com.example.seth.electricaltoolsandsafety.References.SavedEquations;

import com.example.seth.electricaltoolsandsafety.R;
import com.example.seth.electricaltoolsandsafety.Utilities.DatabaseManager;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import androidx.appcompat.app.AlertDialog;

/**
 * Displays a list of saved equations that can be deleted by the user.
 */
public class SavedEquationsFragment extends Fragment {

    private View rootView;

    private DatabaseManager databaseManager;
    private ListView listView;
    private ListAdapter adapter;
    private Cursor cursor;

    /**
     * Default Constructor
     */
    public SavedEquationsFragment() {}

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
        rootView = inflater.inflate(R.layout.fragment_saved_equations, container, false);

        getActivity().setTitle("Saved Equations");

        setHasOptionsMenu(true);

        listView = (ListView) rootView.findViewById(R.id.saved_equations);
        databaseManager = new DatabaseManager(getContext());

        cursor = databaseManager.getToolCursor();

        adapter = new SimpleCursorAdapter(
                getContext(), R.layout.white_text_list, cursor,
                new String[] {DatabaseManager.TITLE_FIELD,
                        DatabaseManager.SNAPSHOT_FIELD},
                new int[] {R.id.title_line, R.id.snapshot_line},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        setupListListener();

        return rootView;
    }

    /**
     * Creates the Options menu.
     * @param menu to inflate
     * @param inflater menu inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.app_bar_saved_equations, menu);
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

            case R.id.clear_list:

                final String TITLE = "Please Confirm";
                final String MESSAGE = "Are you sure you want to delete all the saved equations?";
                final String POSITIVE = "Yes";
                final String NEGATIVE = "No";

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                alertDialog.setTitle(TITLE);
                alertDialog.setMessage(MESSAGE);

                alertDialog.setPositiveButton(POSITIVE, new DialogInterface.OnClickListener(){

                    /**
                     * Deletes all the database contents if confirmed by the user.
                     * @param dialog to the user
                     * @param positiveButton pressed
                     */
                    public void onClick(DialogInterface dialog, int positiveButton) {

                        databaseManager.deleteAll();
                        cursor = databaseManager.getToolCursor();

                        adapter = new SimpleCursorAdapter(
                                getContext(), R.layout.white_text_list, cursor,
                                new String[] {DatabaseManager.TITLE_FIELD,
                                        DatabaseManager.SNAPSHOT_FIELD},
                                new int[] {R.id.title_line, R.id.snapshot_line},
                                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

                        listView.setAdapter(adapter);

                        dialog.dismiss();
                    }
                });

                alertDialog.setNegativeButton(NEGATIVE, new DialogInterface.OnClickListener(){

                    /**
                     * Dismiss the Alert Dialog. No actions are performed.
                     * @param dialog to the user
                     * @param negativeButton pressed
                     */
                    public void onClick(DialogInterface dialog, int negativeButton) {
                        dialog.dismiss(); // do nothing but dismiss alert dialog
                    }
                });

                alertDialog.create();
                alertDialog.show();

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Displays a saved equation when the user clicks one on the list.
     */
    private void setupListListener(){

        listView.setOnItemClickListener(

                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                        //ResultsFragment results = new ResultsFragment();

                        //Bundle args = new Bundle();
                        //args.putLong("id", id);
                        //results.setArguments(args);

                        //FragmentManager manager = getFragmentManager();
                        //FragmentTransaction transaction = manager.beginTransaction();
                        //transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        //transaction.replace(R.id.activityFragments,results);
                        //transaction.addToBackStack(null);
                        //transaction.commit();
                    }
                });

        listView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
    }
}
