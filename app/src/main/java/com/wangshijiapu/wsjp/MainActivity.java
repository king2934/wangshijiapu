package com.wangshijiapu.wsjp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.wangshijiapu.wsjp.db.SQLiteDB;
import com.wangshijiapu.wsjp.services.InitService;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    protected BottomNavigationBar mBottomNavigationBar;
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
        this.initBottomNavigationBar();

    }

    private void initBottomNavigationBar(){
        mBottomNavigationBar = findViewById(R.id.bottom_navigation_bar);

        mBottomNavigationBar
			.setMode(BottomNavigationBar.MODE_FIXED)
            .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);

        mBottomNavigationBar
			.setActiveColor(R.color.colorAccent)//选中颜色 图标和文字
            .setInActiveColor(R.color.DarkGray)//默认未选择颜色
            .setBarBackgroundColor(R.color.White);//默认背景色
		
        mBottomNavigationBar
			.addItem(new BottomNavigationItem(R.drawable.home_light,"首页"))
			.addItem(new BottomNavigationItem(R.drawable.circle,"谱系"))
			.addItem(new BottomNavigationItem(R.drawable.my_light,"个人"))
			.setFirstSelectedPosition(0)
			.initialise();
    }

    public void btnGetUpdatedon(View view) {
        SQLiteDB sdb = new SQLiteDB(getBaseContext(),null);
        String datetime = sdb.getTableCacheUpdatedon();

        TextView tv = findViewById(R.id.id_main_TextView);
        String zb = sdb.getDataZiBei();
        tv.setText("取自数据库：["+zb+"]");
    }
}


