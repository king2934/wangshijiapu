package com.wangshijiapu.wsjp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wangshijiapu.wsjp.db.SQLiteDB;
import com.wangshijiapu.wsjp.services.InitService;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent is = new Intent(this, InitService.class);
        startService(is);//启动一个服务

        try {
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_main);
        //todo
    }


    public void btnGetUpdatedon(View view) {
        SQLiteDB sdb = new SQLiteDB(getBaseContext(),null);
        String datetime = sdb.getTableCacheUpdatedon();

        TextView tv = findViewById(R.id.id_main_TextView);
        String zb = sdb.getDataZiBei();
        tv.setText("取自数据库：["+zb+"]");
    }
}


