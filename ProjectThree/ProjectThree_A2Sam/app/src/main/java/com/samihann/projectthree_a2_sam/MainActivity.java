package com.samihann.projectthree_a2_sam;

/***
 * By Samihan Nandedkar
 * Project Three
 * CS 478
 *
 * Main Activity for A2 application.
 *
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    // Declaring the receiver for Landmark activity
    private LandmarkReceiver lReceiver;
    private IntentFilter lFilter;
    private static final String LANDMARK_ACTION = "com.samihann.projectthree.landmark";

    // Declaring the receiver for Restaurant Activity
    private RestaurantReceiver rReceiver;
    private IntentFilter rFilter;
    private static final String RESTAURANT_ACTION = "com.samihann.projectthree.restaurant";

    private Context context = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        // Adding  custom Toolbar
        setSupportActionBar(findViewById(R.id.my_toolbar));


        // Defining the receiver for the Landmark broadcast.
        lReceiver = new LandmarkReceiver();
        lFilter = new IntentFilter(LANDMARK_ACTION);
        lFilter.setPriority(10);
        registerReceiver(lReceiver, lFilter);


        // Defining the receiver for the Landmark broadcast.
        rReceiver = new RestaurantReceiver();
        rFilter = new IntentFilter(RESTAURANT_ACTION);
        rFilter.setPriority(10);
        registerReceiver(rReceiver, rFilter);

    }

    // Creating and inflating the overflow option menu.
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
                Intent intent1 = new Intent(MainActivity.this, LandmarkActivity.class);
                context.startActivity(intent1);
                return true;
            }

            case R.id.restaurants: {
                Log.d("Menu", "This is restaurant option");
                Intent intent2 = new Intent(MainActivity.this, RestaurantActivity.class);
                startActivity(intent2);
                return true;
            }
            case R.id.home: {
                Intent intent3 = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent3);
                return true;
            }

            default:
                return false;
        }
    }
}