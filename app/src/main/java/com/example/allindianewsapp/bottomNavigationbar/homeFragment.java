package com.example.allindianewsapp.bottomNavigationbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.allindianewsapp.Adapter.fragmentadapter;
import com.example.allindianewsapp.Model.Users;
import com.example.allindianewsapp.R;
import com.example.allindianewsapp.SignupActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class homeFragment extends Fragment {

    ViewPager viewPagersdata;
    TabLayout tabLayout;
    ImageView myimages;
    FirebaseDatabase database;
    FirebaseAuth auth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        tabLayout = view.findViewById(R.id.tablayout);
        viewPagersdata = view.findViewById(R.id.viewPagersdata);
        myimages=view.findViewById(R.id.myimages);
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
//        if (auth.getCurrentUser() !=null){
//
//        }

        database.getReference().child("User").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Users users=snapshot.getValue(Users.class);
                    Picasso.get().load(users.getImageUri()).placeholder(R.drawable.avatar).into(myimages);
                }else{
                    Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        myimages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), SignupActivity.class);
                startActivity(intent);
            }
        });




        viewPagersdata.setAdapter(new fragmentadapter(getActivity().getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPagersdata);




        return view;

    }


}