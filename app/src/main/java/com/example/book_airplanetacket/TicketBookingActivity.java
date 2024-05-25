package com.example.book_airplanetacket;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;

public class TicketBookingActivity extends AppCompatActivity {

    private TextView tvUsername;
    private Button btnSelectDate;
    private Button btnAddPassenger;
    private ListView listViewTickets;
    private ArrayAdapter<String> ticketAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_booking);

        tvUsername = findViewById(R.id.tvUsername);
        btnSelectDate = findViewById(R.id.btnSelectDate);
        btnAddPassenger = findViewById(R.id.btnAddPassenger);
        listViewTickets = findViewById(R.id.listViewTickets);
        databaseHelper = new DatabaseHelper(this);

        // 显示当前登录的用户名
        User currentUser = User.getCurrentUser();
        if (currentUser != null) {
            tvUsername.setText("Welcome, " + currentUser.getUsername());
        }

        // 从数据库获取机票数据
        loadTicketsFromDatabase();

        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 实现选择日期的逻辑
                showDatePickerDialog();
            }
        });

        btnAddPassenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TicketBookingActivity.this, AddPassengerActivity.class);
                startActivity(intent);
            }
        });
    }

    // 从数据库加载机票数据
    private void loadTicketsFromDatabase() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] projection = {
                DatabaseHelper.COLUMN_DEPARTURE_LOCATION,
                DatabaseHelper.COLUMN_DESTINATION,
                DatabaseHelper.COLUMN_PRICE,
                DatabaseHelper.COLUMN_REMAINING_TICKETS
        };

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_TICKETS,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        ArrayList<String> tickets = new ArrayList<>();
        while (cursor.moveToNext()) {
            String departure = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DEPARTURE_LOCATION));
            String destination = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DESTINATION));
            double price = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRICE));
            int remainingTickets = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REMAINING_TICKETS));
            tickets.add("出发地: " + departure + ", 目的地: " + destination + ", 价格: ¥" + price + ", 余票: " + remainingTickets);
        }
        cursor.close();

        // 使用默认的布局文件 android.R.layout.simple_list_item_1 创建 ArrayAdapter
        ticketAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tickets);

        // 将 ArrayAdapter 设置给 ListView
        listViewTickets.setAdapter(ticketAdapter);
    }

    // 获取日期的方法
    private void showDatePickerDialog() {
        // 获取当前日期
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // 创建 DatePickerDialog 对象
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year1, monthOfYear, dayOfMonth1) -> {
                    // 处理选择的日期，这里我们只简单地显示一个 Toast
                    String selectedDate = year1 + "-" + (monthOfYear + 1) + "-" + dayOfMonth1;
                    Toast.makeText(TicketBookingActivity.this, "Selected date: " + selectedDate, Toast.LENGTH_SHORT).show();
                },
                year, month, dayOfMonth);

        // 显示 DatePickerDialog
        datePickerDialog.show();
    }
}
