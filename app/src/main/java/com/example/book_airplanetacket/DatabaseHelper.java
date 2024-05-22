package com.example.book_airplanetacket;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // 表名和字段名
    public static final String TABLE_TICKETS = "tickets";
    public static final String COLUMN_TICKET_ID = "id";
    public static final String COLUMN_FLIGHT_NUMBER = "flight_number";
    public static final String COLUMN_AIRLINE = "airline";
    public static final String COLUMN_DEPARTURE_LOCATION = "departure_location";
    public static final String COLUMN_DESTINATION = "destination";
    public static final String COLUMN_DEPARTURE_TIME = "departure_time";
    public static final String COLUMN_ARRIVAL_TIME = "arrival_time";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_SEAT_TYPE = "seat_type";
    public static final String COLUMN_SEAT_NUMBER = "seat_number";
    public static final String COLUMN_REMAINING_TICKETS = "remaining_tickets";

    // 乘客表相关字段
    public static final String TABLE_PASSENGERS = "passengers";
    public static final String COLUMN_PASSENGER_ID = "id";
    public static final String COLUMN_PASSENGER_NAME = "name";
    public static final String COLUMN_PHONE_NUMBER = "phone_number";

    // 用户表相关字段
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";

    // 机票订单表相关字段
    public static final String TABLE_TICKET_ORDERS = "ticket_orders";
    public static final String COLUMN_ORDER_ID = "order_id";
    public static final String COLUMN_ORDER_FLIGHT_NUMBER = "flight_number";
    public static final String COLUMN_ORDER_AIRLINE = "airline";
    public static final String COLUMN_ORDER_DEPARTURE_LOCATION = "departure_location";
    public static final String COLUMN_ORDER_DESTINATION = "destination";
    public static final String COLUMN_ORDER_DEPARTURE_TIME = "departure_time";
    public static final String COLUMN_ORDER_ARRIVAL_TIME = "arrival_time";
    public static final String COLUMN_ORDER_PRICE = "price";
    public static final String COLUMN_ORDER_SEAT_TYPE = "seat_type";
    public static final String COLUMN_ORDER_SEAT_NUMBER = "seat_number";
    public static final String COLUMN_ORDER_PASSENGER_ID = "passenger_id";
    public static final String COLUMN_ORDER_TICKET_COUNT = "ticket_count"; // 新增字段：购买的票数

    // 数据库名称和版本
    private static final String DATABASE_NAME = "air_ticket_booking.db";
    private static final int DATABASE_VERSION = 1;

    // 创建表的 SQL 语句
    private static final String SQL_CREATE_TICKETS_TABLE = "CREATE TABLE " +
            TABLE_TICKETS + "(" +
            COLUMN_TICKET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_FLIGHT_NUMBER + " TEXT, " +
            COLUMN_AIRLINE + " TEXT, " +
            COLUMN_DEPARTURE_LOCATION + " TEXT, " +
            COLUMN_DESTINATION + " TEXT, " +
            COLUMN_DEPARTURE_TIME + " TEXT, " +
            COLUMN_ARRIVAL_TIME + " TEXT, " +
            COLUMN_PRICE + " TEXT, " +  // 使用 TEXT 类型存储 BigDecimal 值
            COLUMN_SEAT_TYPE + " TEXT, " +
            COLUMN_SEAT_NUMBER + " TEXT, " +
            COLUMN_REMAINING_TICKETS + " INTEGER" +
            ")";

    private static final String SQL_CREATE_PASSENGERS_TABLE = "CREATE TABLE " +
            TABLE_PASSENGERS + "(" +
            COLUMN_PASSENGER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_PASSENGER_NAME + " TEXT, " +
            COLUMN_PHONE_NUMBER + " TEXT" +
            ")";

    private static final String SQL_CREATE_USERS_TABLE = "CREATE TABLE " +
            TABLE_USERS + "(" +
            COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_USERNAME + " TEXT UNIQUE, " +
            COLUMN_PASSWORD + " TEXT" +
            ")";

    private static final String SQL_CREATE_TICKET_ORDERS_TABLE = "CREATE TABLE " +
            TABLE_TICKET_ORDERS + "(" +
            COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_ORDER_FLIGHT_NUMBER + " TEXT, " +
            COLUMN_ORDER_AIRLINE + " TEXT, " +
            COLUMN_ORDER_DEPARTURE_LOCATION + " TEXT, " +
            COLUMN_ORDER_DESTINATION + " TEXT, " +
            COLUMN_ORDER_DEPARTURE_TIME + " TEXT, " +
            COLUMN_ORDER_ARRIVAL_TIME + " TEXT, " +
            COLUMN_ORDER_PRICE + " TEXT, " +
            COLUMN_ORDER_SEAT_TYPE + " TEXT, " +
            COLUMN_ORDER_SEAT_NUMBER + " TEXT, " +
            COLUMN_ORDER_PASSENGER_ID + " INTEGER, " +
            COLUMN_ORDER_TICKET_COUNT + " INTEGER, " +  // 新增字段：购买的票数
            "FOREIGN KEY(" + COLUMN_ORDER_PASSENGER_ID + ") REFERENCES " + TABLE_PASSENGERS + "(" + COLUMN_PASSENGER_ID + ")" +
            ")";

    // 创建触发器的 SQL 语句
    private static final String SQL_CREATE_TRIGGER = "CREATE TRIGGER reduce_ticket_count AFTER INSERT ON " +
            TABLE_TICKET_ORDERS + " BEGIN " +
            "UPDATE " + TABLE_TICKETS + " SET " + COLUMN_REMAINING_TICKETS + " = " + COLUMN_REMAINING_TICKETS + " - NEW." + COLUMN_ORDER_TICKET_COUNT + " " +
            "WHERE " + COLUMN_FLIGHT_NUMBER + " = NEW." + COLUMN_ORDER_FLIGHT_NUMBER + ";" +
            "END;";

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

        // 创建机票订单表
        db.execSQL(SQL_CREATE_TICKET_ORDERS_TABLE);

        // 创建触发器
        db.execSQL(SQL_CREATE_TRIGGER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 升级数据库时的操作，这里简单处理，直接删除表再重新创建
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TICKETS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PASSENGERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TICKET_ORDERS);
        db.execSQL("DROP TRIGGER IF EXISTS reduce_ticket_count");
        onCreate(db);
    }
}
