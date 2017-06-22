package com.example.jywu.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.audiofx.LoudnessEnhancer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import static android.R.id.list;

public class CheckrecordActivity extends AppCompatActivity {
    ListView showDatabase;
    MyDBHelper DBhelper;
    private String[] list = {"book","cdate","info","amount","remarks"};
    private ArrayAdapter<String> listAdapter;
    private SimpleCursorAdapter cursorAdapter;
    private Cursor c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkrecord);
        showDatabase = (ListView)findViewById(R.id.listDatabase);
        DBhelper = new MyDBHelper(this,"hello",null,1);
        SQLiteDatabase db = DBhelper.getReadableDatabase();
        c = db.query(DBhelper.TABLE_NAME,null,null,null,null,null,null);
        //listAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        cursorAdapter = new SimpleCursorAdapter(this,R.layout.databaselayout,c,list,new int[] {R.id.book,R.id.cdate,R.id.info,R.id.amount,R.id.remark});
        showDatabase.setAdapter(cursorAdapter);
        showDatabase.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), "你選擇的是" + list[position], Toast.LENGTH_SHORT).show();
                c.moveToPosition(position);
                //c.getString(6);
                Log.e("photo",c.getString(6));
                startShowPhoto(c.getString(6),c.getDouble(7),c.getDouble(8));
            }
        });
    }
    public void startShowPhoto(String photo,double latitude, double longitude) {
        Intent intent = new Intent();
        intent.setClass(this,ShowPhoto.class);
        Bundle bundle = new Bundle();
        bundle.putString("photo",photo);
        bundle.putDouble("latitude",latitude);
        bundle.putDouble("longitude",longitude);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
