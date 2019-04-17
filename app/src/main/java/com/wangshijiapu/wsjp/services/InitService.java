package com.wangshijiapu.wsjp.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.wangshijiapu.wsjp.threads.runnables.CheckUpdateCacheTableZiBei;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;

public class InitService extends Service {
    public static final String TAG = "InitService";
    public InitService() {
        super();
    }

    //创建服务时调用
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    //服务执行的操作
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        //new TaskLocalSQLiteDB(getBaseContext()).start();
        /*
        * 1、newFixedThreadPool()
        * 2、newCachedThreadPool()
        * 3、newSingleThreadExecutor()
        * 4、newScheduledThreadPool()
        * 5、newSingleThreadScheduledExecutor()
        * ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
        * ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
        * ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        * ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        * ScheduledExecutorService singleThreadScheduledPool = Executors.newSingleThreadScheduledExecutor();
        */
        //定时周期性线程池
        ScheduledExecutorService task =   Executors.newScheduledThreadPool(5);

        //延迟0秒后，每隔10分钟执行一次该任务
        /*
        * TimeUnit.DAYS          //天
        * TimeUnit.HOURS         //小时
        * TimeUnit.MINUTES       //分钟
        * TimeUnit.SECONDS       //秒
        * TimeUnit.MILLISECONDS  //毫秒
        */

        //任务 检查更新 字辈分表
        task.scheduleWithFixedDelay(new CheckUpdateCacheTableZiBei(getBaseContext()), 0, 10, TimeUnit.SECONDS);

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
