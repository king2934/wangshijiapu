package com.wangshijiapu.wsjp.threads;

import android.content.Context;
import android.util.Log;

import com.wangshijiapu.wsjp.db.SQLiteDB;

import java.util.List;

public class TaskLocalSQLiteDB extends Thread{
    public static final String TAG = "TaskSQDB";
    private Context mContext;
    private SQLiteDB sdb;

    //构造
    public TaskLocalSQLiteDB(Context context) {
        super();
        this.mContext = context;
    }

    @Override
    public void run() {
        super.run();
        sdb = new SQLiteDB(mContext,null);
        //sdb.show_tables();
        //sdb.show_tables_caches();
        List uptables = sdb.AutoCheckCacheTableUpdate();
        for(int i=0;i<uptables.size();i++){
            Log.d(TAG,"需要更新的表是："+uptables.get(i));
        }
        Log.d(TAG,"run...");

        int i=0;
        while(true){
            try {
                i++;
                sleep(1000);
                Log.d(TAG,"run "+i);
                if(i>=3){
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
