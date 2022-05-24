package com.example.allindianewsapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allindianewsapp.Model.Postnews;
import com.example.allindianewsapp.Model.Users;
import com.example.allindianewsapp.R;
import com.example.allindianewsapp.fullnewsShowActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class postnewsAdapter extends RecyclerView.Adapter<postnewsAdapter.postnewViewholder> {

    Context context;
    ArrayList<Postnews> postnewsArrayList;
    String userimageuri;


    public postnewsAdapter(Context context, ArrayList<Postnews> postnewsArrayList) {
        this.context = context;
        this.postnewsArrayList = postnewsArrayList;
    }

    @NonNull
    @Override
    public postnewViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.postnews_layout, parent, false);
        return new postnewViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull postnewViewholder holder, int position) {
        Postnews postnews = postnewsArrayList.get(position);
        String newsimagedata;
        newsimagedata = postnews.getPostnewimage();
        Picasso.get().load(newsimagedata).into(holder.newsimage);


        holder.newstitle.setText(postnews.getNewstitle());


        FirebaseDatabase.getInstance().getReference().child("User")
                .child(postnews.getPostId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);
                holder.username.setText(users.getName());
                userimageuri = users.getImageUri();
                Picasso.get().load(userimageuri).into(holder.userimage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, fullnewsShowActivity.class);
                intent.putExtra("newsimages", newsimagedata);
                intent.putExtra("newimagetitle", holder.newstitle.getText().toString());
                intent.putExtra("userimages", userimageuri);
                intent.putExtra("username", holder.username.getText().toString());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postnewsArrayList.size();
    }

    public class postnewViewholder extends RecyclerView.ViewHolder {
        ImageView newsimage, userimage;
        TextView username, newstitle;

        public postnewViewholder(@NonNull View itemView) {
            super(itemView);

            newsimage = itemView.findViewById(R.id.newsimage);
            userimage = itemView.findViewById(R.id.userimage);
            username = itemView.findViewById(R.id.username);
            newstitle = itemView.findViewById(R.id.newstitle);


        }
    }

}
