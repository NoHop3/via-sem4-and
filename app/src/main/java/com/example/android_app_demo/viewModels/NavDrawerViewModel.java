package com.example.android_app_demo.viewModels;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android_app_demo.repositories.UserRepository;
import com.google.firebase.auth.FirebaseUser;

public class NavDrawerViewModel extends AndroidViewModel {
    private final UserRepository userRepository;
    private int id;

    public NavDrawerViewModel(Application app) {
        super(app);
        userRepository = UserRepository.getInstance(app);
        this.id = -1;
    }

    public LiveData<FirebaseUser> getCurrentUser() {
        return userRepository.getCurrentUser();
    }

    public void signOut() {
        userRepository.signOut();
    }
        public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}