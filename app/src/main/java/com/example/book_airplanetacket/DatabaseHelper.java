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

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建机票信息表
        db.execSQL(SQL_CREATE_TICKETS_TABLE);

        // 创建乘客信息表
        db.execSQL(SQL_CREATE_PASSENGERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 升级数据库时的操作，这里简单处理，直接删除表再重新创建
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TICKETS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PASSENGERS);
        onCreate(db);
    }
}
