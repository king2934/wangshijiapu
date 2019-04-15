package com.wangshijiapu.wsjp.threads;

import android.util.Log;

public class InitThreadSQLiteDB extends Thread {

    @Override
    public void run() {
        super.run();
        int i=0;
        while (true){
            try {
                i++;
                sleep(1000);
                Log.d("getdata","start "+i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
