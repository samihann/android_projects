package com.samihann.projecttwo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/********************************************************
 * By,
 * Samihan Nandedkar
 *
 * CS 478: Project Two
 * Spring 2022
 *
 * Third activity to view details for the animal.       *
 *******************************************************/

public class AnimalDetailsActivity extends AppCompatActivity {

    // declare the variables.
    ImageView imageView;
    TextView textView, textView6,textView7,textView8, textView9, textView10, textView11;
    String selectedName, randomFact, lifespan, weight, habitat, endangered, url  ;
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setting the content view for the activity
        setContentView(R.layout.activity_animal_details);

        // Initialize imageView, textView & button.
        imageView = findViewById(R.id.imageView2);
        textView = findViewById(R.id.textView);
        textView6 = findViewById(R.id.textView6);
        textView7 = findViewById(R.id.textView7);
        textView8 = findViewById(R.id.textView8);
        textView9 = findViewById(R.id.textView9);
        textView11 = findViewById(R.id.textView11);
        mButton = findViewById(R.id.button);



        // Get intent data sent from the parent activity.
        Intent intent = getIntent();

        if(intent.getExtras() != null){
            selectedName = intent.getStringExtra("name");
            int selectedImage = intent.getIntExtra("image",0);

            // Set values textView & imageView
            textView.setText(selectedName);
            imageView.setImageResource(selectedImage);
        }

        // Parse through json file for the details for animals.
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONObject currentAnimal = obj.getJSONObject(selectedName);
            randomFact = currentAnimal.getString("randomFact");
            lifespan = currentAnimal.getString("lifespan");
            weight = currentAnimal.getString("weight");
            habitat = currentAnimal.getString("habitat");
            endangered = currentAnimal.getString("endangered");
            url = currentAnimal.getString("url");

            textView6.setText(lifespan);
            textView7.setText(weight);
            textView8.setText(endangered);
            textView9.setText(habitat);
            textView11.setText(randomFact);

        }
        catch (JSONException e){
            e.printStackTrace();
        }

        // Defining the button on click listener.
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("animal-details.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


}