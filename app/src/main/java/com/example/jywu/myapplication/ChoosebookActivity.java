package com.example.jywu.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class ChoosebookActivity extends AppCompatActivity {

    private Spinner spinner_choosebook;
    private Button btn_addbook,btn_deletebook;
    public final ArrayList<String> book  =  new ArrayList<>();
    public int bookposition = 0;
    private boolean checkInputOrNot = false;
    String chooseBook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosebook);
        btn_addbook = (Button)findViewById(R.id.btn_addbook);
        btn_deletebook = (Button)findViewById(R.id.btn_deletebook);
        spinner_choosebook = (Spinner)findViewById(R.id.spinner_choosebook);
        Bundle bundle  = getIntent().getExtras();
        chooseBook = bundle.getString("Book");
        book.add("我的帳本");
        book.add("公司帳本");
        book.add("實驗室基金");
        book.add("私房錢");
        int index = findIndexofChoose(chooseBook);
        ArrayAdapter<String> lunchList = new ArrayAdapter<>(ChoosebookActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                book);
        spinner_choosebook.setAdapter(lunchList);
        spinner_choosebook.setSelection(index);
        spinner_choosebook.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ChoosebookActivity.this, "你選的是" + book.get(position), Toast.LENGTH_SHORT).show();
                bookposition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btn_addbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInputOrNot = true;
                Intent intent = new Intent();
                intent.setClass(ChoosebookActivity.this,MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Book",book.get(bookposition));
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });

        btn_deletebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                book.remove(bookposition);

            }
        });
    }
    public int findIndexofChoose(String input) {
        for(int i =0; i<book.size();i++) {
            if(input.equals(book.get(i))) {
                return i;
            }
        }
        return 0;
    }
    @Override
    public void onBackPressed() {
        if(!checkInputOrNot) {
            Intent intent = new Intent();
            intent.setClass(ChoosebookActivity.this,MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("Book",chooseBook);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
    }
}
