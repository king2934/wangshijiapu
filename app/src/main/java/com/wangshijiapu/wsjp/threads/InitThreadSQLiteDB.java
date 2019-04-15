package com.wangshijiapu.wsjp.threads;

import android.content.Context;
import android.util.Log;

import com.lanhuispace.sqlite.SQLiteDB;

public class InitThreadSQLiteDB extends Thread {
    private SQLiteDB sdb;
    private Context mContext;
    public InitThreadSQLiteDB(Context context) {
        super();
        mContext = context;
        sdb = new SQLiteDB(mContext,null);
        sdb.getWritableDatabase();
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
                if(i>=9){
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
