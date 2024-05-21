package com.example.book_airplanetacket;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // 表名和字段名
    public static final String TABLE_TICKETS = "tickets";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TICKET_NAME = "name";
    public static final String COLUMN_TICKET_PRICE = "price";
    public static final String TABLE_PASSENGERS = "passengers";
    public static final String COLUMN_PASSSENGER_NAME = "name";
    // 添加用户信息表相关内容
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    // 数据库名称和版本
    private static final String DATABASE_NAME = "air_ticket_booking.db";
    private static final int DATABASE_VERSION = 1;
    // 创建表的 SQL 语句
    private static final String SQL_CREATE_TICKETS_TABLE = "CREATE TABLE " +
            TABLE_TICKETS + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_TICKET_NAME + " TEXT, " +
            COLUMN_TICKET_PRICE + " REAL" +
            ")";
    // 创建表的 SQL 语句
    private static final String SQL_CREATE_PASSENGERS_TABLE = "CREATE TABLE " +
            TABLE_PASSENGERS + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_PASSSENGER_NAME + " TEXT" +
            ")";
    // 创建用户信息表的 SQL 语句
    private static final String SQL_CREATE_USERS_TABLE = "CREATE TABLE " +
            TABLE_USERS + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_USERNAME + " TEXT UNIQUE, " +  // 唯一性约束，确保用户名唯一
            COLUMN_PASSWORD + " TEXT" +
            ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建机票信息表
        db.execSQL(SQL_CREATE_TICKETS_TABLE);

        // 创建乘客信息表
        db.execSQL(SQL_CREATE_PASSENGERS_TABLE);

        // 创建用户信息表
        db.execSQL(SQL_CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 升级数据库时的操作，这里简单处理，直接删除表再重新创建
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TICKETS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PASSENGERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }
}
