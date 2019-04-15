package com.wangshijiapu.wsjp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lanhuispace.http.GetJson;
import com.lanhuispace.sqlite.SQLiteDB;
import com.wangshijiapu.wsjp.services.InitService;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(this,InitService.class));
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_main);

        final TextView tv = findViewById(R.id.id_main_TextView);
        tv.setText("wangshijiapu");

        Button btn = findViewById(R.id.id_main_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("开始改变");
                SQLiteDB sdb = new SQLiteDB(null,null);
                SQLiteDatabase db = sdb.getReadableDatabase();
                Cursor cursor = db.query("zibei",new String[]{"id","name"},"id=?",new String[]{"1"},null, null, null, null);
                Toast.makeText(getApplicationContext(),"Button点击事件1",Toast.LENGTH_LONG).show();
            }
        });
    }


}


