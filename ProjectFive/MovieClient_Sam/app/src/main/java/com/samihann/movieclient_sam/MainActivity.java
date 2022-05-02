package com.samihann.movieclient_sam;

/***
 * By Samihan Nandedkar
 * Project Five
 * CS 478
 *
 * Main Page
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.samihann.MovieCentral.MovieCentralAIDL;

public class MainActivity extends AppCompatActivity {

    protected static final String TAG = "MovieClient";
    private MovieCentralAIDL movieCentralAIDL;
    private boolean mIsBound = false;
    private TextView Status;
    private Button bindButton;
    private Button unbindButton;
    private Button nextButton;

    private String packageName = "com.samihann.MovieCentral";
    private String name = "com.samihann.MovieCentral.MovieCentral";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Status = (TextView) findViewById(R.id.status);
        bindButton = (Button) findViewById(R.id.bindButton);
        unbindButton = (Button) findViewById(R.id.unbindButton);
        nextButton = (Button) findViewById(R.id.nextButton);



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
                Toast.makeText(MainActivity.this,"Services are unbinded!", Toast.LENGTH_LONG ).show();
            }
        }));

        nextButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MovieList.class);
                startActivity(intent);
            }
        }));

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
                Toast.makeText(MainActivity.this,"Services are bound!", Toast.LENGTH_LONG ).show();
            } else {
                Status.setText("Status: Error!");
                unbindButton.setEnabled(false);
                Toast.makeText(MainActivity.this,"Some error occured!", Toast.LENGTH_LONG ).show();
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
            movieCentralAIDL = MovieCentralAIDL.Stub.asInterface(iservice);
            mIsBound = true;

        }
        public void onServiceDisconnected(ComponentName className) {
            movieCentralAIDL = null;

        }
    };

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}