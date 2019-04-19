package com.wangshijiapu.wsjp.registers;

import android.database.ContentObserver;
import android.os.Handler;
import android.os.Message;

public class DownloadApkChange extends ContentObserver {
    /**
     * Creates a content observer. 监听下载时的变化 目前没用
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */
    public DownloadApkChange(Handler handler) {
        super(handler);
        Message message = handler.obtainMessage();
        message.what = 1;
        message.obj = "倒计时：";
        handler.sendMessage(message);
    }
}
