package com.lanhuispace.tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class SysUtils {

    //取得当前时间 字符 String
    public String getDateTime(){
        this.setDefaultTimeZone();//设置默认系统时区东八区 中国北京时区
        String datetime = null;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        datetime = df.format(new Date());
        return datetime;
    }

    //系统时区
    public String getTimeZone(){
        return TimeZone.getDefault().getDisplayName();
    }

    //设置默认系统时区东八区 中国北京时区
    private void setDefaultTimeZone(){
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
    }
}
