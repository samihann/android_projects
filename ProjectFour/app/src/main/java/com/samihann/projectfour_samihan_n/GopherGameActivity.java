package com.samihann.projectfour_samihan_n;

/***
 *
 * By Samihan Nandedkar
 * UIN: 667142409
 *
 * Main Gopher Game activity.
 *
 * In this activity UI thread will start two worker threads to play against each other to find
 * the position of Gopher.
 *
 * Worker Thread One:
 * Uses Runnable to talk with UI THread
 * Algorithm: Guesses a random position for the player using java.util.Random
 *
 * Worker Thread Two:
 * Used message to talk with UI thread
 * Algorithm: Guesses position in 51, 2, 53, 4, 55, 6, 57 ... procession.
 * Starting position used in 50 and the position is incremented by 51 then its modulus with 100 is calculate to get
 * the final position.
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Random;


public class GopherGameActivity extends AppCompatActivity {


    // Declaring the game players
    final int GOPHER = 0;
    final static int PLAYERTHREAD1 = 1;
    final static int PLAYERTHREAD2 = 2;

    // Declaring the location for gopher
    int GOPHER_LOCATION;

    //All the Possible Outcomes.
    // Success = The player is able to find the player.
    // Nearmiss = THe player position is in one of the adjacent holes to gopher.
    // CloseGuess = The player is 2 holes away from the gopher’s hole in any directions.
    // Completemiss = If the player position is any other location
    // Notallowed = TO avoid the player from making move on same location.
    final int SUCCESS = 1;
    final int NEARMISS = 2;
    final int CLOSEGUESS  = 3;
    final int COMPLETEMISS = 4;
    final int NOTALLOWED = 5;

    // Field one for the player one's progress
    final int fieldOne[] = {
            R.id.firstCell1, R.id.firstCell2, R.id.firstCell3, R.id.firstCell4, R.id.firstCell5,
            R.id.firstCell6, R.id.firstCell7, R.id.firstCell8, R.id.firstCell9, R.id.firstCell10,
            R.id.firstCell11, R.id.firstCell12, R.id.firstCell13, R.id.firstCell14, R.id.firstCell15,
            R.id.firstCell16, R.id.firstCell17, R.id.firstCell18, R.id.firstCell19, R.id.firstCell20,
            R.id.firstCell21, R.id.firstCell22, R.id.firstCell23, R.id.firstCell24, R.id.firstCell25,
            R.id.firstCell26, R.id.firstCell27, R.id.firstCell28, R.id.firstCell29, R.id.firstCell30,
            R.id.firstCell31, R.id.firstCell32, R.id.firstCell33, R.id.firstCell34, R.id.firstCell35,
            R.id.firstCell36, R.id.firstCell37, R.id.firstCell38, R.id.firstCell39, R.id.firstCell40,
            R.id.firstCell41, R.id.firstCell42, R.id.firstCell43, R.id.firstCell44, R.id.firstCell45,
            R.id.firstCell46, R.id.firstCell47, R.id.firstCell48, R.id.firstCell49, R.id.firstCell50,
            R.id.firstCell51, R.id.firstCell52, R.id.firstCell53, R.id.firstCell54, R.id.firstCell55,
            R.id.firstCell56, R.id.firstCell57, R.id.firstCell58, R.id.firstCell59, R.id.firstCell60,
            R.id.firstCell61, R.id.firstCell62, R.id.firstCell63, R.id.firstCell64, R.id.firstCell65,
            R.id.firstCell66, R.id.firstCell67, R.id.firstCell68, R.id.firstCell69, R.id.firstCell70,
            R.id.firstCell71, R.id.firstCell72, R.id.firstCell73, R.id.firstCell74, R.id.firstCell75,
            R.id.firstCell76, R.id.firstCell77, R.id.firstCell78, R.id.firstCell79, R.id.firstCell80,
            R.id.firstCell81, R.id.firstCell82, R.id.firstCell83, R.id.firstCell84, R.id.firstCell85,
            R.id.firstCell86, R.id.firstCell87, R.id.firstCell88, R.id.firstCell89, R.id.firstCell90,
            R.id.firstCell91, R.id.firstCell92, R.id.firstCell93, R.id.firstCell94, R.id.firstCell95,
            R.id.firstCell96, R.id.firstCell97, R.id.firstCell98, R.id.firstCell99, R.id.firstCell100};

    // Field two for player two's progress
    final int fieldTwo[] = {
            R.id.secondCell1, R.id.secondCell2, R.id.secondCell3, R.id.secondCell4, R.id.secondCell5,
            R.id.secondCell6, R.id.secondCell7, R.id.secondCell8, R.id.secondCell9, R.id.secondCell10,
            R.id.secondCell11, R.id.secondCell12, R.id.secondCell13, R.id.secondCell14, R.id.secondCell15,
            R.id.secondCell16, R.id.secondCell17, R.id.secondCell18, R.id.secondCell19, R.id.secondCell20,
            R.id.secondCell21, R.id.secondCell22, R.id.secondCell23, R.id.secondCell24, R.id.secondCell25,
            R.id.secondCell26, R.id.secondCell27, R.id.secondCell28, R.id.secondCell29, R.id.secondCell30,
            R.id.secondCell31, R.id.secondCell32, R.id.secondCell33, R.id.secondCell34, R.id.secondCell35,
            R.id.secondCell36, R.id.secondCell37, R.id.secondCell38, R.id.secondCell39, R.id.secondCell40,
            R.id.secondCell41, R.id.secondCell42, R.id.secondCell43, R.id.secondCell44, R.id.secondCell45,
            R.id.secondCell46, R.id.secondCell47, R.id.secondCell48, R.id.secondCell49, R.id.secondCell50,
            R.id.secondCell51, R.id.secondCell52, R.id.secondCell53, R.id.secondCell54, R.id.secondCell55,
            R.id.secondCell56, R.id.secondCell57, R.id.secondCell58, R.id.secondCell59, R.id.secondCell60,
            R.id.secondCell61, R.id.secondCell62, R.id.secondCell63, R.id.secondCell64, R.id.secondCell65,
            R.id.secondCell66, R.id.secondCell67, R.id.secondCell68, R.id.secondCell69, R.id.secondCell70,
            R.id.secondCell71, R.id.secondCell72, R.id.secondCell73, R.id.secondCell74, R.id.secondCell75,
            R.id.secondCell76, R.id.secondCell77, R.id.secondCell78, R.id.secondCell79, R.id.secondCell80,
            R.id.secondCell81, R.id.secondCell82, R.id.secondCell83, R.id.secondCell84, R.id.secondCell85,
            R.id.secondCell86, R.id.secondCell87, R.id.secondCell88, R.id.secondCell89, R.id.secondCell90,
            R.id.secondCell91, R.id.secondCell92, R.id.secondCell93, R.id.secondCell94, R.id.secondCell95,
            R.id.secondCell96, R.id.secondCell97, R.id.secondCell98, R.id.secondCell99, R.id.secondCell100};


    //Array to keep track which position is occupied by which player.
    int[] positionOccupancyArray = new int[100];

    // UI Handlers
    //runnable & message
    private Handler runnableUiHandler = new Handler(Looper.getMainLooper()) ;
    private Handler messageUiHandler;

    // The worker thread's handlers.
    private Handler oneHandler;
    private Handler twoHandler;

    // boolean to keep track if the gopher is found. and to check if the game is in stopped state
    boolean isGopherFound = false;
    boolean isStopped = true;

    // Keeping track of the player's steps.
    private int stepByPlayerOne = 0;
    private int stepByPlayerTwo = 0;

    // Declaring the view elements

    // Final layout will display the result when gopher is found.
    LinearLayout finalLayout;

    // TO update the status of player one & two
    TextView player1Status;
    TextView player2Status;
    TextView finalResult;
    // Declaring the buttons
    private Button startButton;
    private Button stopButton;
    private Button clearAll;

    // Array of string of the possible outcome to guess the UI thread can return back.
    final String[] possibleOutcomesForGuess = {"PossibleOutcomes","Success","Near Miss", "Close Guess", "Complete Miss"};


    // Using Random to choose position for Player One.
    int low = 1;
    int high = 101;
    private Random r;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gopher_game);

        //Initialing the occupancy array with -1 as all positions are not occupied
        Arrays.fill(positionOccupancyArray,-1);

        // Initializing with view elements
        player1Status = findViewById(R.id.player1_status);
        player2Status = findViewById(R.id.player2_status);
        finalResult = findViewById(R.id.result);
        startButton = findViewById(R.id.start);
        stopButton = findViewById(R.id.stop);
        clearAll = findViewById(R.id.startagain);
        finalLayout = findViewById(R.id.finalLayout);

        // Listener for Clear button
        clearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open the activity again.
                Intent intent = new Intent(GopherGameActivity.this, GopherGameActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        // Listener for start button
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Setting Start Button as disabled and stop button as enabled.
                startButton.setEnabled(false);
                stopButton.setEnabled(true);

                // Starting the game.
                beginGame();

            }
        });

        // SHowing the default dialog at the begining of the game.
        createCustomizeDialog();


    }

    private void createCustomizeDialog() {
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Hello. " + "\nPlease press `Start Game` to begin the game.\n`Stop` button will stop the game. " +
                "\nThen please press `Clear` button to reset the game to begin again. \n\nGood Luck finding me. \nFriendly Gopher.");
        builder.setTitle("Welcome to Gopher Game.");


        builder.setPositiveButton("Close",
                        new DialogInterface
                                .OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {
                                dialog.cancel();
                            }
                        });
        builder.create();
        builder.show();
    }

    // Function to start game.
    private void beginGame() {
        // Set a random position for gopher and place it on screen.
        r = new Random();
        GOPHER_LOCATION = r.nextInt(high -low) + low;
        setPosition(GOPHER_LOCATION, GOPHER , 0);

        // Listener for stop button.
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // set stop button as disabled
                stopButton.setEnabled(false);
                // Change the isStopped boolean as true.
                isStopped = true;
            }
        });

        // Defining the message handler for UI Thread.
        messageUiHandler = new Handler(Looper.getMainLooper()) {
            public void handleMessage(Message msg) {
                int what = msg.what ;
                switch (what) {
                    // When Player One makes a guess, set position & update the status on screen.
                    case PLAYERTHREAD1:
                        setPosition(msg.arg1,PLAYERTHREAD1, stepByPlayerOne);
                        player1Status.setText(possibleOutcomesForGuess[msg.arg2]);
                        // If the outcome is SUCCESS then update the screen
                        if(msg.arg2 == SUCCESS){
                            finalLayout.setVisibility(View.VISIBLE);
                            finalResult.setText("PLAYER 1 WON!");
                            stopButton.setEnabled(false);
                        }
                        break;
                    // When Player two makes a guess, set position & update the status on screen
                    case PLAYERTHREAD2:
                        setPosition(msg.arg1,PLAYERTHREAD2, stepByPlayerTwo);
                        player2Status.setText("Position "+ msg.arg1 +" : " + possibleOutcomesForGuess[msg.arg2]);
                        // If the outcome is SUCCESS then update the screen
                        if(msg.arg2 == SUCCESS){
                            finalLayout.setVisibility(View.VISIBLE);
                            finalResult.setText("PLAYER 2 WON!");
                            stopButton.setEnabled(false);
                        }
                        break;
                }
            }
        };

        // Set the isStopped as false.
        isStopped = false ;

        // Defining and starting the two worker threads
        Thread threadOne = new Thread(new WorkerThreadOne());
        threadOne.start();
        Thread threadTwo = new Thread(new WorkerThreadTwo());
        threadTwo.start();
    }

    // Update the UI for the positions given
    public void setPosition(int guessPosition, int item, int stepNumber){
        TextView t;
        TextView t1;
        switch(item){
            case GOPHER: {
                t = findViewById(fieldOne[guessPosition]);
                int gopher_color = ContextCompat.getColor(this, R.color.gopherLocation);
                t1 = findViewById(fieldTwo[guessPosition]);
                t.setText("");
                t1.setText("");
                t.setBackgroundResource(R.drawable.gopher_icon);
                t1.setBackgroundResource(R.drawable.gopher_icon);
                TextView t2 = findViewById(R.id.gopher);
                String s = "Gopher Location Set: " + guessPosition;
                t2.setText(s);
                positionOccupancyArray[guessPosition] = GOPHER;
                break;
            }
            case PLAYERTHREAD1: {
                int player1_color = ContextCompat.getColor(this, R.color.playerThread1);
                t = findViewById(fieldOne[guessPosition]);
                t.setBackgroundColor(player1_color);
                String s = "S:" + stepNumber;
                t.setText(s);
                positionOccupancyArray[guessPosition] = PLAYERTHREAD1;
                break;
            }
            case PLAYERTHREAD2: {
                t = findViewById(fieldTwo[guessPosition]);
                int player2_color = ContextCompat.getColor(this, R.color.playerThread2);
                t.setBackgroundColor(player2_color);
                String s = "S:" + stepNumber;
                t.setText(s);
                positionOccupancyArray[guessPosition] = PLAYERTHREAD2;
                break;
            }
        }
    };

    // Check position given by the player and return the outcome
    public int checkPossibleResponseForPosition(int pos) {
        int playersCurrentRow = pos/10;
        int playersCurrentColumn = pos%10;
        int gophersCurrentRow = GOPHER_LOCATION/10;
        int gophersCurrentColumn = GOPHER_LOCATION%10;

        // Check if the position is occupied bu any player then return a NOTALLOWED
        if(positionOccupancyArray[pos] != -1 && positionOccupancyArray[pos] != 0)
            return NOTALLOWED;
        else {
            // check the if the Gopher location is found
            if (pos == GOPHER_LOCATION) {
                return SUCCESS;
            }
            // Check if the player is in same row as the gopher and is 8 cols away from the gopher.
            else if (playersCurrentRow == gophersCurrentRow && pos >= GOPHER_LOCATION - 8 && pos <= GOPHER_LOCATION + 8) {
                return NEARMISS;
            }
            // Check if the player is two places away from gopher in any direction
            else if ((playersCurrentRow == gophersCurrentRow && Math.abs(playersCurrentColumn - gophersCurrentColumn) == 2) ||
                    (playersCurrentColumn == gophersCurrentColumn && Math.abs(playersCurrentRow - gophersCurrentRow) == 2) ||
                    (Math.abs(playersCurrentColumn - gophersCurrentColumn) == 2 && Math.abs(playersCurrentRow - gophersCurrentRow) == 2)) {
                return CLOSEGUESS;
            }
            // Return for everything else.
            else
                return COMPLETEMISS;
        }

    }

    // Runnable for Player One.
    class WorkerThreadOne implements Runnable{

        @Override
        public void run() {

            // Defining the handler for worker thread.
            oneHandler = new Handler(Looper.getMainLooper());

            // Loop while the gopher is not found.
            while(!isGopherFound) {
                // If stop is pressed then exit from the loop and end thread process
                if(isStopped){
                    break;
                }

                // Select random position for the player one in the range.
                r = new Random();
                final int myPos = r.nextInt(high -low) + low;
                final int outcomeReceived = checkPossibleResponseForPosition(myPos);

                // If the position is not occupied by this player before or any other player.
                if(outcomeReceived != NOTALLOWED){
                    // Increase the steps taken by the player
                    stepByPlayerOne += 1;

                    // Send runnable to UI Thread Handler to place item for this player
                    runnableUiHandler.post(new Runnable() {
                        public void run() {
                            setPosition(myPos,PLAYERTHREAD1, stepByPlayerOne);
                            player1Status.setText("Position "+ myPos +" : " + possibleOutcomesForGuess[outcomeReceived]);
                        }
                    });

                    // IF the outcome received is SUCCESS then send different runnable to UI thread and end game.
                    if(outcomeReceived == SUCCESS){
                        isGopherFound =true;
                        runnableUiHandler.post(new Runnable() {
                            public void run() {
                                finalLayout.setVisibility(View.VISIBLE);
                                finalResult.setText("PLAYER 1 WON!");
                                stopButton.setEnabled(false);

                            }
                        });
                        break;
                    }
                }

                // Make the thread sleep for 2 seconds after making a guess.
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    class WorkerThreadTwo implements Runnable{
        @Override
        public void run() {

            // Starting position for the thread.
            // THis player will go through all the places one by one.
            int player2Postition=50;

            // Defining the handler for worker thread.
            twoHandler = new Handler(Looper.getMainLooper());

            // Loop while the gopher is not found.
            while(!isGopherFound && player2Postition < 100){
                if(isStopped){
                    break;
                }

                final int outcomeReceived = checkPossibleResponseForPosition(player2Postition);

                // If the position guess is not occupied by this player or player one
                if(outcomeReceived != NOTALLOWED){
                    stepByPlayerTwo += 1;

                    final int myPos = player2Postition;

                    //Send message to UI Thread handler to place item for PLayer two
                    Message msg = messageUiHandler.obtainMessage(PLAYERTHREAD2) ;
                    msg.arg1 = myPos ;
                    msg.arg2 = outcomeReceived;
                    messageUiHandler.sendMessage(msg);

                    // if the outcome is received then set the boolean as true and exit from loop.
                    if(outcomeReceived == SUCCESS){
                        isGopherFound =true;
                        break;
                    }
                }

                // Sleep the thread for 2 seconds after making a guess.
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Updating the position for the player
                // This player will search in this way
                // Guess 1: 51, guess 2: 2, Guess 3: 53 ........
                player2Postition += 51;
                player2Postition = player2Postition % 100;
            }
        }
    }
}