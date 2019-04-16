package com.wangshijiapu.wsjp.threads;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lanhuispace.sqlite.SQLiteDB;

public class InitThreadSQLiteDB extends Thread {
    private SQLiteDB sdb;
    private Context mContext;
    public InitThreadSQLiteDB(Context context) {
        super();
        mContext = context;
        sdb = new SQLiteDB(mContext,null);
        sdb.show_tables();
        /*
        SQLiteDatabase sqlDB = sdb.getWritableDatabase();
        if(sqlDB.isOpen()){
            Log.d("getdata","thread isOpen");
        }/**/
    }

    @Override
    public void run() {
        super.run();
        int i=0;
        while (true){
            try {
                i++;
                sleep(1000);
                Log.d("getdata","start "+i);
                if(i>=3){
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
