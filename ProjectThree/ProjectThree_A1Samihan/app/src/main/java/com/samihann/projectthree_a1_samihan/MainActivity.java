package com.samihann.projectthree_a1_samihan;

/***
 * By Samihan Nandedkar
 * Project Three
 * CS 478
 *
 * Main Activity for A1 application.
 *
 */

import static android.content.Intent.FLAG_INCLUDE_STOPPED_PACKAGES;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    protected Button restaurantButtons ;
    protected Button attractionButtons ;

    // Actions for the broadcast to be sent.
    private static final String RESTAURANT_ACTION = "com.samihann.projectthree.restaurant";
    private static final String LANDMARK_ACTION = "com.samihann.projectthree.landmark";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        restaurantButtons = (Button) findViewById(R.id.button);
        attractionButtons = (Button) findViewById(R.id.button2);

        // Defining the landmark button listener
        attractionButtons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"A implicit broadcast for Landmark Activity is sent across. Please go into recents and open app A2.",Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.setAction(LANDMARK_ACTION);
                intent.setFlags(FLAG_INCLUDE_STOPPED_PACKAGES);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                sendBroadcast(intent);
                finish();
            }
        });

        restaurantButtons.setOnClickListener(resButton);
    }

    // defining the restaurant button listener
    public View.OnClickListener resButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(MainActivity.this,"A implicit broadcast for Restaurant Activity is sent across. Please go into recents and open app A2.",Toast.LENGTH_LONG).show();
            Intent intent = new Intent();
            intent.setAction(RESTAURANT_ACTION);
            intent.setFlags(FLAG_INCLUDE_STOPPED_PACKAGES);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            sendBroadcast(intent);
            finish();
        }

    };
}