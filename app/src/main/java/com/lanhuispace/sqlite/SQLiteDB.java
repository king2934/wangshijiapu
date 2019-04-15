package com.lanhuispace.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class SQLiteDB extends SQLiteOpenHelper {
    public static final String DB_NAME = "wsjp.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_ZIBEI = "zibei";

	// 传递数据库名与版本号给父类
    public SQLiteDB(@Nullable Context context, @Nullable SQLiteDatabase.CursorFactory factory) {
	super(context, DB_NAME, factory, DB_VERSION);
        Log.d("getdata","SQLiteDB类:new SQLiteDB");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("getdata","init 初始化数据库");
		this.init_db(db);//初始化数据库
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

	//初始化数据库
    public void init_db(SQLiteDatabase db){
        this.init_table_zibei(db);
    }
	
	//初始化表 - 字辈
	public void init_table_zibei(SQLiteDatabase db) {
        Log.d("getdata","start create数据库表");
		String tb_sql = "create table if not exists " + TABLE_ZIBEI + " ( "
		+"id integer primary key autoincrement,"
		+"sort integer not null ,"
		+"name varchar(2) not null"
		+")";
		db.execSQL(tb_sql);
	}
	
}
