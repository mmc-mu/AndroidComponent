package com.example.lib_net.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.concurrent.locks.Lock;

/**
 * Created by wangjiao on 2019/3/6.
 */

public abstract class BaseDao<T> {
    protected String TAG;
    protected Lock lock;
    protected SQLiteOpenHelper mHelper;
    protected SQLiteDatabase db;

    public BaseDao(SQLiteOpenHelper helper){
        TAG= getClass().getName();
        lock = DbHelper.lock;
        this.mHelper = helper;
        this.db = openWriter();
    }
    private final void closeDatabase(SQLiteDatabase db, Cursor cursor){
        if(cursor!=null && !cursor.isClosed())cursor.close();
        if(db!=null && db.isOpen()) db.close();
    }


    protected SQLiteDatabase openWriter(){
        return mHelper.getWritableDatabase();
    }
    private SQLiteDatabase openReader(){
        return mHelper.getReadableDatabase();
    }
    /** 获取对应的表名 */
    public abstract String getTableName();
    public abstract void unInit();

    /** 将Cursor解析成对应的JavaBean */
    public abstract T parseCursorToBean(Cursor cursor);

    /** 需要替换的列 */
    public abstract ContentValues getContentValues(T t);
}
