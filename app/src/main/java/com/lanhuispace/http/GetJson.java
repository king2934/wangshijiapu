package com.lanhuispace.http;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetJson {
    private String strUrl;
    private URL url;
    public String data;

    public GetJson(String strurl){
       this.strUrl = strurl;
    }

    public String getDataJson(){
		String data = "";
		HttpURLConnection httpConn = null;
		BufferedReader br = null;
        try {
            this.url = new URL(this.strUrl);
            httpConn = (HttpURLConnection) this.url.openConnection();
			httpConn.setRequestMethod("GET");//请求方式 GET/POST
			httpConn.setConnectTimeout(5000);//连接超时为5秒
			httpConn.setReadTimeout(5000);//读取数据超时为5秒			
            httpConn.setDoOutput(true);//设置运行输出
            httpConn.setDoInput(true);//设置运行输入
			httpConn.setUseCaches(false);//Post方式不能缓存,需手动设置为false
			httpConn.setRequestProperty("Charset", "UTF-8");//编码格式
			//httpConn.setRequestProperty("MyProperty", "this is me!");//传递自定义参数
			
			InputStream is = httpConn.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));
			
			String str;
			while( (str=br.readLine())!=null ){
				data += str;
			}
            Log.d("getdata","OK:HttpURLConnection");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d("getdata","Error:new URL(strUrl)");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("getdata","Error:HttpURLConnection");
        } finally {
			if(httpConn!=null){
				httpConn.disconnect();
			}
			if (br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
		}
        return data;
    }
}