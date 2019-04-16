package com.lanhuispace.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class SQLiteDB extends SQLiteOpenHelper {
    public static final String TAG = "getdata";
    public static final String DB_NAME = "wsjp.db";
    public static final int DB_VERSION = 1;
	
    public static final String TABLE_CACHE_UPDATE = "cache_table_update";
    public static final String TABLE_ZIBEI = "zibei";

    private SQLiteDatabase database = null;

	// 传递数据库名与版本号给父类
    public SQLiteDB(@Nullable Context context, @Nullable SQLiteDatabase.CursorFactory factory) {
	super(context, DB_NAME, factory, DB_VERSION);
        Log.d(TAG,"SQLiteDB类:new SQLiteDB");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG,"SQLiteDB类：onCreate init 初始化数据库");
		this.init_db(db);//初始化数据库
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

	//初始化数据库
    private void init_db(SQLiteDatabase db){
        this.init_tables(db);
        Log.d(TAG,"SQLiteDB类：create 表 "+TABLE_CACHE_UPDATE);

        this.init_table_zibei(db);
		Log.d(TAG,"SQLiteDB类：create 表 zibei");
    }
	
	//初始化表 各表更新
	private void init_tables(SQLiteDatabase db){
		String sql = "create table if not exists "+TABLE_CACHE_UPDATE+" ("
		+"id integer primary key autoincrement,"
		+"tbname varchar(32) not null,"
		+"updatedon datatime"
		+")";
        db.execSQL(sql);
	}
	
	//初始化表 - 字辈
	private void init_table_zibei(SQLiteDatabase db) {        
		String tb_sql = "create table if not exists " + TABLE_ZIBEI + " ( "
		+"id integer primary key autoincrement,"
		+"sort integer not null ,"
		+"name varchar(2) not null"
		+")";
		db.execSQL(tb_sql);
	}

	//所有表
    public void show_tables(){
        this.open();
        if(this.database.isOpen()){
            Log.d(TAG,"SQLiteDB类：数据库已经打开...");
			Cursor cursor = this.database.rawQuery("select name from sqlite_master where type='table' order by name", null);
			while(cursor.moveToNext()){
				//遍历出表名
				String name = cursor.getString(0);
				Log.d(TAG, "SQLiteDB类：表名 "+name);
			}
		}        
    }

    //打开数据库
    public void open(){
		if(this.database==null){
			this.database = this.getReadableDatabase();
		}
    }
	
}
