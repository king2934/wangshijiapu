package com.wangshijiapu.wsjp.broadcasts;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class DownLoadApkBroadcast extends BroadcastReceiver {
    public static final String TAG = "dApkb";
    private Context mContext;

    public DownLoadApkBroadcast(Context context) {
        mContext = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
		Log.d(TAG,"接收到了广播，文件可能已经下载完成。");
        Toast.makeText(context,"接收下载完成广播",Toast.LENGTH_LONG).show();
		//long ID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
        //Log.d(TAG,"广播"+ID);
    }
}
