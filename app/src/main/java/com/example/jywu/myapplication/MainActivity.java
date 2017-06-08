package com.example.jywu.myapplication;

import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button btn_choosebook,btn_addrecordm,btn_checkrecord,btn_exit;
    private String Data;
    private MyDBHelper DBhelper;
    private String chooseBook ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Data = new String();
        btn_choosebook = (Button)findViewById(R.id.btn_choosebook);
        btn_addrecordm = (Button)findViewById(R.id.btn_addrecord);
        btn_checkrecord = (Button)findViewById(R.id.btn_checkrecord);
        btn_exit = (Button)findViewById(R.id.btn_exit);
        DBhelper = new MyDBHelper(this,"hello",null,1);
        Bundle bundle = getIntent().getExtras();
        try {
            Data = bundle.getString("Data");
            add(Data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            chooseBook = bundle.getString("Book");
        } catch (Exception e) {
            e.printStackTrace();
            chooseBook = "我的帳本";
        }
        btn_choosebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,ChoosebookActivity.class);
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
        //Log.e("RESUME","~~");
    }
    private void add (String inputData){
        Log.e("Inputdata",inputData);
        ArrayList<String> buffer = new ArrayList<>();
        ContentValues values = new ContentValues();
        int index = inputData.indexOf(",");
        int header  = 0;
        int length = inputData.length();
        while(index != length-1 &&  index != -1) {
            String test = inputData.substring(header,index);
            buffer.add(test);
            inputData = inputData.substring(index+1);
            index = inputData.indexOf(",");
        }
        values.put("book", buffer.get(0));
        values.put("cdate",buffer.get(1));
        values.put("info",buffer.get(2));
        values.put("amount",buffer.get(3));
        values.put("remarks",buffer.get(4));
        long id = DBhelper.getWritableDatabase().insert(DBhelper.TABLE_NAME,null,values);
        Log.e("Add",id+"");
        SQLiteDatabase db = DBhelper.getReadableDatabase();
        Cursor c = db.query(DBhelper.TABLE_NAME,null,null,null,null,null,null);
        while(c.moveToNext()) {
           // Log.e("id",c.getInt(0)+"");
           // Log.e("book",c.getString(1));
           // Log.e("Date",c.getString(2));
           // Log.e("info",c.getString(3)+"");
           // Log.e("remarks",c.getString(4)+"");
           // Log.e("money",c.getInt(5)+"");
        }
    }
}
