package com.example.android_app_demo.ui.productList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProductListViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ProductListViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is a product list fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}