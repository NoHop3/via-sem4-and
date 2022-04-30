package com.example.android_app_demo.listeners;

import com.example.android_app_demo.models.RecipeDetailsResponse;

public interface RecipeDetailsListener {
    void didFetch(RecipeDetailsResponse response, String message);
    void didError(String message);
}
