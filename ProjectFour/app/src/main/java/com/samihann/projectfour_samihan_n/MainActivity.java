package com.samihann.projectfour_samihan_n;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    // Declare the button to start the game.
    private Button start_game_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Associating the declared button with the layout
        start_game_button = findViewById(R.id.button);


        // Adding a listener to the button to redirect to appropriate class when pressed.
        start_game_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open CONTINUOUS MODE
                Intent intent = new Intent(MainActivity.this, GopherGameActivity.class);
                startActivity(intent);
            }
        });
    }
}