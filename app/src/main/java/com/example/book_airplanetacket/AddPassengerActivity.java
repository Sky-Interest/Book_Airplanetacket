package com.example.book_airplanetacket;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddPassengerActivity extends AppCompatActivity {

    private EditText etPassengerName, etPhoneNumber, etIdCard;
    private Button btnSavePassenger;
    private DatabaseHelper databaseHelper;
    private int ticketId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_passenger);

        etPassengerName = findViewById(R.id.etPassengerName);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etIdCard = findViewById(R.id.etIdCard);
        btnSavePassenger = findViewById(R.id.btnSavePassenger);
        databaseHelper = new DatabaseHelper(this);

        // 获取从上一个界面传递过来的机票ID
        ticketId = getIntent().getIntExtra("selectedTicketId", -1);

        btnSavePassenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePassenger();
            }
        });
    }

    private void savePassenger() {
        String name = etPassengerName.getText().toString().trim();
        String phoneNumber = etPhoneNumber.getText().toString().trim();
        String idCard = etIdCard.getText().toString().trim();

        if (name.isEmpty() || phoneNumber.isEmpty() || idCard.isEmpty()) {
            // 如果有任何一个字段为空，则显示提示消息
            Toast.makeText(this, "请填写所有字段", Toast.LENGTH_SHORT).show();
            return;
        }

        // 将乘客信息添加到数据库
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_PASSENGER_NAME, name);
        values.put(DatabaseHelper.COLUMN_PHONE_NUMBER, phoneNumber);
        values.put(DatabaseHelper.COLUMN_ID_CARD, idCard);

        long newRowId = db.insert(DatabaseHelper.TABLE_PASSENGERS, null, values);
        if (newRowId != -1) {
            Toast.makeText(this, "乘客添加成功", Toast.LENGTH_SHORT).show();
            // 创建一个 Intent 以将乘客信息返回给 TicketBookingActivity
            Intent intent = new Intent();
            intent.putExtra("selectedTicketId", ticketId);
            intent.putExtra("passengerName", name);
            intent.putExtra("passengerPhoneNumber", phoneNumber);
            intent.putExtra("passengerIdCard", idCard);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            Toast.makeText(this, "添加乘客时出错", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
}
