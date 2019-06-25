package com.techespo.imagecaptureandstorerecyclerview.imgComponent;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ImageZoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String imagePath =  getIntent().getExtras().getString("image_path");
        CustomZoomView zomView  =  new CustomZoomView(this);
        zomView.setImageURI(Uri.parse(imagePath));
        setContentView(zomView);
    }
}
