package com.example.seth.electricaltoolsandsafety.Main;

import com.example.seth.electricaltoolsandsafety.R;
import com.example.seth.electricaltoolsandsafety.References.ElectricalFormulas.ElectricalFormulasFragment;
import com.example.seth.electricaltoolsandsafety.References.ReferenceList.ReferencesFragment;
import com.example.seth.electricaltoolsandsafety.References.SavedEquations.SavedEquationsFragment;
import com.example.seth.electricaltoolsandsafety.Tools.Conversions.ConversionsFragment;
import com.example.seth.electricaltoolsandsafety.Tools.MAD.MADFragment;
import com.example.seth.electricaltoolsandsafety.Tools.OhmsLaw.OhmsLawFragment;
import com.example.seth.electricaltoolsandsafety.Tools.PowerFactor.PowerFactorFragment;
import com.example.seth.electricaltoolsandsafety.Tools.Rigging.RiggingFragment;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;

/**
 * Creates the Main Activity for the Electrical Tools and Safety Application. All Tool and
 * References fragments are displayed from Main through a navigation menu selection.
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private MenuItem menuItem;

    private OhmsLawFragment ohmsLawFragment;
    private ConversionsFragment conversionsFragment;
    private MADFragment madFragment;
    private PowerFactorFragment powerFactorFragment;
    private RiggingFragment riggingFragment;
    private ElectricalFormulasFragment electricalFormulasFragment;
    private ReferencesFragment referencesFragment;
    private SavedEquationsFragment savedEquationsFragment;

    /**
     * Creates and setups the main activity and navigation drawer.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Locks the screen to Portrait mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Setup Navigation Drawer and Set to Full Screen
        setupNavigationMenu();

        // Creates and setups the Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Creates and setups the Navigation Drawer
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Creates and setups the navigation menu toggle.
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        setupFragments();
        setupDrawerListener();
        manageFragments(ohmsLawFragment); // set default activity to Ohm's Law

        drawer.openDrawer(GravityCompat.START);
    }

    /**
     * Sets up the Navigation Menu and sets it to a full screen view on startup.
     */
    private void setupNavigationMenu(){

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) navigationView.getLayoutParams();
        params.width = metrics.widthPixels;
        navigationView.setLayoutParams(params);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * Manages fragment transactions when navigation items are selected.
     *
     * @param fragment to initiate.
     */
    private void manageFragments(Fragment fragment){

        if(!fragment.isAdded()){
            manager = getSupportFragmentManager();
            transaction = manager.beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.replace(R.id.activityFragments,fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    /**
     * Setups up all the application fragments.
     */
    private void setupFragments(){

        ohmsLawFragment = new OhmsLawFragment();
        conversionsFragment = new ConversionsFragment();
        madFragment = new MADFragment();
        powerFactorFragment = new PowerFactorFragment();
        riggingFragment = new RiggingFragment();
        electricalFormulasFragment = new ElectricalFormulasFragment();
        referencesFragment = new ReferencesFragment();
        savedEquationsFragment = new SavedEquationsFragment();
    }

    /**
     * Navigates to previous displayed activities if the back button is depressed.
     */
    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(!drawer.isDrawerOpen(GravityCompat.START)){
            drawer.openDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Setups up Drawer Listener to navigate to the various fragments when selected.
     */
    private void setupDrawerListener(){

        drawer.addDrawerListener(new DrawerLayout.SimpleDrawerListener(){

            /**
             * AFter the drawer is closed, the fragment is displayed.
             * @param view
             */
            @Override
            public void onDrawerClosed(View view){

                super.onDrawerClosed(view);

                if(menuItem != null){
                    onNavigationItemSelected(menuItem);
                }
            }
        });
    }

    /**
     * Navigates to the selected tool or reference activity based of the user navigation
     * menu selection.
     * @param item to navigate to from the user selection
     * @return true
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        menuItem = null;

        // Closes the drawer, then displays the selected fragment.
        if(drawer.isDrawerOpen(GravityCompat.START)){

            menuItem = item;
            drawer.closeDrawers();
            return false;
        }

        // Navigates to the various application tool and reference fragment
        switch(id) {

            case R.id.nav_ohms_law:
                manageFragments(ohmsLawFragment);
                break;

            case R.id.nav_electrical_unit_conversions:
                manageFragments(conversionsFragment);
                break;

            case R.id.nav_minimum_approach_distance:
                manageFragments(madFragment);
                break;

            case R.id.nav_power_factor:
                manageFragments(powerFactorFragment);
                break;

            case R.id.nav_rigging:
                manageFragments(riggingFragment);
                break;

            case R.id.nav_electrical_formulas:
                manageFragments(electricalFormulasFragment);
                break;

            case R.id.nav_saved_equations:
                manageFragments(savedEquationsFragment);
                break;

            case R.id.nav_references:
                manageFragments(referencesFragment);
                break;
        }

        this.navigationView.setFocusable(true);

        return true;
    }
}
