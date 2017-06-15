package com.example.android.bakingapp.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.bakingapp.R;

public class MainActivity extends AppCompatActivity {

    public static boolean isTablet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            if (findViewById(R.id.bakesGrid) != null) {
                isTablet = true;
                FragmentManager fragmentManager = getSupportFragmentManager();
                BakesFragment bakesFragment = new BakesFragment();
                fragmentManager.beginTransaction()
                        .add(R.id.bakesGrid, bakesFragment)
                        .commit();
            } else {
                FragmentManager fragmentManager = getSupportFragmentManager();
                BakesFragment bakesFragment = new BakesFragment();
                fragmentManager.beginTransaction()
                        .add(R.id.bakesframe, bakesFragment)
                        .commit();
            }
        }
    }
}
