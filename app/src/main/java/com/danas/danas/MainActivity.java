package com.danas.danas;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView appBottomNavigationView;
    private FrameLayout appFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appBottomNavigationView = findViewById(R.id.bottomNavigationView);
        appFrameLayout = findViewById(R.id.frameLayout);

        appBottomNavigationView.setItemIconTintList(null);

        appBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();

                if (itemId == R.id.home) {
                    loadFragment(new HomeFragment(), false);
                } else if (itemId == R.id.school) {
                    loadFragment(new SchoolFragment(), false);
                } else if (itemId == R.id.work) {
                    loadFragment(new WorkFragment(), false);
                } else {
                    loadFragment(new ProfileFragment(), false);
                }

                return true;
            }
        });

        loadFragment(new HomeFragment(), true);
    }

    private void loadFragment(Fragment appFragment, boolean isAppInitialized) {
        FragmentManager appFragmentManager = getSupportFragmentManager();
        FragmentTransaction appFragmentTransaction = appFragmentManager.beginTransaction();

        if(isAppInitialized) {
            appFragmentTransaction.add(R.id.frameLayout, appFragment);
        } else {
            appFragmentTransaction.replace(R.id.frameLayout, appFragment);
        }

        appFragmentTransaction.commit();
    }
}