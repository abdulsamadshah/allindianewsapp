package com.example.allindianewsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.allindianewsapp.bottomNavigationbar.homeFragment;
import com.example.allindianewsapp.bottomNavigationbar.videoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNav = findViewById(R.id.bottomNav);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new homeFragment());
        transaction.commit();

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()) {
                    case R.id.home:
                        fragmentTransaction.replace(R.id.container, new homeFragment());
                        break;

                    case R.id.videos:
                        fragmentTransaction.replace(R.id.container, new videoFragment());
                        break;

//                    case R.id.rewards:
//                        fragmentTransaction.replace(R.id.container,new rewardsFragment());
//                        break;
//
//                    case R.id.profiledata:
//                        fragmentTransaction.replace(R.id.container,new profileFragment());
//                        break;

                }
                fragmentTransaction.commit();
                return true;
            }
        });
    }
}