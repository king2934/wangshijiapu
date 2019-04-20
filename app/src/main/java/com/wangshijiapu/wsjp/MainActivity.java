package com.wangshijiapu.wsjp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.ShapeBadgeItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
import com.wangshijiapu.wsjp.db.SQLiteDB;
import com.wangshijiapu.wsjp.fragments.ContactsFragment;
import com.wangshijiapu.wsjp.fragments.FamilyFragment;
import com.wangshijiapu.wsjp.fragments.HomeFragment;
import com.wangshijiapu.wsjp.fragments.MyTreeFragment;
import com.wangshijiapu.wsjp.fragments.UserFragment;
import com.wangshijiapu.wsjp.listeners.MyBottomNavBarSelectedListener;
import com.wangshijiapu.wsjp.services.InitService;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Fragment> mFragmentArrayList;
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
        this.initFragment();
        this.initBottomNavigationBar();
    }

    //初始化
    private void initFragment() {
        mFragmentArrayList = new ArrayList<Fragment>();
        mFragmentArrayList.add(new HomeFragment());
        mFragmentArrayList.add(new FamilyFragment());
        mFragmentArrayList.add(new ContactsFragment());
        mFragmentArrayList.add(new MyTreeFragment());
        mFragmentArrayList.add(new UserFragment());

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        for (int i = 0 ;i < mFragmentArrayList.size(); i++){
            Fragment fragment = mFragmentArrayList.get(i);
            ft.add(R.id.layout_fragment,fragment);
            ft.hide(fragment);
            if(i==0){
                ft.show(fragment);
            }
        }
        ft.commit();
    }

    //底部导航
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

        mBottomNavigationBar.setTabSelectedListener(new MyBottomNavBarSelectedListener(this,mFragmentArrayList));
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


