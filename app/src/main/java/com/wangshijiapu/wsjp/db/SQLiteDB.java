package com.wangshijiapu.wsjp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.lanhuispace.tools.SysUtils;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class SQLiteDB extends SQLiteOpenHelper {
    public static final String TAG = "SQLiteDB";
    public static final String DB_NAME = "wsjp.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_CACHE_UPDATE = "cache_table_update";
    public static final String TABLE_ZIBEI = "zibei";

    private SQLiteDatabase database = null;
    private SysUtils sysutil = null;

    public SQLiteDB(@Nullable Context context,  @Nullable SQLiteDatabase.CursorFactory factory) {
        super(context, DB_NAME, factory, DB_VERSION);
        Log.d(TAG,"new SQLiteDB");
		this.sysutil = new SysUtils();
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
        cval.put("updatedon",this.sysutil.getDateTime());//
		
		ContentValues cv_zibei = new ContentValues();
		cv_zibei.put("id",1);
        cv_zibei.put("tbname","zibei");
        cv_zibei.put("updatedon","2019-01-01 00:00:01");

        db.insert(this.TABLE_CACHE_UPDATE,null,cval);
        db.insert(this.TABLE_CACHE_UPDATE,null,cv_zibei);
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
    
	//查看缓存表数据
	public void show_tables_caches(){
        this.open();//打开数据库

        /**
         * 查询获得游标
         * query(参数...)
         * 参数1：表名
         * 参数2：要想显示的列
         * 参数3：where子句
         * 参数4：where子句对应的条件值
         * 参数5：分组方式
         * 参数6：having条件
         * 参数7：排序方式
         */
		Cursor cursor = this.database.query (
		        TABLE_CACHE_UPDATE,
                new String[]{"id","tbname","updatedon"},
                "id=?",
                new String[]{"0"},null,null,null);
        //Cursor cursor = this.database.rawQuery("select * from "+TABLE_CACHE_UPDATE,null);
		//
        while(cursor.moveToNext()){
            //遍历出表名
            String id = cursor.getString(cursor.getColumnIndex("id"));
            String tbname = cursor.getString(cursor.getColumnIndex("tbname"));
            String updatedon = cursor.getString(cursor.getColumnIndex("updatedon"));
            Log.d(TAG, "data[ id:"+id+", tbname:"+tbname+", updatedon:"+updatedon+" ] ");
        }
	}

	//取一个时间
    public String getTableCacheUpdatedon(){
        this.open();//打开数据库
        Cursor cursor = this.database.query (
                TABLE_CACHE_UPDATE,
                new String[]{"updatedon"},
                "id=?",
                new String[]{"0"},null,null,null);
        String updatedon = null;
        while(cursor.moveToNext()){
            updatedon = cursor.getString(cursor.getColumnIndex("updatedon"));
        }
        return updatedon;
    }
	
	//自动检查表是否要更新
	public List AutoCheckCacheTableUpdate(){
		List list = new ArrayList();
		this.open();//打开数据库
		Cursor cursor = this.database.query (TABLE_CACHE_UPDATE,null,null,null,null,null,null);
		while(cursor.moveToNext()){
			String id = cursor.getString(cursor.getColumnIndex("id"));
            String tbname = cursor.getString(cursor.getColumnIndex("tbname"));
            String updatedon = cursor.getString(cursor.getColumnIndex("updatedon"));
			int diss = this.sysutil.getIntCompareDatetimeHour(this.sysutil.getDateTime(),updatedon);
			if(diss>=24 && id!="0"){
				list.add(tbname);
			}
		}
		return list;
	}
	
    //打开数据库
    public void open(){
        if(this.database==null){
            this.database = this.getReadableDatabase();//getReadableDatabase getWritableDatabase
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //检查本地字辈表(zibei)数据保质期
    public boolean isExpireZibei() {
        this.open();//打开数据库
        Cursor cursor = this.database.query (
                TABLE_CACHE_UPDATE,
                new String[]{"updatedon"},
                "id=?",
                new String[]{"1"},null,null,null);
        cursor.moveToFirst();
        String updatedon = cursor.getString(0);
        int diss = this.sysutil.getIntCompareDatetimeHour(this.sysutil.getDateTime(),updatedon);
        if(diss>=24){
            return true;
        }
        return false;
    }

    //接收传递过来的数据写到本地库中 字辈 zibei
    public void putDataZiBei(JSONArray jsonArray) {

    }
}
