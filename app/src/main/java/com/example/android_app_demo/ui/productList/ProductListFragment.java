package com.example.android_app_demo.ui.productList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.android_app_demo.R;
import com.example.android_app_demo.databinding.FragmentProductListBinding;

public class ProductListFragment extends Fragment {

    private FragmentProductListBinding binding;
    Button clickBtn;
    EditText enteredText;
    TextView textShown;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProductListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding = FragmentProductListBinding.inflate(inflater, container, false);
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