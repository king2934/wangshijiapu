package com.wangshijiapu.wsjp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.lanhuispace.tools.SysUtils;

import androidx.annotation.Nullable;

public class SQLiteDB extends SQLiteOpenHelper {
    public static final String TAG = "SQLiteDB";
    public static final String DB_NAME = "wsjp.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_CACHE_UPDATE = "cache_table_update";
    public static final String TABLE_ZIBEI = "zibei";

    private SQLiteDatabase database = null;

    public SQLiteDB(@Nullable Context context,  @Nullable SQLiteDatabase.CursorFactory factory) {
        super(context, DB_NAME, factory, DB_VERSION);
        Log.d(TAG,"new SQLiteDB");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.init_db(db);//初始化数据库
    }

    //初始化数据库
    private void init_db(SQLiteDatabase db) {
        this.init_tables(db);
        Log.d(TAG,"create 表 "+TABLE_CACHE_UPDATE);

        this.init_table_zibei(db);
        Log.d(TAG,"create 表 "+TABLE_ZIBEI);
    }

    //字辈表
    private void init_table_zibei(SQLiteDatabase db) {
        String sql = "create table if not exists " + TABLE_ZIBEI + " ( "
                +"id integer primary key autoincrement,"
                +"sort integer not null ,"
                +"name varchar(2) not null"
                +")";
        db.execSQL(sql);
    }

    //初始化数据库 表 缓存表
    private void init_tables(SQLiteDatabase db) {
        String sql = "create table if not exists "+TABLE_CACHE_UPDATE+" ("
                +"id integer primary key autoincrement,"
                +"tbname varchar(32) not null,"
                +"updatedon datatime"
                +")";
        db.execSQL(sql);

        ContentValues cval = new ContentValues();
        cval.put("id",0);
        cval.put("tbname","表名");
        cval.put("updatedon",new SysUtils().getDateTime());

        db.insert(this.TABLE_CACHE_UPDATE,null,cval);
    }

    //所有表
    public void show_tables(){
        this.open();
        if(this.database.isOpen()){
            Log.d(TAG,"数据库已经打开...");
            Cursor cursor = this.database.rawQuery("select name from sqlite_master where type='table' order by name", null);
            while(cursor.moveToNext()){
                //遍历出表名
                String name = cursor.getString(0);
                Log.d(TAG, "表名 "+name);
            }
        }
    }

    //打开数据库
    public void open(){
        if(this.database==null){
            this.database = this.getReadableDatabase();
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
