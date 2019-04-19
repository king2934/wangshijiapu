package com.wangshijiapu.wsjp.threads.runnables;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import com.lanhuispace.http.GetJson;
import com.wangshijiapu.wsjp.R;
import com.wangshijiapu.wsjp.registers.DownloadApkChange;

import org.json.JSONException;
import org.json.JSONObject;

public class CheckUpdateAppVersion implements Runnable{
    private static final String TAG = "VersionCode";
    protected Context mContext;
	private String Data;
	protected Handler mDownloadApkHandler;
	protected BroadcastReceiver mDownLoadApkBroadcast;
    public CheckUpdateAppVersion(Context context, Handler downloadApkHandler, BroadcastReceiver downLoadApkBroadcast) {
        mContext = context;
        mDownloadApkHandler = downloadApkHandler;
        mDownLoadApkBroadcast = downLoadApkBroadcast;
	}
	
	//检查APP更新Url
	private String getUrl_CKUP(){
		String url_home = (String) mContext.getResources().getText(R.string.url_app_link_main);
        String url_ckapp = (String) mContext.getResources().getText(R.string.url_check_update_app);
		String ckUrl = url_home + url_ckapp;
		return ckUrl;
	}

	//获取本地App版本号
	private int getVersionCodeLocal()
	{
		PackageManager manager = mContext.getPackageManager();
		int localCode = -1;
        try {
            PackageInfo info = manager.getPackageInfo(mContext.getPackageName(), 0);
            localCode = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
		return localCode;
	}
	
	//获取远程服务器数据
	private String getData(String url){
		this.Data = new GetJson(url).getDataJson();
		return this.Data;
	}
	
	//获取远程服务器App版本号
	private int getVersionCodeServer(String data){
		int serverCode = -0;
        int ResultCode = -1;
        try {
            JSONObject jsonObject = new JSONObject(data);
            ResultCode = jsonObject.getInt("ResultCode");
            if( ResultCode == 0 ){
				serverCode = jsonObject.getInt("versionCode");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return serverCode;
	}
	
	//下载APK
	private void downloadApk(){
        String apkUrl = null;
        try {
            JSONObject jsonObject = new JSONObject(this.Data);
            apkUrl = jsonObject.getString("download_address");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(apkUrl!=null){
            Log.d(TAG,"开始下载..."+apkUrl);

            //DownloadApkChange downloadApkChange = new DownloadApkChange(mDownloadApkHandler);

            DownloadManager downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
			
			//调用Request下载
			DownloadManager.Request request = new DownloadManager.Request(Uri.parse(apkUrl));
			
			//保存在SD卡文件夹Trinea下 文件名是wangshijiapu.apk
			request.setDestinationInExternalPublicDir("Trinea", "wangshijiapu.apk");
			request.setTitle("王氏家谱APP");//设置下载中通知栏提示的标题
			request.setDescription("王嘴王氏家族专用的APP软件");//设置下载中通知栏提示的介绍
			
			//VISIBILITY_VISIBLE_NOTIFY_COMPLETED 表示下载完成后显示通知栏提示 VISIBILITY_HIDDEN 表示不显示任何通知栏提示
			request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
			
			//NETWORK_MOBILE NETWORK_WIFI NETWORK_BLUETOOTH
			//request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
			
			//设置下载文件的mineType
			//用于Activity的intent-filter 响应点击的打开文件。
			/*
			*
				<intent-filter>
					<action android:name="android.intent.action.VIEW" />
					<category android:name="android.intent.category.DEFAULT" />
					<data android:mimeType="application/cn.trinea.download.file" />
				</intent-filter>
			*/
			request.setMimeType("application/cn.trinea.download.file");
			
			
			request.allowScanningByMediaScanner();//允许MediaScanner扫描到这个文件
			long downloadId = downloadManager.enqueue(request);
            
			Log.d(TAG,"downloadId:"+downloadId);
        }
    }

	//检查是否要更新
	public boolean isUpdateApk(){
		boolean isUP = false;
        this.getData(this.getUrl_CKUP());
        int lcode = this.getVersionCodeLocal();
        int scode = this.getVersionCodeServer(this.Data);
        if( scode > lcode){
            isUP = true;
        }
		return isUP;
	}
	
	
    @Override
    public void run() {
		if(this.isUpdateApk()){
			Log.d(TAG,"检测出新版本...");
			this.downloadApk();
		}
    }
}
