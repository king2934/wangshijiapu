package com.wangshijiapu.wsjp.services;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.wangshijiapu.wsjp.broadcasts.DownLoadApkBroadcast;
import com.wangshijiapu.wsjp.threads.runnables.CheckUpdateAppVersion;
import com.wangshijiapu.wsjp.threads.runnables.CheckUpdateCacheTableZiBei;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;

public class InitService extends Service {
    public static final String TAG = "InitService";
    private Context mContext;
	
	private BroadcastReceiver downLoadApkBroadcast;

    public Handler DownloadApkHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String info= (String) msg.obj;
            Log.d(TAG,"Handler:"+info);
        }
    };

    public InitService() {
        super();
        this.mContext = getBaseContext();
		this.downLoadApkBroadcast = null;
    }

    //创建服务时调用
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
		this.downLoadApkBroadcast = new DownLoadApkBroadcast();
		this.registerBroadcast();//注册广播
		if(this.downLoadApkBroadcast!=null){
			Log.d(TAG, "广播类");
		}
	}

    //服务执行的操作
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");

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
        ScheduledExecutorService taskApp =   Executors.newScheduledThreadPool(1);
		taskApp.scheduleWithFixedDelay(
		    new CheckUpdateAppVersion(getBaseContext()),
			0, 
			30, 
			TimeUnit.MINUTES);
		
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

	//注册广播
	private void registerBroadcast() {
		/**注册service 广播 1.任务完成时 2.进行中的任务被点击*/
       IntentFilter intentFilter = new IntentFilter();
       intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
       intentFilter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);
       registerReceiver(downLoadApkBroadcast, intentFilter);
	}

	//注销广播
    private void unregisterBroadcast(){
        if(downLoadApkBroadcast != null){
            unregisterReceiver(downLoadApkBroadcast);
            downLoadApkBroadcast = null;
        }

    }
	
	
	
    //销毁服务时调用
    @Override
    public void onDestroy() {
        super.onDestroy();
		unregisterBroadcast();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
