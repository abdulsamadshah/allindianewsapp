package com.example.allindianewsapp.Tabnavigation;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.allindianewsapp.Adapter.postnewsAdapter;
import com.example.allindianewsapp.Model.Postnews;
import com.example.allindianewsapp.PostnewsActivity;
import com.example.allindianewsapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class aajTakNewsFragment extends Fragment {
    FloatingActionButton uploadnews;
    FirebaseDatabase database;
    RecyclerView aajtakrecyclerview;
    ArrayList<Postnews> postnewsArrayList;
    postnewsAdapter postnewsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_aaj_tak_news, container, false);

        uploadnews = view.findViewById(R.id.uploadnews);
        aajtakrecyclerview = view.findViewById(R.id.aajtakrecyclerview);
        database = FirebaseDatabase.getInstance();
        windowtoptollbar();
        uploadnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PostnewsActivity.class);
                startActivity(intent);
            }
        });


        aajtakrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        postnewsArrayList = new ArrayList<>();
        postnewsAdapter=new postnewsAdapter(getContext(),postnewsArrayList);
        aajtakrecyclerview.setAdapter(postnewsAdapter);


        database.getReference().child("posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postnewsArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Postnews postnews = dataSnapshot.getValue(Postnews.class);
                    postnewsArrayList.add(postnews);
                }

                postnewsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }


    private void windowtoptollbar() {
        ((AppCompatActivity) getContext()).getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.black));
        }
    }
}