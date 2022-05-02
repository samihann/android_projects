package com.samihann.movieclient_sam;


/***
 * By Samihan Nandedkar
 * Project Five
 * CS 478
 *
 * Single Movie View. Retrieves a single movie information from MovieCentral
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
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.samihann.MovieCentral.MovieCentralAIDL;

public class MovieView extends AppCompatActivity {

    protected static final String TAG = "MovieView";
    private MovieCentralAIDL movieCentralAIDL;
    private boolean mIsBound = false;
    private TextView movieNameText;
    private TextView directorText;
    private Button showVideo;
    private String url;
    private String movieName;
    private String director;
    private String[] data;
    private WebView mView;
    private int index;
    String temp = null;


    private String packageName = "com.samihann.MovieCentral";
    private String name = "com.samihann.MovieCentral.MovieCentral";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_view);


        movieNameText = (TextView) findViewById(R.id.movieName);
        directorText = (TextView) findViewById(R.id.director);
        showVideo = (Button) findViewById(R.id.showVideo);
        mView = (WebView) findViewById(R.id.webview);



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            index = extras.getInt("index");
            Log.i(TAG, String.valueOf(index));
        }

        showVideo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mView.loadUrl(url);

            }
        });


    }
    @Override
    protected void onStart() {
        super.onStart();
        checkBindingAndBind();
    }

    protected void checkBindingAndBind() {
        if (!mIsBound) {

            boolean b = false;
            Intent i = new Intent(MovieCentralAIDL.class.getName());

//            ServiceInfo info = getPackageManager().resolveService(i, 0).serviceInfo;
            i.setComponent(new ComponentName(packageName, name));

            b = bindService(i, this.mConnection, Context.BIND_AUTO_CREATE);

            if (b) {
                Toast.makeText(MovieView.this,"Services are bound!", Toast.LENGTH_LONG ).show();
            } else {
                Toast.makeText(MovieView.this,"Some error occured!", Toast.LENGTH_LONG ).show();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        unBindService();
    }

    protected void unBindService() {
        if (mIsBound) {
            unbindService(mConnection);
            mIsBound = false;
        }
    }

    private final ServiceConnection mConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className, IBinder iservice) {
            Log.i(TAG, "The Service Connected");
            movieCentralAIDL = MovieCentralAIDL.Stub.asInterface(iservice);
            mIsBound = true;
            try {
                temp = movieCentralAIDL.aMovie(index);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "CALLED FUNCTION");
            data = temp.split("-",3);
            movieName = data[0];
            director = data[1];
            url = data[2];
            movieNameText.setText("Name: "+movieName);
            directorText.setText("Director: "+director);


        }
        public void onServiceDisconnected(ComponentName className) {
            Log.i(TAG, "THe Service Disconnected");
            movieCentralAIDL = null;
            mIsBound = false;

        }
    };
}