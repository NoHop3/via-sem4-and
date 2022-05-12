package com.example.android_app_demo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.example.android_app_demo.viewModels.NavDrawerViewModel;
import com.google.android.material.navigation.NavigationView;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android_app_demo.databinding.ActivityNavDrawerBinding;

public class NavDrawerActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityNavDrawerBinding binding;
    private NavDrawerViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(NavDrawerViewModel.class);

        checkIfSignedIn();


        binding = ActivityNavDrawerBinding.inflate(getLayoutInflater());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_drawer, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_nav_drawer);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void checkIfSignedIn() {
        viewModel.getCurrentUser().observe(this, user -> {
            if (user != null) {
                Log.v("Logging", "HELLO!!!" + viewModel.getCurrentUser().getValue().getEmail());
                setContentView(binding.getRoot());

                setSupportActionBar(binding.appBarNavDrawer.toolbar);
                DrawerLayout drawer = binding.drawerLayout;
                NavigationView navigationView = binding.navView;
                // Passing each menu ID as a set of Ids because each
                // menu should be considered as top level destinations.
                mAppBarConfiguration = new AppBarConfiguration.Builder(
                        R.id.nav_home, R.id.nav_productList, R.id.nav_shoppingList, R.id.nav_previousShoppings)
                        .setOpenableLayout(drawer)
                        .build();
                NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_nav_drawer);
                NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
                NavigationUI.setupWithNavController(navigationView, navController);
            } else
                startLoginActivity();
        });
    }

    private void startLoginActivity() {
        startActivity(new Intent(this, SignInActivity.class));
        finish();
    }

    private void signOut() {
        viewModel.signOut();
    }

}