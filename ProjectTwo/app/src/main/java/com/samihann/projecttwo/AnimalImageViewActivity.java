package com.samihann.projecttwo;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/********************************************************
 * By,
 * Samihan Nandedkar
 *
 * CS 478: Project Two
 * Spring 2022
 *
 * Second activity to view image for the animal.       *
 *******************************************************/

public class AnimalImageViewActivity extends AppCompatActivity {

    // declare the variables.
    ImageView imageView;
    TextView textView;
    String selectedName;
    int selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the layout for the activity
        setContentView(R.layout.activity_animal_image_view);

        // Initialize imageView & textView
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.tvName);

        // Get intent data sent from the parent activity.
        Intent intent = getIntent();

        if(intent.getExtras() != null){
            selectedName = intent.getStringExtra("name");
            selectedImage = intent.getIntExtra("image",0);

            // Set values textView & imageView
            textView.setText(selectedName);
            imageView.setImageResource(selectedImage);
        }

        // Define the on click listener for the image.
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(AnimalImageViewActivity.this,AnimalDetailsActivity.class).putExtra("name",selectedName).putExtra("image",selectedImage));
            }
        });


    }
}