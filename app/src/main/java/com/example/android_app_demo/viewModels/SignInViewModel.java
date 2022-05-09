package com.example.android_app_demo.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android_app_demo.repositories.UserRepository;
import com.google.firebase.auth.FirebaseUser;

public class SignInViewModel extends AndroidViewModel {
    private final UserRepository userRepository;
    public SignInViewModel(@NonNull Application application) {
        super(application);
        userRepository = UserRepository.getInstance(application);
    }
    public LiveData<FirebaseUser> getCurrentUser() {
        return userRepository.getCurrentUser();
    }

}
