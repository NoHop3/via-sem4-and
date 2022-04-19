package com.example.android_app_demo.ui.previousShoppings;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android_app_demo.databinding.FragmentPreviousShoppingsBinding;

public class PreviousShoppingsFragment extends Fragment {

    private FragmentPreviousShoppingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PreviousShoppingsViewModel previousShoppingsViewModel =
                new ViewModelProvider(this).get(PreviousShoppingsViewModel.class);

        binding = FragmentPreviousShoppingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textPreviousShoppings;
        previousShoppingsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}