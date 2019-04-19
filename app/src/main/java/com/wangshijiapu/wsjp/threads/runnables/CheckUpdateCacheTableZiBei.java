package com.wangshijiapu.wsjp.threads.runnables;

import android.content.Context;
import android.util.Log;

import com.lanhuispace.http.GetJson;
import com.wangshijiapu.wsjp.R;
import com.wangshijiapu.wsjp.db.SQLiteDB;

public class CheckUpdateCacheTableZiBei implements Runnable{
    private static final String TAG="CKzibei";
    protected Context mContext;

    public CheckUpdateCacheTableZiBei(Context context) {
        this.mContext = context;
    }

    @Override
    public void run() {
        Log.d(TAG,"任务 检查更新 字辈分表");
        SQLiteDB sdb = new SQLiteDB(this.mContext,null);

        //检查本地表是否要更新
        if(sdb.isExpireZibei()){
            Log.d(TAG,"检查更新，要更新...");
            String url_home = mContext.getResources().getString(R.string.url_app_link_main);
            String strUrl = url_home + mContext.getResources().getString(R.string.url_zibei);
            sdb.putDataZiBei(new GetJson(strUrl).getDataJson());
        }
        //sdb.getDataZiBei();
    }
}
