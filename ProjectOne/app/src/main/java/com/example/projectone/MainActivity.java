package com.example.projectone;

/*
By,
Name: Samihan Nandedkar
UIN:667142409
CS 478
Spring 2022
Project One
 */

import androidx.activity.result.*;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

// Main Activity Class for main page.
public class MainActivity<val, var> extends AppCompatActivity {


    protected Button firstButton ;
    protected String phoneNumber;
    protected Button secondButton ;

    // Default onCreate method.
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i("MainActivity","Starting the main activity fot the ProjectOne - Samihan Nandedkar");

        // Returning saved Instance State
        super.onCreate(savedInstanceState);
        // Setting the Layout for the main activity.
        setContentView(R.layout.activity_main);

        // Binding the UI components with the variables
        firstButton = (Button) findViewById(R.id.button);
        secondButton = (Button) findViewById(R.id.button2);

        // Setting up listeners for the buttons
        firstButton.setOnClickListener(secondPage);
        // Default listen to showcase the Toast message when the number is not yet entered.
        secondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("MainActivity","Dail Button is clicked before entering number. Error message is displayed");
                Toast.makeText(MainActivity.this,"Please Enter the Phone Number.",Toast.LENGTH_LONG).show();
            }
        });
    }

// Defining method for Enter Number Button. THis will redirect to new activity.
    public View.OnClickListener secondPage = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Log.i("MainActivity","First Button is clicked. Redirect to first Page.");
            switchActivity();
        }

    };

    // Defining the Result launcher whenever is redirected from first activity.
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.i("MainActivity","Redirected to Main activity from second activity");

                    // Condition to check the returned result code.
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Log.i("MainActivity","Result: RESULT_OK");
                        // Retriving the data from Intent
                        Intent data = result.getData();
                        okayResult(data);
                    }
                    else if (result.getResultCode() == Activity.RESULT_CANCELED){
                        Log.i("MainActivity","Result: RESULT_CANCELLED");
                        Intent data = result.getData();
                        phoneNumber=data.getStringExtra("number");
                        // Second Button Listener when the result is false. Error is displayed with number.
                        secondButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Log.i("MainActivity","The Number Entered is in incorrect format.");
                                String message = phoneNumber + " : Number in incorrect format.";
                                // Showcasing toast message.
                                Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                    else {
                        secondButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this,"Please Enter the Phone Number.",Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                }
            });


    // Defining method to switch to new activity.
    private void switchActivity() {
        Log.i("MainActivity","Switching to second activity.");
        // Creating intent to open SecondActivity
        Intent i = new Intent(this, SecondActivity.class) ;
        someActivityResultLauncher.launch(i);
    }

    // Defining the method to be called the RESULT_OK  is received.
    private  void okayResult(Intent data){
        phoneNumber=data.getStringExtra("number");
        // Listener for second number to redirect to dialer.
        secondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("MainActivity","Redirecting to dialer with the number: "+phoneNumber);
                // Creating a intent for ACTION_DIAL to redirect to default dialer of application.
                Intent intent = new Intent(Intent.ACTION_DIAL);
                String encodedNumber= Uri.encode(phoneNumber);
                Uri num = Uri.parse("tel:" + encodedNumber);
                // Setting the number in the intent.
                intent.setData(num);
                // starting the intent.
                startActivity(intent);
            }
        });
    }

}