package com.example.jywu.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.File;

public class ShowPhoto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        setContentView(R.layout.activity_show_photo);
        File root = Environment.getExternalStorageDirectory();
        String photo = bundle.getString("photo");
        ImageView IV = (ImageView) findViewById(R.id.imageView);
        Bitmap bMap = BitmapFactory.decodeFile(photo);
        IV.setImageBitmap(bMap);
    }
}
