package com.example.allindianewsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.allindianewsapp.Model.Postnews;
import com.example.allindianewsapp.Model.Users;
import com.example.allindianewsapp.databinding.ActivityPostnewsBinding;
import com.example.allindianewsapp.databinding.FragmentAajTakNewsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Date;

public class PostnewsActivity extends AppCompatActivity {

    Uri imageuri;
    FirebaseDatabase database;
    FirebaseStorage storage;
    FirebaseAuth auth;
    ActivityPostnewsBinding binding;
    Users users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostnewsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
       getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT >= 21) {
            Window window =getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.gray));
        }else{
            Toast.makeText(this, "errror", Toast.LENGTH_SHORT).show();
        }

            database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();

        binding.videotitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String description = binding.videotitle.getText().toString();
                if (!description.isEmpty()) {
                    binding.postbutton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.postdesign));
                    binding.postbutton.setTextColor(getApplicationContext().getResources().getColor(R.color.white));
                    binding.postbutton.setEnabled(true);
                } else {
                    binding.postbutton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.follow_background));
                    binding.postbutton.setTextColor(getApplicationContext().getResources().getColor(R.color.black));
                    binding.postbutton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        database.getReference().child("User")
                .child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Users users = snapshot.getValue(Users.class);
                    Picasso.get().load(users.getImageUri()).into(binding.nprofile);
                    binding.names.setText(users.getName());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 25);
            }
        });


        binding.postbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final StorageReference reference = storage.getReference().child("posts")
                        .child(FirebaseAuth.getInstance().getUid())
                        .child(new Date().getTime() + "");

                reference.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Postnews postnews = new Postnews();
                                postnews.setPostnewimage(uri.toString());
                                postnews.setNewstitle(binding.videotitle.getText().toString());
                                postnews.setNewsdescriptiondata(binding.newsdescriptiondata.getText().toString());
                                postnews.setPostId(FirebaseAuth.getInstance().getUid());
                                postnews.setPostedAt(String.valueOf(new Date().getTime()));

                                database.getReference().child("posts")
                                        .push()
                                        .setValue(postnews).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        Toast.makeText(getApplicationContext(), "news Uploaded succuessfully", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            imageuri = data.getData();
            binding.postimage.setImageURI(imageuri);
            binding.postimage.setVisibility(View.VISIBLE);


            binding.postbutton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.postdesign));
            binding.postbutton.setTextColor(getApplicationContext().getResources().getColor(R.color.white));
            binding.postbutton.setEnabled(true);


        }
    }

}