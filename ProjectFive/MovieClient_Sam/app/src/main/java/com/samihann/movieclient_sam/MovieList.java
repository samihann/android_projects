package com.samihann.movieclient_sam;

/***
 * By Samihan Nandedkar
 * Project Five
 * CS 478
 *
 * Movie List View. Retrieves all movies from MovieCentral
 *
 */


import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.samihann.MovieCentral.MovieCentralAIDL;

public class MovieList extends AppCompatActivity {

    protected static final String TAG = "MovieList";
    private MovieCentralAIDL movieCentralAIDL;
    private boolean mIsBound = false;
    private TextView Status;
    private Button bindButton;
    private Button unbindButton;
    private Button getMovies;
    private String[] movieList;

    private String packageName = "com.samihann.MovieCentral";
    private String name = "com.samihann.MovieCentral.MovieCentral";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        Status = (TextView) findViewById(R.id.status1);
        bindButton = (Button) findViewById(R.id.bindButton1);
        unbindButton = (Button) findViewById(R.id.unbindButton1);
        getMovies = (Button) findViewById(R.id.getMovies);


        bindButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                checkBindingAndBind();
                bindButton.setEnabled(false);
                unbindButton.setEnabled(true);

            }
        });

        unbindButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unBindService();
                bindButton.setEnabled(true);
                unbindButton.setEnabled(false);
                Toast.makeText(MovieList.this,"Services are unbinded!", Toast.LENGTH_LONG ).show();
            }
        }));

        getMovies.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    if (mIsBound) {
                        movieList = movieCentralAIDL.allMovieName();
                        ArrayAdapter adapter = new ArrayAdapter(MovieList.this, android.R.layout.simple_list_item_1,movieList);
                        ListView lv = findViewById(R.id.movieList);
                        lv.setAdapter(adapter);
                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

                                Intent i = new Intent(MovieList.this, MovieView.class);
                                i.putExtra("index", position);

                                startActivity(i);
                            }
                        });
                    } else {
                        Toast.makeText(MovieList.this,"Please bind to get the movie list.", Toast.LENGTH_LONG ).show();
                    }
                } catch (RemoteException e) {
                    Log.e(TAG, e.toString());
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkBindingAndBind();

    }

    protected void unBindService() {
        if (mIsBound) {
            unbindService(mConnection);
            Status.setText("Status: Not Bound!");
            mIsBound = false;
        }
    }

    protected void checkBindingAndBind() {
        if (!mIsBound) {

            boolean b = false;
            Intent i = new Intent(MovieCentralAIDL.class.getName());

//            ServiceInfo info = getPackageManager().resolveService(i, 0).serviceInfo;
            i.setComponent(new ComponentName(packageName, name));

            b = bindService(i, this.mConnection, Context.BIND_AUTO_CREATE);

            if (b) {
                Status.setText("Status: Bound to MovieCentral");
                bindButton.setEnabled(false);
                Toast.makeText(MovieList.this,"Services are bound!", Toast.LENGTH_LONG ).show();
            } else {
                Status.setText("Status: Error!");
                unbindButton.setEnabled(false);
                Toast.makeText(MovieList.this,"Some error occured!", Toast.LENGTH_LONG ).show();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        unBindService();
    }

    private final ServiceConnection mConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className, IBinder iservice) {
            Log.i(TAG, "HERE HERE HERE HERE");
            movieCentralAIDL = MovieCentralAIDL.Stub.asInterface(iservice);

            mIsBound = true;

        }
        public void onServiceDisconnected(ComponentName className) {
            Log.i(TAG, "HERE WHY WHY WHY WHY HERE HERE");
            movieCentralAIDL = null;

        }
    };
}