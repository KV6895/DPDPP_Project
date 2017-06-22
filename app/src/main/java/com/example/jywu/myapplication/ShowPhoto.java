package com.example.jywu.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class ShowPhoto extends AppCompatActivity {
    private Button gps_Button;
    private double longitude;
    private double latitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        setContentView(R.layout.activity_show_photo);
        File root = Environment.getExternalStorageDirectory();
        String photo = bundle.getString("photo");
        longitude = bundle.getDouble("longitude");
        latitude = bundle.getDouble("latitude");
        ImageView IV = (ImageView) findViewById(R.id.imageView);
        gps_Button = (Button) findViewById(R.id.btn_gps1);

        Bitmap bMap = BitmapFactory.decodeFile(photo);
        IV.setImageBitmap(bMap);
        gps_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowPhoto.this, ShowGPS.class);
                Bundle bundle = new Bundle();
                bundle.putDouble("latitude",latitude);
                bundle.putDouble("longitude",longitude);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
    }

}
