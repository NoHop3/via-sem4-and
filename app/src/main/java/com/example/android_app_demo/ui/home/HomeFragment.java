package com.example.android_app_demo.ui.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_app_demo.R;
import com.example.android_app_demo.adapters.RandomRecipeAdapter;
import com.example.android_app_demo.listeners.RandomRecipeResponseListener;
import com.example.android_app_demo.models.RandomRecipeApiResponse;
import com.example.android_app_demo.requestManager.RequestManager;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private RandomRecipeAdapter randomRecipeAdapter;
    private ProgressDialog dialog;
    private RequestManager manager;
    Spinner spinner;
    List<String> tags = new ArrayList<>();
    private Object HomeFragment;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_home, container, false);

        dialog = new ProgressDialog(view.getContext());
        dialog.setTitle("Loading....");

        spinner = spinner.findViewById(R.id.spinner_tags);
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.tags,
                R.layout.spinner_text
        );
        arrayAdapter.setDropDownViewResource(R.layout.spinner_inner_text);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(spinnerSelectedListener);

        manager = new RequestManager(view.getContext());
        recyclerView = view.findViewById(R.id.recycler_random);

//        manager.getRandomRecipes(randomRecipeResponseListener);
//        dialog.show();

        return view;
    }

    private final RandomRecipeResponseListener randomRecipeResponseListener = new RandomRecipeResponseListener() {
        @Override
        public void didFetch(RandomRecipeApiResponse response, String message) {
            dialog.dismiss();
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(HomeFragment.super.requireContext(), 1));
            randomRecipeAdapter = new RandomRecipeAdapter(HomeFragment.super.requireContext(), response.recipes);
            recyclerView.setAdapter(randomRecipeAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(requireView().getContext(), "", Toast.LENGTH_SHORT).show();
        }
    };
    private final AdapterView.OnItemSelectedListener spinnerSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            tags.clear();
            tags.add(adapterView.getSelectedItem().toString());
            manager.getRandomRecipes(randomRecipeResponseListener, tags);
            dialog.show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}