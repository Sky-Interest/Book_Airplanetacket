package com.example.book_airplanetacket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.book_airplanetacket.R;
import com.example.book_airplanetacket.DatabaseHelper;

public class BookingDetailsActivity extends AppCompatActivity {

    private TextView tvTicketInfo;
    private Button btnOrderTicket;
    private EditText etPassengerName;
    private Button btnAddPassenger;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        tvTicketInfo = findViewById(R.id.tvTicketInfo);
        btnOrderTicket = findViewById(R.id.btnOrderTicket);

        // 获取从上一个界面传递过来的机票信息
        String ticketInfo = getIntent().getStringExtra("ticket_info");
        // 在 TextView 中显示机票信息
        tvTicketInfo.setText(ticketInfo);

        //新建数据库对象
        dbHelper = new DatabaseHelper(this);

        btnOrderTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPassengerToDatabase();
            }
        });
    }

    private void addPassengerToDatabase() {
        String passengerName = etPassengerName.getText().toString().trim();

        // 检查乘客姓名是否为空
        if (passengerName.isEmpty()) {
            Toast.makeText(this, "Please enter passenger name", Toast.LENGTH_SHORT).show();
            return;
        }

        // 获取可写的数据库
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 创建一个 ContentValues 对象，用于存储数据
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_PASSENGER_NAME, passengerName);

        // 向数据库插入数据
        long newRowId = db.insert(DatabaseHelper.TABLE_PASSENGERS, null, values);

        // 检查是否插入成功
        if (newRowId == -1) {
            Toast.makeText(this, "Error inserting passenger", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Passenger added successfully", Toast.LENGTH_SHORT).show();
        }
    }
}