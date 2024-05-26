package com.example.book_airplanetacket;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class BookingDetailsActivity extends AppCompatActivity {

    private TextView tvTicketInfo;
    private DatabaseHelper dbHelper;
    private int ticketId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        tvTicketInfo = findViewById(R.id.tvTicketInfo);

        // 获取从上一个界面传递过来的机票 ID 和乘客信息
        ticketId = getIntent().getIntExtra("selectedTicketId", -1);
        String passengerName = getIntent().getStringExtra("passengerName");
        String passengerPhoneNumber = getIntent().getStringExtra("passengerPhoneNumber");
        String passengerIdCard = getIntent().getStringExtra("passengerIdCard");

        // 新建数据库对象
        dbHelper = new DatabaseHelper(this);

        // 获取从数据库查询到的机票信息
        String ticketInfo = getTicketInfo(ticketId);

        // 拼接乘客信息
        String passengerInfo = "\n乘客姓名: " + passengerName +
                "\n电话号码: " + passengerPhoneNumber +
                "\n身份证号: " + passengerIdCard;

        // 在 TextView 中显示机票信息和乘客信息
        tvTicketInfo.setText(ticketInfo + passengerInfo);
    }

    // 根据机票ID从数据库中查询机票信息
    private String getTicketInfo(int ticketId) {
        String ticketInfo = "";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                DatabaseHelper.COLUMN_FLIGHT_NUMBER,
                DatabaseHelper.COLUMN_AIRLINE,
                DatabaseHelper.COLUMN_DEPARTURE_LOCATION,
                DatabaseHelper.COLUMN_DESTINATION,
                DatabaseHelper.COLUMN_DEPARTURE_TIME,
                DatabaseHelper.COLUMN_ARRIVAL_TIME,
                DatabaseHelper.COLUMN_PRICE,
                DatabaseHelper.COLUMN_SEAT_TYPE,
                DatabaseHelper.COLUMN_SEAT_NUMBER,
                DatabaseHelper.COLUMN_REMAINING_TICKETS
        };
        String selection = DatabaseHelper.COLUMN_TICKET_ID + " = ?";
        String[] selectionArgs = {String.valueOf(ticketId)};

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_TICKETS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            String flightNumber = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FLIGHT_NUMBER));
            String airline = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AIRLINE));
            String departureLocation = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DEPARTURE_LOCATION));
            String destination = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DESTINATION));
            String departureTime = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DEPARTURE_TIME));
            String arrivalTime = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ARRIVAL_TIME));
            double price = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRICE));
            String seatType = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SEAT_TYPE));
            String seatNumber = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SEAT_NUMBER));
            int remainingTickets = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REMAINING_TICKETS));

            ticketInfo = "航班号: " + flightNumber +
                    "\n航空公司: " + airline +
                    "\n出发地: " + departureLocation +
                    "\n目的地: " + destination +
                    "\n出发时间: " + departureTime +
                    "\n到达时间: " + arrivalTime +
                    "\n价格: ¥" + price +
                    "\n座位类型: " + seatType +
                    "\n座位号: " + seatNumber ;
//                    +
//                    "\n剩余票数: " + remainingTickets;

            cursor.close();
        }

        return ticketInfo;
    }
}
