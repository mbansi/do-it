package com.bansi.doit.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class MainDb extends SQLiteAssetHelper {

    public final static String DATABASE_NAME = "DoIt.db";
    public final static  int DATABASE_VERSION = 1;

    public MainDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
