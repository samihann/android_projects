package com.example.projectone;

/*
By,
Name: Samihan Nandedkar
UIN:667142409
CS 478
Spring 2022
Project One
 */

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

// Second Activity class.
public class SecondActivity extends AppCompatActivity {

    protected EditText editTextView;
    // Defining Regex pattern to compare the input with.
    // Allowed format for 9 digit number.
    // *********
    // *** *** ***
    // (***) ***-***
    String regexPattern = "^(\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{3}$";

    // When Back button is pressed.
    @Override
    public void onBackPressed()
    {
        Log.i("SecondActivity","Back button is pressed instead of done.");
        // Setting default intent as no input is entered.
        Intent defaultIntent = new Intent();
        // Setting number as empty sting.
        defaultIntent.putExtra("number","");
        // Setting the result code as RESULT_CANCELLED
        setResult(Activity.RESULT_CANCELED,defaultIntent);
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("SecondActivity","Starting the second activity.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // binding the edit text variable with the UI component
        editTextView = (EditText) findViewById(R.id.editText);

        //listener for the Done/Return key for the editText
        editTextView.setOnEditorActionListener(editAction);
    }

    // Defining the listener for edit text component.
    public EditText.OnEditorActionListener editAction = new EditText.OnEditorActionListener(){
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

            // Condition when Done is entered on keyboard.
            if(i==KeyEvent.KEYCODE_ENTER || i== EditorInfo.IME_ACTION_DONE){
                Log.i("SecondActivity","Done/Return entered on keyboard.");

                // Condition to compare input text with regex patter to validate
                // if the entered phone number is in correct format.
                if(editTextView.getText().toString().matches(regexPattern)){
                    // The validation completed.
                    Log.i("SecondActivity","Number format validated successfully.");

                    // Creating intent to return to Main activity.
                    Intent validNumber = new Intent();

                    // Adding the number to the intent
                    validNumber.putExtra("number",editTextView.getText().toString());

                    // Setting the result code as RESULT_OK
                    setResult(Activity.RESULT_OK,validNumber);
                    finish();
                    return true;

                } else {
                    // Creating intent to return to Main activity.
                    Intent notValidNumber = new Intent();

                    // Adding the number to the intent
                    notValidNumber.putExtra("number", editTextView.getText().toString());

                    // Setting the result code as RESULT_CANCELLED
                    setResult(Activity.RESULT_CANCELED, notValidNumber);
                    finish();
                    return true;
                }
            }
            return false;
        }
    };
}