package com.wangshijiapu.wsjp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_main);
    }
}
