package com.wangshijiapu.wsjp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.ShapeBadgeItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
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
		
		TextBadgeItem numberBadgeItem = new TextBadgeItem();
		ShapeBadgeItem shapeBadgeItem = new ShapeBadgeItem();
        
        mBottomNavigationBar
			.addItem(new BottomNavigationItem(R.drawable.home_light,"首页"))
			.addItem(new BottomNavigationItem(R.drawable.jp_book,"家谱").setBadgeItem(shapeBadgeItem))
			.addItem(new BottomNavigationItem(R.drawable.people_list,"联系人"))
			.addItem(new BottomNavigationItem(R.drawable.circle,"谱系"))
			.addItem(new BottomNavigationItem(R.drawable.my_light,"个人").setBadgeItem(numberBadgeItem))
			.setFirstSelectedPosition(0)
			.initialise();
		numberBadgeItem.setText("3");
    }

    public void btnGetUpdatedon(View view) {
        SQLiteDB sdb = new SQLiteDB(getBaseContext(),null);
        String datetime = sdb.getTableCacheUpdatedon();

        TextView tv = findViewById(R.id.id_main_TextView);
        String zb = sdb.getDataZiBei();
        tv.setText("取自数据库：["+zb+"]");
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}


