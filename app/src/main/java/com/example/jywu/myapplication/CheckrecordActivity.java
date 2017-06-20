package com.example.jywu.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import static android.R.id.list;

public class CheckrecordActivity extends AppCompatActivity {
    ListView showDatabase;
    MyDBHelper DBhelper;
    private String[] list = {"book","cdate","info","amount","remarks"};
    private ArrayAdapter<String> listAdapter;
    private SimpleCursorAdapter cursorAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkrecord);
        showDatabase = (ListView)findViewById(R.id.listDatabase);
        DBhelper = new MyDBHelper(this,"hello",null,1);
        SQLiteDatabase db = DBhelper.getReadableDatabase();
        Cursor c = db.query(DBhelper.TABLE_NAME,null,null,null,null,null,null);
        //listAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        cursorAdapter = new SimpleCursorAdapter(this,R.layout.databaselayout,c,list,new int[] {R.id.book,R.id.cdate,R.id.info,R.id.amount,R.id.remark});
        showDatabase.setAdapter(cursorAdapter);
        showDatabase.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "你選擇的是" + list[position], Toast.LENGTH_SHORT).show();
            }
        });
        while(c.moveToNext()) {
             Log.e("id",c.getInt(0)+"");
             Log.e("book",c.getString(1));
             Log.e("Date",c.getString(2));
             Log.e("info",c.getString(3)+"");
             Log.e("remarks",c.getString(4)+"");
             Log.e("money",c.getInt(5)+"");
        }
    }
    /* <TextView
        android:text="消費總計"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignBottom="@+id/tv_incometext"
        android:layout_centerHorizontal="true"
        android:id="@+id/tv_outcometext" />

    <TextView
        android:text="收入總計"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginStart="30dp"
        android:layout_marginTop="74dp"
        android:id="@+id/tv_incometext"
        android:layout_alignParentTop="true"
        android:layout_alignParentStart="true" />

    <TextView
        android:text="損益總計"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignBottom="@+id/tv_outcometext"
        android:layout_toEndOf="@+id/tv_outcometext"
        android:layout_marginStart="63dp"
        android:id="@+id/tv_totaltext" />

    <TextView
        android:text="$2000"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_below="@+id/tv_incometext"
        android:layout_alignStart="@+id/tv_incometext"
        android:layout_marginTop="15dp"
        android:id="@+id/tv_incomemoney" />

    <TextView
        android:text="$1000"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignTop="@+id/tv_incomemoney"
        android:layout_centerHorizontal="true"
        android:id="@+id/tv_outcomemoney" />

    <TextView
        android:text="$1000"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignBottom="@+id/tv_outcomemoney"
        android:layout_alignStart="@+id/tv_totaltext"
        android:id="@+id/tv_totalmoney" />
        */
}
