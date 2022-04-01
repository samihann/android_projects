package com.samihann.projectthree_a2_sam;

/***
 * By Samihan Nandedkar
 * Project Three
 * CS 478
 *
 * Restaurant Activity for A2 application.
 *
 *
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class RestaurantActivity extends AppCompatActivity {

    // Declaring the variables to retrieve the list from resources.
    public static String[] rList;
    public static String[] rWebsites;

    // Declaring the frame layout for two fragments.
    private FrameLayout listFragmentLayout;
    private FrameLayout webFragmentLayout;

    FragmentManager fragmentManager;

    private  RestaurantListFragment restaurantListFragment = new RestaurantListFragment() ;
    private RestaurantWebsiteFragment websiteFragment = new RestaurantWebsiteFragment();
    private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
    private static final String TAG = "RestaurantActivity";
    private static final String TAG_RESTAURANT_LIST_FRAGMENT = "RestaurantListFragment";
    private static final String TAG_RESTAURANT_WEBSITE_FRAGMENT = "WebsiteFragment";

    private ListViewModel mModel ;

    private Context context = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_restaurant);

        // Get the string arrays with the restaurant list and websites
        rList = getResources().getStringArray(R.array.Restaurants);
        rWebsites = getResources().getStringArray(R.array.rWebsites);


        context = this;
        // Defining the toolbar for the activity
        setSupportActionBar(findViewById(R.id.my_toolbar));

        // Get references to the Fragment layouts
        listFragmentLayout = (FrameLayout) findViewById(R.id.restaurant_fragment_container);
        webFragmentLayout = (FrameLayout) findViewById(R.id.rwebsite_fragment_container);

        // Get a reference to the SupportFragmentManager instead of original FragmentManager
        fragmentManager = getSupportFragmentManager();

        // Start a new FragmentTransaction
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        restaurantListFragment = (RestaurantListFragment) fragmentManager.findFragmentByTag(TAG_RESTAURANT_LIST_FRAGMENT);
        websiteFragment = (RestaurantWebsiteFragment) fragmentManager.findFragmentByTag(TAG_RESTAURANT_WEBSITE_FRAGMENT);

        if (restaurantListFragment == null){
            restaurantListFragment = new RestaurantListFragment();
            fragmentTransaction.replace(R.id.restaurant_fragment_container, restaurantListFragment,TAG_RESTAURANT_LIST_FRAGMENT);
            fragmentTransaction.commit();
        }
        else{

            // if fragment was retained, add in the respective container to display the fragment.
            fragmentTransaction.replace(R.id.restaurant_fragment_container, restaurantListFragment, TAG_RESTAURANT_LIST_FRAGMENT);
            fragmentTransaction.commit();
            // check if the webview fragment was also retained.
            if (websiteFragment == null) {
                websiteFragment = new RestaurantWebsiteFragment();
            }
            else{
                //if retained, add the fragment to display
                if (!websiteFragment.isAdded()) {

                    fragmentTransaction.replace(R.id.rwebsite_fragment_container, websiteFragment,TAG_RESTAURANT_LIST_FRAGMENT);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    fragmentManager.executePendingTransactions();

                }
            }
        }

        //if webview fragment was not created, create.
        if(websiteFragment == null){
            websiteFragment = new RestaurantWebsiteFragment();
        }

        // Add a OnBackStackChangedListener to reset the layout when the back stack changes
        fragmentManager.addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        setLayout();
                    }
                });

        mModel = new ViewModelProvider(this).get(ListViewModel.class) ;
        mModel.getSelectedItem().observe(this, item -> {
            if (!websiteFragment.isAdded()) {
                FragmentTransaction fragmentTransaction2 = fragmentManager.beginTransaction() ;

                fragmentTransaction2.replace(R.id.rwebsite_fragment_container,websiteFragment, TAG_RESTAURANT_WEBSITE_FRAGMENT);

                // Add this FragmentTransaction to the backstack
                fragmentTransaction2.addToBackStack(null);

                // Commit the FragmentTransaction
                fragmentTransaction2.commit();

                // Force Android to execute the committed FragmentTransaction
                fragmentManager.executePendingTransactions();
            }
        });
        setLayout() ;
    }

    private void setLayout() {

        // Determine whether the web fragment has been added
        if (!websiteFragment.isAdded()) {

            // Make the list fragment occupy the entire layout
            listFragmentLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    MATCH_PARENT, MATCH_PARENT));
            webFragmentLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                    MATCH_PARENT));
        } else {

            // Make the list take 1/3 of the layout's width
            listFragmentLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                    MATCH_PARENT, 1f));

            // Make the webview take 2/3's of the layout's width
            webFragmentLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                    MATCH_PARENT, 2f));
        }
    }

    public void onStart() {
        super.onStart();

        //set layout and show webview when fragments were retained after configuration change
        setLayout();
        websiteFragment.showWebViewIndex(restaurantListFragment.mCurrIdx);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);

        return true;
    }

    // Listener for options menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //check which item was clicked from options menu and execute respective process
        switch (item.getItemId()) {

            case R.id.landmarks: {
                Log.d("Menu", "This is landmark option");

                Intent intent1 = new Intent(context, LandmarkActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
                return true;
            }

            case R.id.restaurants: {
                Log.d("Menu", "This is restaurant option");

                Intent intent2 = new Intent(context, RestaurantActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent2);
                return true;
            }
            case R.id.home: {
                Intent intent3 = new Intent(RestaurantActivity.this, MainActivity.class);
                startActivity(intent3);
                return true;
            }
            default:
                return false;
        }

    }

}