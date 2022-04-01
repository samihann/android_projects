package com.samihann.projectthree_a2_sam;

/***
 * By Samihan Nandedkar
 * Project Three
 * CS 478
 *
 * List view model used in fragments
 *
 *
 */
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

public class ListViewModel extends ViewModel {

    private final MutableLiveData<Integer> selectedItem = new MutableLiveData<Integer>();

    public void selectItem(Integer item) {
        selectedItem.setValue(item);
    }
    public LiveData<Integer> getSelectedItem() {
        return selectedItem;
    }
}
