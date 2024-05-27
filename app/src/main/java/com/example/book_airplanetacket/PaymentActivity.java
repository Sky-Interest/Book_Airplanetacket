package com.example.book_airplanetacket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class PaymentActivity extends AppCompatActivity {

    private RadioGroup radioGroupPayment;
    private Button btnProceedPayment;
    private DatabaseHelper dbHelper;
    private int ticketId;
    private String flightNumber;
    private String airline;
    private String departureLocation;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private double price;
    private String seatType;
    private String seatNumber;
    private String passengerName;
    private String passengerPhoneNumber;
    private String passengerIdCard;
    private int insuranceType;
    private double totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        radioGroupPayment = findViewById(R.id.radioGroupPayment);
        btnProceedPayment = findViewById(R.id.btnProceedPayment);

        // 初始化数据库帮助类
        dbHelper = new DatabaseHelper(this);

        // 获取从上一个界面传递过来的机票 ID 和其他信息
        ticketId = getIntent().getIntExtra("ticketId", -1);
        passengerName = getIntent().getStringExtra("passengerName");
        passengerPhoneNumber = getIntent().getStringExtra("passengerPhoneNumber");
        passengerIdCard = getIntent().getStringExtra("passengerIdCard");
        insuranceType = getIntent().getIntExtra("insuranceType", 0);
        totalPrice = getIntent().getDoubleExtra("totalPrice", 0.0);

        // 获取机票信息
        getTicketInfo(ticketId);

        btnProceedPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceedPayment();
            }
        });
    }

    private void getTicketInfo(int ticketId) {
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
                DatabaseHelper.COLUMN_SEAT_NUMBER
        };

        String selection = DatabaseHelper.COLUMN_TICKET_ID + " = ?";
        String[] selectionArgs = { String.valueOf(ticketId) };

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
            flightNumber = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FLIGHT_NUMBER));
            airline = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AIRLINE));
            departureLocation = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DEPARTURE_LOCATION));
            destination = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DESTINATION));
            departureTime = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DEPARTURE_TIME));
            arrivalTime = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ARRIVAL_TIME));
            price = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRICE));
            seatType = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SEAT_TYPE));
            seatNumber = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SEAT_NUMBER));

            cursor.close();
        } else {
            if (cursor != null) {
                cursor.close();
            }
            Toast.makeText(this, "未找到机票信息", Toast.LENGTH_SHORT).show();
            finish(); // 关闭活动
        }
    }

    private void proceedPayment() {
        // 获取选中的支付方式
        int selectedId = radioGroupPayment.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(selectedId);

        // 检查是否选择了支付方式
        if (selectedId == -1) {
            Toast.makeText(this, "请选择支付方式！", Toast.LENGTH_SHORT).show();
        } else {
            // 获取选中的支付方式的文本
            String paymentMethod = radioButton.getText().toString();

            // 将订单信息插入数据库
            insertOrderIntoDatabase(flightNumber, airline, departureLocation, destination, departureTime, arrivalTime, price, seatType, seatNumber, passengerName, passengerPhoneNumber, passengerIdCard, insuranceType, totalPrice, paymentMethod);

            // 显示支付成功消息并跳转回 TicketBookingActivity
            Toast.makeText(this, "支付成功！感谢使用 " + paymentMethod + " 进行支付！", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(PaymentActivity.this, TicketBookingActivity.class);
            startActivity(intent);
            finish(); // 结束当前活动
        }
    }

    private void insertOrderIntoDatabase(String flightNumber, String airline, String departureLocation, String destination, String departureTime, String arrivalTime, double price, String seatType, String seatNumber, String passengerName, String passengerPhoneNumber, String passengerIdCard, int insuranceType, double totalPrice, String paymentMethod) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ORDER_FLIGHT_NUMBER, flightNumber);
        values.put(DatabaseHelper.COLUMN_ORDER_AIRLINE, airline);
        values.put(DatabaseHelper.COLUMN_ORDER_DEPARTURE_LOCATION, departureLocation);
        values.put(DatabaseHelper.COLUMN_ORDER_DESTINATION, destination);
        values.put(DatabaseHelper.COLUMN_ORDER_DEPARTURE_TIME, departureTime);
        values.put(DatabaseHelper.COLUMN_ORDER_ARRIVAL_TIME, arrivalTime);
        values.put(DatabaseHelper.COLUMN_ORDER_PRICE, price);
        values.put(DatabaseHelper.COLUMN_ORDER_SEAT_TYPE, seatType);
        values.put(DatabaseHelper.COLUMN_ORDER_SEAT_NUMBER, seatNumber);
        values.put(DatabaseHelper.COLUMN_ORDER_PASSENGER_ID, passengerName); // 假设 passengerName 是唯一标识符
        values.put(DatabaseHelper.COLUMN_ORDER_TICKET_COUNT, 1);  // 假设每次购买一张票
        values.put(DatabaseHelper.COLUMN_ORDER_INSURANCE_TYPE, insuranceType);
        values.put(DatabaseHelper.COLUMN_ORDER_TOTAL_PRICE, totalPrice);
//        values.put(DatabaseHelper.COLUMN_PAYMENT_METHOD, paymentMethod);

        long newRowId = db.insert(DatabaseHelper.TABLE_TICKET_ORDERS, null, values);
        if (newRowId != -1) {
            Toast.makeText(this, "订单信息保存成功！", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "订单信息保存失败！", Toast.LENGTH_SHORT).show();
        }
    }
}
