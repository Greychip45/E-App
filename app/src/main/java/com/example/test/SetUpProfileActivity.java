package com.example.test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

public class SetUpProfileActivity extends AppCompatActivity {

    private Button btnSkip;
    private FloatingActionButton btnOpenCamera;
    private Uri uri;
    private ImageView profile;
    FirebaseStorage mStorage;
    private EditText businessName,businessBio,businessContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_profile);

        btnSkip = findViewById(R.id.btnSkip);
        businessName = findViewById(R.id.txt_name);
        businessBio = findViewById(R.id.txt_email);
        businessContact = findViewById(R.id.txt_phone);
        btnOpenCamera = findViewById(R.id.btnOpenCamera);
        profile = findViewById(R.id.imgProfile);

        btnOpenCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToHomeActivity();
            }
        });
    }

    //Method to send user to home activity
    private void sendUserToHomeActivity(){
        Intent i = new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(i);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    //Method to open the gallery
    private void openGallery() {

        ImagePicker.Companion.with(SetUpProfileActivity.this)
                .galleryOnly()
                .start();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(data.getData() != null){
            uri = data.getData();
            Picasso.get().load(uri).placeholder(R.drawable.download).into(profile);

        }
    }

}