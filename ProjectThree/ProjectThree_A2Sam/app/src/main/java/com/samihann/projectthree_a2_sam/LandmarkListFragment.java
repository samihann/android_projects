package com.samihann.projectthree_a2_sam;

/***
 * By Samihan Nandedkar
 * Project Three
 * CS 478
 *
 * Landmark list fragment for Restaurant Activity.
 *
 *
 */
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.ViewModelProvider;

public class LandmarkListFragment extends ListFragment {

    private static final String TAG = "LandmarkListFragment";
    public int mCurrIdx = -1;

    private ListViewModel mModel;

    // Called when the user selects an item from the List
    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int pos, long id) {

        mCurrIdx = pos;
        mModel.selectItem(pos);

        // Indicates the selected item has been checked
        getListView().setItemChecked(pos, true);

        // Inform the ModelView that the selection may have changed
        mModel.selectItem(pos);

    }

    @Override
    public void onViewCreated(View view, Bundle savedState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onActivityCreated()");
        super.onViewCreated(view, savedState);

        mModel = new ViewModelProvider(requireActivity()).get(ListViewModel.class);

        // Set the list adapter for the ListView
        setListAdapter(new ArrayAdapter<>(getActivity(),
                R.layout.list_fragment, LandmarkActivity.lList));

        // Set the list choice mode to allow only one selection at a time
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

}
