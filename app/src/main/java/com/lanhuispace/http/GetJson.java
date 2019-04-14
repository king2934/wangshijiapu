package com.lanhuispace.http;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetJson {
    private String strUrl;
    private URL url;
    public String data;

    public GetJson(String strurl){
       this.strUrl = strurl;
    }

    public void getDataJson(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection conn=null;
                BufferedReader br=null;
                try {
                    URL url=new URL("https://www.wangshijiapu.com/api/zibei.php");
                    conn= (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(8000);
                    conn.setReadTimeout(8000);
                    InputStream in=conn.getInputStream();
                    br=new BufferedReader(new InputStreamReader(in));

                    StringBuilder sb=new StringBuilder();
                    String s;
                    while((s = br.readLine())!=null){
                        sb.append(s);
                    }
                    data = sb.toString();
                    Log.d("data","---"+sb.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("haha",e.getMessage());
                }finally {
                    if (conn!=null){
                        conn.disconnect();
                    }
                    if (br!=null){
                        try {
                            br.close();
                        } catch (IOException e) {
                            e.printStackTrace();

                        }
                    }
                }
            }
        }).start();
    }
}
