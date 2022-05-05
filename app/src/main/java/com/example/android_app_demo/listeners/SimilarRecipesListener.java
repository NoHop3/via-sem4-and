package com.example.android_app_demo.listeners;

import com.example.android_app_demo.models.SimilarRecipesResponse;

import java.util.List;

public interface SimilarRecipesListener {
    void didFetch  (List<SimilarRecipesResponse> response, String message);
    void didError (String message);
}
