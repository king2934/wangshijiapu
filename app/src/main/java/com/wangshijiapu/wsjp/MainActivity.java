package com.wangshijiapu.wsjp;

import android.content.Intent;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.wangshijiapu.wsjp.services.InitService;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent is = new Intent(this, InitService.class);
        startService(is);

        try {
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_main);
        //todo
    }


}


