package com.example.android_app_demo.listeners;

import com.example.android_app_demo.models.InstructionsResponse;

import java.util.List;

public interface InstructionsListener {
    void didFetch(List<InstructionsResponse> response, String message);
    void didError(String message);
}
