package com.samihann.MovieCentral;

/***
 * By Samihan Nandedkar
 * Project Five
 * CS 478
 *
 * Service
 *
 */

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.samihann.MovieCentral.MovieCentralAIDL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MovieCentral extends Service {

    private final MovieCentralAIDL.Stub mBinder = new MovieCentralAIDL.Stub() {
        @Override
        public String[] allMovies() throws RemoteException {
            // Parse through json file for the details for animals.
            String[] complete = getResources().getStringArray(R.array.complete);
            return complete;
        }

        @Override
        public String aMovie(int number) throws RemoteException {
            String[] complete = getResources().getStringArray(R.array.complete);
            String movieDetails = complete[number];
            return movieDetails;
        }

        @Override
        public String[] allMovieName() throws RemoteException {
            String[] nameList = getResources().getStringArray(R.array.name);
            return nameList;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("movie-list.json");
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