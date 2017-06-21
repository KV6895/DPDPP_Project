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
import android.widget.EditText;
import android.widget.ToggleButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddrecordActivity extends AppCompatActivity {

    private Button btn_addrecordOK,btn_openCamera;
    private ToggleButton tbtn_food,tbtn_traffic,tbtn_home,tbtn_ent,tbtn_else,tbtn_income;
    private EditText money,describe;
    private String choose;
    private String chooseBook;
    private MyDBHelper DBhelper;
    private int lastID;
    private String photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addrecord);
        DBhelper = new MyDBHelper(this,"hello",null,1);
        money = (EditText)findViewById(R.id.editText_money);
        describe = (EditText)findViewById(R.id.editText_describe);
        btn_addrecordOK = (Button)findViewById(R.id.btn_addrecordOK);
        btn_openCamera = (Button)findViewById(R.id.btn_openCamera);
        tbtn_food = (ToggleButton)findViewById(R.id.tbtn_food);
        tbtn_traffic = (ToggleButton)findViewById(R.id.tbtn_traffic);
        tbtn_home = (ToggleButton)findViewById(R.id.tbtn_home);
        tbtn_ent = (ToggleButton)findViewById(R.id.tbtn_ent);
        tbtn_else = (ToggleButton)findViewById(R.id.tbtn_else);
        tbtn_income = (ToggleButton)findViewById(R.id.tbtn_income);
        Bundle bundle  = getIntent().getExtras();
        chooseBook = bundle.getString("Book");
        tbtn_food.setChecked(true);
        choose = "食物";
        SQLiteDatabase db = DBhelper.getReadableDatabase();
        Cursor testc = db.query(DBhelper.TABLE_NAME, new String [] {"MAX(_id)"}, null, null, null, null, null);
        testc.moveToFirst();
        lastID = testc.getInt(0);
        photo = "/sdcard/demo/94iAccounting"+String.valueOf(lastID+1)+".jpg";
        tbtn_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tbtn_food.isChecked()) {
                    setCheckfalse();
                    tbtn_food.setChecked(true);
                    choose = "食物";
                }
            }
        });

        tbtn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tbtn_home.isChecked()) {
                    setCheckfalse();
                    tbtn_home.setChecked(true);
                    choose = "居家";
                }
            }
        });

        tbtn_traffic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tbtn_traffic.isChecked()) {
                    setCheckfalse();
                    tbtn_traffic.setChecked(true);
                    choose = "交通";
                }
            }
        });

        tbtn_ent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tbtn_ent.isChecked()) {
                    setCheckfalse();
                    tbtn_ent.setChecked(true);
                    choose = "娛樂";
                }
            }
        });


        tbtn_else.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tbtn_else.isChecked()) {
                    setCheckfalse();
                    tbtn_else.setChecked(true);
                    choose = "其他";
                }
            }
        });

        tbtn_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tbtn_income.isChecked()) {
                    setCheckfalse();
                    tbtn_income.setChecked(true);
                    choose = "收入";
                }
            }
        });

        btn_addrecordOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(AddrecordActivity.this,MainActivity.class);
                Bundle bundle = new Bundle();
                addtoDatabase(add()); // input data to the database
                bundle.putString("Book",chooseBook);
                intent.putExtras(bundle);
                startActivity(intent);
                AddrecordActivity.this.finish();
            }
        });

        btn_openCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddrecordActivity.this,CameraActivity.class);
                startActivity(intent);
            }
        });
    }
    public String add() {
        String inputData = new String();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String cdate = dateFormat.format(date);
        String getDescribe = describe.getText().toString();
        String getMoney = money.getText().toString();
        if(money.getText().toString().length() == 1) {
            getMoney="0";
        }
        inputData += chooseBook + ",";
        inputData += cdate + ",";
        inputData += choose + ",";
        inputData += getMoney+ ",";
        inputData += getDescribe + ",";
        return inputData;
    }
    private void addtoDatabase (String inputData){
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
        photo = "/sdcard/demo/94iAccounting"+String.valueOf(lastID+1)+".jpg";
        values.put("photo",photo);
        long id = DBhelper.getWritableDatabase().insert(DBhelper.TABLE_NAME,null,values);
        Log.e("Add",id+"");
    }
    void setCheckfalse() {
        tbtn_food.setChecked(false);
        tbtn_traffic.setChecked(false);
        tbtn_home.setChecked(false);
        tbtn_ent.setChecked(false);
        tbtn_else.setChecked(false);
        tbtn_income.setChecked(false);
    }
    @Override
    public  void onBackPressed(){
        Intent intent = new Intent();
        intent.setClass(AddrecordActivity.this,MainActivity.class);
        Bundle bundle = new Bundle();
        //addtoDatabase(add()); // input data to the database
        bundle.putString("Book",chooseBook);
        intent.putExtras(bundle);
        startActivity(intent);
        AddrecordActivity.this.finish();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }
}
