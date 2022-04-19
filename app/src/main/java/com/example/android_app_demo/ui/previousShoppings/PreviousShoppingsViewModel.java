package com.example.android_app_demo.ui.previousShoppings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PreviousShoppingsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public PreviousShoppingsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is a previous shoppings fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}