package com.example.seth.electricaltoolsandsafety.References.ReferenceList;

import com.example.seth.electricaltoolsandsafety.R;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ReferencesFragment extends Fragment {

    private View rootView;

    private ListView references;
    private ArrayAdapter<String> adapter;

    private ArrayList<String> referenceTitles;
    private ArrayList<String> webSites;

    public ReferencesFragment() {}

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

        rootView = inflater.inflate(R.layout.fragment_references, container, false);

        getActivity().setTitle("References"); // Set Options Menu Title

        setWebSites();
        adapter = new ArrayAdapter<>(getActivity(), R.layout.reference_list, referenceTitles);

        references = (ListView) rootView.findViewById(R.id.referenceList);
        references.setAdapter(adapter);
        setupReferenceListener();

        return rootView;
    }

    /**
     * Setups a the reference ListView listener.
     */
    private void setupReferenceListener(){

        references.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            /**
             * Navigates to the reference items website.
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(webSites.get(position)));
                startActivity(intent);
            }
        });
    }

    /**
     * Adds the ListView Titles and their respective websites.
     */
    private void setWebSites(){

        referenceTitles = new ArrayList<>();
        referenceTitles.add("OSHA 1910.269 - Power Generation, Transmission and Distribution");
        referenceTitles.add("OSHA 1926.500 subpart M - Fall Protection");
        referenceTitles.add("OSHA 1926.251 - Rigging Equipment");
        referenceTitles.add("NIST - International System of Units");

        webSites = new ArrayList<>();
        webSites.add("https://www.osha.gov/pls/oshaweb/owadisp.show_document?p_table=STANDARDS&p_id=9868");
        webSites.add("https://www.osha.gov/pls/oshaweb/owadisp.show_document?p_table=STANDARDS&p_id=10756");
        webSites.add("https://www.osha.gov/pls/oshaweb/owadisp.show_document?p_table=STANDARDS&p_id=10686");
        webSites.add("http://www.nist.gov/customcf/get_pdf.cfm?pub_id=200349");
    }
}

