package com.lanhuispace.tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SysUtils {

    //取得当前时间 字符 String
    public String getDateTime(){
        String datetime = null;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        datetime = df.format(new Date());
        return datetime;
    }
}
