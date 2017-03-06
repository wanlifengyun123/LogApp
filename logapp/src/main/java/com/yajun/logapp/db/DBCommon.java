package com.yajun.logapp.db;

import android.net.Uri;

/**
 * Created by yajun on 2016/10/17.
 *
 */
public class DBCommon {

    public static abstract interface BaseColumns {
        public static final String AUTHORITY = "cn.com.open.mooc";
        public static final String _ID = "_id";
    }

    public static abstract interface UserColumns extends DBCommon.BaseColumns
    {
        public static final Uri CONTENT_URI_USER = Uri.parse("content://com.yajun.logapp/" + DBContentProvider.USER_TABLE_NAME);
        public static final String CREATE_TABLE_USER = " CREATE TABLE IF NOT EXISTS " + DBContentProvider.USER_TABLE_NAME + " ( " + "_id" + " INTEGER PRIMARY KEY AUTOINCREMENT ," + "uid" + " INTEGER," + "age" + " INTEGER," + "nickname" + " VARCHAR," + "teacher" + " VARCHAR , " + "pic" + " VARCHAR , " + "sex" + " INTEGER)";
        public static final String NICKNAE = "nickname";
        public static final String AGE = "age";
        public static final String PIC = "pic";
        public static final String SEX = "sex";
        public static final String TEACHER = "teacher";
        public static final String UID = "uid";
        public static final String[] projects = { "uid", "nickname", "sex", "pic", "teacher" };
    }
}
