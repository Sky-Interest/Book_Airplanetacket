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

    private static final int REQUEST_ADD_PASSENGER = 1; // 请求码

    private TextView tvUsername;
    private Button btnSelectDate;
    private ListView listViewTickets;
    private ArrayAdapter<String> ticketAdapter;
    private DatabaseHelper databaseHelper;
    private ArrayList<Ticket> tickets; // 存储机票信息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_booking);

        tvUsername = findViewById(R.id.tvUsername);
        btnSelectDate = findViewById(R.id.btnSelectDate);
        listViewTickets = findViewById(R.id.listViewTickets);
        databaseHelper = new DatabaseHelper(this);

        // 显示当前登录的用户名
        User currentUser = User.getCurrentUser();
        if (currentUser != null) {
            tvUsername.setText("欢迎, 用户" + currentUser.getUsername());
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

        listViewTickets.setOnItemClickListener((parent, view, position, id) -> {
            // 获取点击的机票
            Ticket selectedTicket = tickets.get(position);

            // 跳转到添加乘客页面
            Intent intent = new Intent(TicketBookingActivity.this, AddPassengerActivity.class);
            intent.putExtra("selectedTicketId", selectedTicket.getId());
            startActivityForResult(intent, REQUEST_ADD_PASSENGER);
        });
    }

    // 从数据库加载机票数据
    private void loadTicketsFromDatabase() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] projection = {
                DatabaseHelper.COLUMN_TICKET_ID,
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

        tickets = new ArrayList<>();
        ArrayList<String> ticketDescriptions = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TICKET_ID));
            String departure = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DEPARTURE_LOCATION));
            String destination = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DESTINATION));
            double price = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRICE));
            int remainingTickets = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REMAINING_TICKETS));

            Ticket ticket = new Ticket(id, "", "", departure, destination, "", "", price, "", "", -1, remainingTickets);
            tickets.add(ticket);

            ticketDescriptions.add("出发地: " + departure + ", 目的地: " + destination + ", 价格: ¥" + price + ", 余票: " + remainingTickets);
        }
        cursor.close();

        // 使用默认的布局文件 android.R.layout.simple_list_item_1 创建 ArrayAdapter
        ticketAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ticketDescriptions);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADD_PASSENGER && resultCode == RESULT_OK) {
            // 获取传递的机票ID
            int ticketId = data.getIntExtra("selectedTicketId", -1);

            // 跳转到机票详情页
            Intent intent = new Intent(TicketBookingActivity.this, BookingDetailsActivity.class);
            intent.putExtra("selectedTicketId", ticketId);

            // 获取乘客信息并传递给 BookingDetailsActivity
            intent.putExtra("passengerName", data.getStringExtra("passengerName"));
            intent.putExtra("passengerPhoneNumber", data.getStringExtra("passengerPhoneNumber"));
            intent.putExtra("passengerIdCard", data.getStringExtra("passengerIdCard"));

            startActivity(intent);
        }
    }
}
