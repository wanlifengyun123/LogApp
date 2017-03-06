package com.yajun.logapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yajun on 2016/10/17.
 *
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String DBNAME = "test.db";
    private static final int VERSION = 1;

    private static DBOpenHelper sInstance;

    private DBOpenHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    public static DBOpenHelper getInstance(Context context) {
        if (sInstance == null)
            sInstance = new DBOpenHelper(context.getApplicationContext());
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBCommon.UserColumns.CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        int currentVersion = oldVersion + 1;
        if(currentVersion > newVersion){
            return;
        }
        switch (currentVersion){
            case 2:
                db.execSQL("ALTER TABLE " + DBContentProvider.USER_TABLE_NAME + " ADD COLUMN " + "companyid" + " Integer default 0;");
            default:
                break;
        }

    }
}
