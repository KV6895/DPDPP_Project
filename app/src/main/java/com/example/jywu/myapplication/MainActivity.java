package com.example.jywu.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button btn_choosebook,btn_addrecordm,btn_checkrecord,btn_exit;
    private String chooseBook = "我的帳本" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_choosebook = (Button)findViewById(R.id.btn_choosebook);
        btn_addrecordm = (Button)findViewById(R.id.btn_addrecord);
        btn_checkrecord = (Button)findViewById(R.id.btn_checkrecord);
        btn_exit = (Button)findViewById(R.id.btn_exit);

        Bundle bundle = getIntent().getExtras();
        try {
            chooseBook = bundle.getString("Book");
            Log.e("在try",chooseBook);
        } catch (Exception e) {
            e.printStackTrace();
            chooseBook = "我的帳本";
            Log.e("在catch",chooseBook);
        }
        btn_choosebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,ChoosebookActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Book",chooseBook);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
        btn_addrecordm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,AddrecordActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Book",chooseBook);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
        btn_checkrecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,CheckrecordActivity.class);
                startActivity(intent);
            }
        });

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.finish();
            }
        });
    }
    @Override
    protected void onResume(){
        super.onResume();
    }

}
