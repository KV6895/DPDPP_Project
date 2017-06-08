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
import android.widget.EditText;
import android.widget.ToggleButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddrecordActivity extends AppCompatActivity {

    private Button btn_addrecordOK;
    private ToggleButton tbtn_food,tbtn_traffic,tbtn_home,tbtn_ent,tbtn_else,tbtn_income;
    private EditText money,describe;
    private String choose;
    private String chooseBook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addrecord);
        money = (EditText)findViewById(R.id.editText_money);
        describe = (EditText)findViewById(R.id.editText_describe);
        btn_addrecordOK = (Button)findViewById(R.id.btn_addrecordOK);
        tbtn_food = (ToggleButton)findViewById(R.id.tbtn_food);
        tbtn_traffic = (ToggleButton)findViewById(R.id.tbtn_traffic);
        tbtn_home = (ToggleButton)findViewById(R.id.tbtn_home);
        tbtn_ent = (ToggleButton)findViewById(R.id.tbtn_ent);
        tbtn_else = (ToggleButton)findViewById(R.id.tbtn_else);
        tbtn_income = (ToggleButton)findViewById(R.id.tbtn_income);

        Bundle bundle  = getIntent().getExtras();
        chooseBook = bundle.getString("Book");
        //Log.e("CHOOSEBOOK",chooseBook);
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
        //choose = new String();

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
                String data = new String();
                Bundle bundle = new Bundle();
                bundle.putString("Data",add());
                bundle.putString("Book",chooseBook);
                intent.putExtras(bundle);
                startActivity(intent);
                AddrecordActivity.this.finish();

            }
        });
    }
    public String add() {
        String inputData = new String();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String cdate = dateFormat.format(date);
        String getDescribe = describe.getText().toString();
        String getMoney = money.getText().toString().substring(1);
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
    void setCheckfalse() {
        tbtn_food.setChecked(false);
        tbtn_traffic.setChecked(false);
        tbtn_home.setChecked(false);
        tbtn_ent.setChecked(false);
        tbtn_else.setChecked(false);
        tbtn_income.setChecked(false);
    }
}
