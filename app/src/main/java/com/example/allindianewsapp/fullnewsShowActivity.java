package com.example.allindianewsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.example.allindianewsapp.Model.Postnews;
import com.example.allindianewsapp.Model.Users;
import com.example.allindianewsapp.databinding.ActivityFullnewsShowBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class fullnewsShowActivity extends AppCompatActivity {
    ActivityFullnewsShowBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFullnewsShowBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("news");
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

        String image = getIntent().getStringExtra("newsimages");
        Picasso.get().load(image).into(binding.newsimage);
        String newstitledata = getIntent().getStringExtra("newimagetitle");
        binding.newstitles.setText(newstitledata);

        String userimage = getIntent().getStringExtra("userimages");
        Picasso.get().load(userimage).placeholder(R.drawable.avatar).into(binding.userimagedata);
        binding.username.setText(getIntent().getStringExtra("username"));


        database.getReference().child("posts")
                .child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Postnews postnew = snapshot.getValue(Postnews.class);
                    binding.newsdescription.setText(postnew.getNewsdescriptiondata());

                }else{
                    Toast.makeText(fullnewsShowActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

}