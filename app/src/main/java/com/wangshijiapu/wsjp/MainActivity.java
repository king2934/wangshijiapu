package com.wangshijiapu.wsjp;

import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.lanhuispace.http.GetJson;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_main);
        Log.d("data","activity_main");
        new GetJson("https://").getDataJson();
    }


}
