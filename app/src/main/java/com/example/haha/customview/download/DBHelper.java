package com.example.haha.customview.download;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by haha on 2019/7/1.
 * 数据库帮助类
 */

public class DBHelper extends SQLiteOpenHelper {

    //数据库名称
    private static final String DB_NAME = "download.db";
    //数据库版本
    private static final int VERSION = 1;
    //创建数据库表sql语句
    private static final String SQL_CREATE = "create table thread_info(id integer primary key autoincrement,thread_id integer" +
            ",url text,start integer,end integer,finished integer)";
    //删除数据库表语句
    private static final String SQL_DROP = "drop table if exists thread_info";

    public DBHelper(Context context) {
        super(context,DB_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP);
    }
}
