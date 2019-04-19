package com.wangshijiapu.wsjp.broadcasts;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class DownLoadApkBroadcast extends BroadcastReceiver {
    public static final String TAG = "dApkb";
    private Context mContext;

    public DownLoadApkBroadcast() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        //检测下载完成事件
        if(intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)){
            long downId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            installApk(downId);
        }

    }

    //安装下载的Apk
    private void installApk(long downId) {
        DownloadManager downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri downloadApkUri = downloadManager.getUriForDownloadedFile(downId);
        Intent intent = new Intent();
        //intent.setAction(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//FLAG_GRANT_READ_URI_PERMISSION FLAG_ACTIVITY_NEW_TASK
        intent.setDataAndType(downloadApkUri, "application/vnd.android.package-archive");
        this.mContext.startActivity(intent);
    }
}
