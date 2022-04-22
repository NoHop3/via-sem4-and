package com.example.android_app_demo.ui.home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private RandomRecipeAdapter randomRecipeAdapter;
    private ProgressDialog dialog;
    private RequestManager manager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_home, container, false);

        dialog = new ProgressDialog(view.getContext());
        dialog.setTitle("Loading....");

        manager = new RequestManager(view.getContext());
        recyclerView = view.findViewById(R.id.recycler_random);

        manager.getRandomRecipes(randomRecipeResponseListener);
        dialog.show();

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}