package com.wangshijiapu.wsjp.threads.runnables;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.lanhuispace.http.GetJson;
import com.wangshijiapu.wsjp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class CheckUpdateAppVersion implements Runnable{
    private static final String TAG = "VersionCode";
    protected Context mContext;
	private String Data;
	
	private String package_title;
	private String package_description;
	private String package_download_address;
	private String package_download_filepath;
	private String package_download_filename;
	private String localhost_download_file;
	
	
    public CheckUpdateAppVersion(Context context) {
        mContext = context;
		this.init();
	}
	
	//初始化
	private void init(){
		this.package_title = null;
		this.package_description = null;
		this.package_download_address = null;
		this.package_download_filepath = null;
		this.package_download_filename = null;
		this.localhost_download_file = null;
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
				this.package_title = jsonObject.getString("title");
				this.package_description = jsonObject.getString("description");
				this.package_download_address = jsonObject.getString("download_address");
				this.package_download_filepath = jsonObject.getString("download_filepath");
				this.package_download_filename = jsonObject.getString("download_filename");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
		
		if(this.package_title==null){
			this.package_title = "王氏家谱APP";
		}
		if(this.package_description==null){
			this.package_description = "王嘴王氏家族专用的APP软件";
		}
		if(this.package_download_filepath==null){
			this.package_download_filepath = "wsjp_downloads";
		}
		if(this.package_download_filename==null){
			this.package_download_filename = "wsjp.apk";
		}
		
        return serverCode;
	}

	//检查本地是否已经下载过更新包了
	private boolean isDownloadApkExists() {
		this.localhost_download_file = Environment.getExternalStorageDirectory().getPath()+"/"+this.package_download_filepath+"/"+this.package_download_filename;
		File apkFile = new File(this.localhost_download_file);
		if(apkFile.exists()){
			return true;
		}else{
			return false;
		}
	}
	
	//下载APK
	private void downloadApk(){

        if( this.package_download_address != null ){

            DownloadManager downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
			
			//调用Request下载
			DownloadManager.Request request = new DownloadManager.Request(Uri.parse(this.package_download_address));
			
			//保存在SD卡文件夹 this.package_download_filepath 下 文件名是 this.package_download_filename
			request.setDestinationInExternalPublicDir(this.package_download_filepath, this.package_download_filename);
			request.setTitle(this.package_title);//设置下载中通知栏提示的标题
			request.setDescription(this.package_description);//设置下载中通知栏提示的介绍
			
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
			request.setMimeType("application/vnd.android.package-archive");//application/cn.trinea.download.file
			
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
	
	//检查本下载的apk文件是否比已安装的包新
	public boolean isUpdateDownloadApk(){
		PackageManager packageManager = this.mContext.getPackageManager();
		PackageInfo pgInfo = packageManager.getPackageArchiveInfo(this.localhost_download_file, PackageManager.GET_ACTIVITIES);
		//对比下载文件和已安装的包版本大小
		if( pgInfo.versionCode > this.getVersionCodeLocal() ){
			return true;
		}else{
			return false;
		}
	}

	//安装下载的Apk
	private void installApk() {
		Intent intent = new Intent();
		File file = new File(this.localhost_download_file);
		Uri uri = Uri.fromFile(file);
		intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//FLAG_GRANT_READ_URI_PERMISSION FLAG_ACTIVITY_NEW_TASK
		intent.setDataAndType(uri, "application/vnd.android.package-archive");
		this.mContext.startActivity(intent);
	}
	
    @Override
    public void run() {	
				
		//检查本地与远程服务器是否有更新
		if(this.isUpdateApk()){
			Log.d(TAG,"检测出新版本...");
			
			//检查本地是否已经有下载的更新包
			if(this.isDownloadApkExists()){
				
				//下载的包对比已安装的包
				if(this.isUpdateDownloadApk()){
					this.installApk();
				}
			}else{
				this.downloadApk();
			}			
		}
    }

}
