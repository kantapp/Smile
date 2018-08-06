package com.kantapp.smile.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kantapp.smile.R;
import com.kantapp.smile.Utils.Mobile;
import com.takusemba.cropme.CropView;
import com.takusemba.cropme.OnCropListener;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;

public class ProfilePicChange extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    public static final String TAG = "ProfilePicChange.java";
    private Button imageButton;
    private Boolean isImageSelected = false;
    private ImageView imageLogo;
    private CropView imageMainPreview;
    private ImageView restAll;
    private LinearLayout imageMainLin, progressLin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_pic_change);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Change Profile");
        imageLogo = findViewById(R.id.imageLogo);
        imageMainPreview = findViewById(R.id.imageMainPreview);
        imageButton = findViewById(R.id.imageButton);
        imageMainLin = findViewById(R.id.imageMainLin);
        progressLin = findViewById(R.id.progressLin);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isImageSelected) {
                    imageMainPreview.crop(new OnCropListener() {
                        @Override
                        public void onSuccess(Bitmap bitmap) {
                            updateProfile(bitmap);
                        }

                        @Override
                        public void onFailure() {

                        }
                    });
                } else {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                }
            }
        });

        restAll = findViewById(R.id.restAll);
        restAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isImageSelected = false;
                imageButton.setText("select Image");
            }
        });

    }

    private void updateProfile(Bitmap bitmap) {

        imageMainLin.setVisibility(View.GONE);
        progressLin.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressLin.setVisibility(View.GONE);
                imageMainLin.setVisibility(View.VISIBLE);
                onBackPressed();
            }
        },2000);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                isImageSelected = true;
                imageButton.setText("UPLOAD");
                imageMainPreview.setBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(this, "Somthing went wrong please try again", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        {
            this.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    //convert drawable to bitmap
    private Bitmap getDrawable2Bitmap(int image) {
        return BitmapFactory.decodeResource(getResources(), image);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
