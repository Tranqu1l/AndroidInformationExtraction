package com.ididsec.www.androidinformationextraction;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ZMC on 2018/12/18.
 */

public class MyDatebaseHelper extends SQLiteOpenHelper {
    private static String name = "documents.db";
    private static Integer version = 10;
    private static final String CREATE_TABLE="create table documents("
            +"id integer primary key,"
            +"title text,"
            +"content text,"
            +"path text,UNIQUE(path))";
    private Context mContext;
    public MyDatebaseHelper(Context context){
        super(context,name,null,version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        db.execSQL("drop table if exists documents");
        onCreate(db);
    }
}
