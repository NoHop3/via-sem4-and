package com.example.android_app_demo.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.android_app_demo.R;
import com.example.android_app_demo.databinding.FragmentGalleryBinding;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    Button clickBtn;
    EditText enteredText;
    TextView textShown;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        clickBtn = root.findViewById(R.id.clickBtn);
        enteredText = root.findViewById(R.id.editTextTextPersonName);
        textShown = root.findViewById(R.id.textView);
        clickBtn.setOnClickListener(view -> {
            Toast.makeText(getContext(), "I burnt this toast :D", Toast.LENGTH_SHORT).show();
            textShown.setText(enteredText.getText());
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}