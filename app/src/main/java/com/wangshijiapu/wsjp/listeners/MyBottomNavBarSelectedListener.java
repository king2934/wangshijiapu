package com.wangshijiapu.wsjp.listeners;

import android.content.Context;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.wangshijiapu.wsjp.MainActivity;

import java.util.ArrayList;

public class MyBottomNavBarSelectedListener implements BottomNavigationBar.OnTabSelectedListener {
    private static final String TAG = "NavBar";
    private Context mContext;
    private MainActivity mMainActivity;
    private ArrayList<Fragment> mFragmentArrayList;

    private int ii = 0;
    public MyBottomNavBarSelectedListener(MainActivity mainActivity, ArrayList<Fragment> mfragments) {
        mMainActivity = mainActivity;
        mContext = mainActivity;
        mFragmentArrayList = mfragments;
    }

    private void showFragment(int index){
        FragmentTransaction ft = mMainActivity.getSupportFragmentManager().beginTransaction();
        Fragment fragment = mFragmentArrayList.get(index);
        ft.show(fragment);
        ft.commit();
    }
    private void hideFragment(int index){
        FragmentTransaction ft = mMainActivity.getSupportFragmentManager().beginTransaction();
        Fragment fragment = mFragmentArrayList.get(index);
        ft.hide(fragment);
        ft.commit();
    }

    private void showTransaction(int index){
        FragmentTransaction ft = mMainActivity.getSupportFragmentManager().beginTransaction();
        hideTransaction(ft);
        Fragment fragment = mFragmentArrayList.get(index);
        ft.show(fragment);
        ft.commit();

    }

    private void hideTransaction(FragmentTransaction ft){
        for (int i = 0 ;i < mFragmentArrayList.size(); i++){
            Fragment fragment = mFragmentArrayList.get(i);
            if( fragment != null ){
                ft.hide(fragment);
            }
        }
    }


    @Override
    public void onTabSelected(int position) { //未选中 -> 选中
        //showTransaction(position);
        showFragment(position);
        //选择一个 置为活动
        Log.d(TAG,"未选中 -> 选中 "+position);
    }

    @Override
    public void onTabUnselected(int position) { //选中 -> 未选中
        // 上一个
        hideFragment(position);
        Log.d(TAG,"选中 -> 未选中 "+position);
    }

    @Override
    public void onTabReselected(int position) { //选中 -> 选中
        //再次选择当前活动
        Log.d(TAG,"选中 -> 选中 :"+position);
    }
}
