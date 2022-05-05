package com.example.android_app_demo.ui.chosenRecipe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_app_demo.NavDrawerViewModel;
import com.example.android_app_demo.R;
import com.example.android_app_demo.adapters.IngredientsAdapter;
import com.example.android_app_demo.adapters.SimilarRecipeAdapter;
import com.example.android_app_demo.listeners.RecipeClickListener;
import com.example.android_app_demo.listeners.RecipeDetailsListener;
import com.example.android_app_demo.listeners.SimilarRecipesListener;
import com.example.android_app_demo.models.Recipe;
import com.example.android_app_demo.models.RecipeDetailsResponse;
import com.example.android_app_demo.models.SimilarRecipesResponse;
import com.example.android_app_demo.requestManager.RequestManager;
import com.example.android_app_demo.ui.home.HomeFragment;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ChosenRecipeFragment extends Fragment {

    private int id;
    TextView textView_meal_name, textView_meal_source, textView_meal_summary;
    ImageView imageView_meal_image;
    RecyclerView recycler_meal_ingredients, recycler_meal_similar;
    RequestManager manager;
    ProgressDialog dialog;
    IngredientsAdapter ingredientsAdapter;
    SimilarRecipeAdapter similarRecipeAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        NavDrawerViewModel viewModel = new ViewModelProvider(ChosenRecipeFragment.super.requireActivity()).get(NavDrawerViewModel.class);
        id = viewModel.getId();

        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        findViews(view);
        manager = new RequestManager(view.getContext());
        manager.getRecipeDetails(recipeDetailsListener, id);
        manager.getSimilarRecipes(similarRecipesListener, id);
        dialog = new ProgressDialog(view.getContext());
        dialog.setTitle("Loading details");
        dialog.show();
        return view;
    }

    private void findViews(View view) {
        textView_meal_name = view.findViewById(R.id.textView_meal_name);
        textView_meal_source = view.findViewById(R.id.textView_meal_source);
        textView_meal_summary = view.findViewById(R.id.textView_meal_summary);
        imageView_meal_image = view.findViewById(R.id.imageView_meal_image);
        recycler_meal_ingredients = view.findViewById(R.id.recycler_meal_ingredients);
        recycler_meal_similar = view.findViewById(R.id.recycler_meal_similar);
    }

    private final RecipeDetailsListener recipeDetailsListener = new RecipeDetailsListener() {
        @Override
        public void didFetch(RecipeDetailsResponse response, String message) {
            dialog.dismiss();
            textView_meal_name.setText(response.title);
            textView_meal_source.setText(response.sourceName);
            textView_meal_summary.setText(response.summary);
            Picasso.get().load(response.image).into(imageView_meal_image);

            recycler_meal_ingredients.setHasFixedSize(true);
            recycler_meal_ingredients.setLayoutManager(new LinearLayoutManager(requireView().getContext(), LinearLayoutManager.HORIZONTAL, false));
            ingredientsAdapter = new IngredientsAdapter(requireView().getContext(), response.extendedIngredients);
            recycler_meal_ingredients.setAdapter(ingredientsAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(requireView().getContext(), message, Toast.LENGTH_SHORT).show();
        }
    };
    private final SimilarRecipesListener similarRecipesListener = new SimilarRecipesListener() {
        @Override
        public void didFetch(List<SimilarRecipesResponse> response, String message) {
            recycler_meal_similar.setHasFixedSize(true);
            recycler_meal_similar.setLayoutManager(new LinearLayoutManager(ChosenRecipeFragment.view.getContext(), LinearLayoutManager.HORIZONTAL, false));
            similarRecipeAdapter = new SimilarRecipeAdapter(ChosenRecipeFragment.view.getContext(), response, recipeClickListener);
            recycler_meal_similar.setAdapter(similarRecipeAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(ChosenRecipeFragment.view.getContext(), message, Toast.LENGTH_SHORT).show();

        }
    };
    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClick(String id) {
                startActivity(new Intent(ChosenRecipeFragment.view.getContext(), ChosenRecipeFragment.class)
                .putExtra("id", id));
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
