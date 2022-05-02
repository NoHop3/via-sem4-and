package com.example.android_app_demo.ui.home;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_app_demo.NavDrawerActivity;
import com.example.android_app_demo.NavDrawerViewModel;
import com.example.android_app_demo.R;
import com.example.android_app_demo.adapters.RandomRecipeAdapter;
import com.example.android_app_demo.listeners.RandomRecipeResponseListener;
import com.example.android_app_demo.listeners.RecipeClickListener;
import com.example.android_app_demo.models.RandomRecipeApiResponse;
import com.example.android_app_demo.requestManager.RequestManager;
import com.example.android_app_demo.ui.chosenRecipe.ChosenRecipeFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    RandomRecipeAdapter randomRecipeAdapter;
    ProgressDialog dialog;
    RequestManager manager;
    Spinner spinner;
    List<String> tags;
    ArrayAdapter arrayAdapter;
    SearchView searchView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

//        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.)
//        System.out.println("Fragments: "+getParentFragmentManager().getFragments());

        tags = new ArrayList<>();
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        dialog = new ProgressDialog(view.getContext());
        dialog.setTitle("Loading....");

        searchView = view.findViewById(R.id.searchView_home);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                tags.clear();
                tags.add(query);
                manager.getRandomRecipes(randomRecipeResponseListener, tags);
                dialog.show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        // Cause of error #1-> spinner = spinner.findViewById() you cannot do that because spinner is not initialized!!!!
        // Cause of error #2-> context is accessible via view.getContext() not "this"!!!
        // Cause of error code
        /**
         * spinner.findViewById(R.id.spinner_tags)
         * arrayAdapter = ArrayAdapter.createFromResource(this, R.array.tags, R.layout.spinner_text);
         */
        //Correct code bellow
        spinner = view.findViewById(R.id.spinner_tags);
        arrayAdapter = ArrayAdapter.createFromResource(view.getContext(), R.array.tags, R.layout.spinner_text);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_inner_text);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(spinnerSelectedListener);

        manager = new RequestManager(view.getContext());
        recyclerView = view.findViewById(R.id.recycler_random);

//        manager.getRandomRecipes(randomRecipeResponseListener, tags);
//        dialog.show();

        return view;
    }

    private final RandomRecipeResponseListener randomRecipeResponseListener = new RandomRecipeResponseListener() {
        @Override
        public void didFetch(RandomRecipeApiResponse response, String message) {
            dialog.dismiss();
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(HomeFragment.super.requireContext(), 1));
            randomRecipeAdapter = new RandomRecipeAdapter(HomeFragment.super.requireContext(), response.recipes, recipeClickListener);
            recyclerView.setAdapter(randomRecipeAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(requireView().getContext(), message, Toast.LENGTH_SHORT).show();
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

    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @SuppressLint("ResourceType")
        @Override
        public void onRecipeClick(String id) {
            int idToPass = Integer.parseInt(id);

            NavDrawerViewModel viewModel = new ViewModelProvider(HomeFragment.super.requireActivity()).get(NavDrawerViewModel.class);
            viewModel.setId(idToPass);

            NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.chosenRecipeFragment);
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}