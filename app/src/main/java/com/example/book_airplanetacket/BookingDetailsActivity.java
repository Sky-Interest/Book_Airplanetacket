package com.example.book_airplanetacket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class BookingDetailsActivity extends AppCompatActivity {

    private TextView tvTicketInfo;
    private TextView tvTotalPrice;
    private CheckBox cbInsurance;
    private RadioGroup rgInsurance;
    private RadioButton rbBasicInsurance, rbPremiumInsurance;
    private Button btnOrderTicket;
    private DatabaseHelper dbHelper;
    private int ticketId;
    private double ticketPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        tvTicketInfo = findViewById(R.id.tvTicketInfo);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        cbInsurance = findViewById(R.id.cbInsurance);
        rgInsurance = findViewById(R.id.rgInsurance);
        rbBasicInsurance = findViewById(R.id.rbBasicInsurance);
        rbPremiumInsurance = findViewById(R.id.rbPremiumInsurance);
        btnOrderTicket = findViewById(R.id.btnOrderTicket);

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

        // 设置默认总价
        updateTotalPrice();

        // 设置保险复选框改变的监听器
        cbInsurance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                rgInsurance.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                rbBasicInsurance.setEnabled(isChecked);
                rbPremiumInsurance.setEnabled(isChecked);
                updateTotalPrice();
            }
        });

        // 设置保险单选按钮改变的监听器
        rgInsurance.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                updateTotalPrice();
            }
        });

        // 设置前往支付按钮的点击事件
        btnOrderTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookingDetailsActivity.this, PaymentActivity.class);
                intent.putExtra("ticketId", ticketId);
                intent.putExtra("passengerName", passengerName);
                intent.putExtra("passengerPhoneNumber", passengerPhoneNumber);
                intent.putExtra("passengerIdCard", passengerIdCard);
                intent.putExtra("insuranceType", getSelectedInsuranceType());
                intent.putExtra("totalPrice", getTotalPrice());
                startActivity(intent);
            }
        });
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
            ticketPrice = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRICE));
            String seatType = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SEAT_TYPE));
            String seatNumber = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SEAT_NUMBER));

            ticketInfo = "航班号: " + flightNumber +
                    "\n航空公司: " + airline +
                    "\n出发地: " + departureLocation +
                    "\n目的地: " + destination +
                    "\n出发时间: " + departureTime +
                    "\n到达时间: " + arrivalTime +
                    "\n价格: ¥" + ticketPrice +
                    "\n座位类型: " + seatType +
                    "\n座位号: " + seatNumber;

            cursor.close();
        }

        return ticketInfo;
    }

    // 更新总价
    private void updateTotalPrice() {
        double insurancePrice = 0;
        if (cbInsurance.isChecked()) {
            insurancePrice = rbBasicInsurance.isChecked() ? 100 : 200;
        }
        double totalPrice = ticketPrice + insurancePrice;
        tvTotalPrice.setText("总价: ¥" + totalPrice);
    }

    // 获取选中的保险类型
    private int getSelectedInsuranceType() {
        if (!cbInsurance.isChecked()) {
            return 0;
        }
        return rbBasicInsurance.isChecked() ? 1 : 2;
    }

    // 获取总价
    private double getTotalPrice() {
        double insurancePrice = 0;
        if (cbInsurance.isChecked()) {
            insurancePrice = rbBasicInsurance.isChecked() ? 100 : 200;
        }
        return ticketPrice + insurancePrice;
    }
}
