package com.wangshijiapu.wsjp.threads.runnables;

import android.content.Context;
import android.util.Log;

import com.lanhuispace.http.GetJson;
import com.wangshijiapu.wsjp.db.SQLiteDB;

public class CheckUpdateCacheTableZiBei implements Runnable{
    private static final String TAG="geturlzibei";
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
            String strUrl = "https://www.wangshijiapu.com/api/zibei.php";
            sdb.putDataZiBei(new GetJson(strUrl).getDataJson());

            /*
            GetJson getJson = new GetJson(strUrl);
            String jsonData = getJson.getDataJson();
            Log.d(TAG,jsonData);
            try {
                JSONObject jsonObject = new JSONObject(jsonData);
                int ResultCode = jsonObject.getInt("ResultCode");
                if(ResultCode==0){
                    JSONArray jsonArray = jsonObject.getJSONArray("Data");
                    sdb.putDataZiBei(jsonArray);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            /**/
        }
    }
}
