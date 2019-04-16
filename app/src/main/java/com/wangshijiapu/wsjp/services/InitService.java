package com.wangshijiapu.wsjp.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.wangshijiapu.wsjp.threads.TaskLocalSQLiteDB;

import androidx.annotation.Nullable;

public class InitService extends Service {

    public InitService() {
        super();
    }

    //创建服务时调用
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("getdata", "InitService类 onCreate");
    }

    //服务执行的操作
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("getdata", "InitService类 onStartCommand");
        new TaskLocalSQLiteDB(getBaseContext()).start();
        return super.onStartCommand(intent, flags, startId);
    }

    //销毁服务时调用
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
