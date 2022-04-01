package com.samihann.projectthree_a2_sam;

/***
 * By Samihan Nandedkar
 * Project Three
 * CS 478
 *
 * Webview fragment for Restaurant Activity.
 *
 */

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class RestaurantWebsiteFragment extends Fragment {

    private WebView mWebView = null;
    public int mCurrIdx = -1;
    private int webArrLength;
    private ListViewModel model;

    public RestaurantWebsiteFragment() {
        super();
    }

    //get for index of the webpage shown
    int getShownIndex() {
        return mCurrIdx;
    }

    //Show the website at given index.
    public void showWebViewIndex(int newIndex) {

        //if index is out of the bound for array, return
        if (newIndex < 0 || newIndex >= webArrLength)
            return;

        //update the index and load the url accordingly
        mCurrIdx = newIndex;
        mWebView.loadUrl(RestaurantActivity.rWebsites[mCurrIdx]);

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.web_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        mWebView = (WebView) getActivity().findViewById(R.id.webview);
        webArrLength = RestaurantActivity.rWebsites.length;

        model = new ViewModelProvider(requireActivity()).get(ListViewModel.class);

        // retains last quote shown on config change
        model.getSelectedItem().observe(getViewLifecycleOwner(), item -> {
            if (item == mCurrIdx || item < 0 || item >= webArrLength)
                return;

            // Update UI
            mCurrIdx = item;
            mWebView.loadUrl(RestaurantActivity.rWebsites[mCurrIdx]);
        });


    }
}
