package com.example.book_airplanetacket;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddPassengerActivity extends AppCompatActivity {

    private EditText etPassengerName, etPhoneNumber;
    private Button btnSavePassenger;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_passenger);

        etPassengerName = findViewById(R.id.etPassengerName);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        btnSavePassenger = findViewById(R.id.btnSavePassenger);
        databaseHelper = new DatabaseHelper(this);

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

        if (name.isEmpty() || phoneNumber.isEmpty()) {
            Toast.makeText(this, "请填写所有字段", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_PASSENGER_NAME, name);
        values.put(DatabaseHelper.COLUMN_PHONE_NUMBER, phoneNumber);

        long newRowId = db.insert(DatabaseHelper.TABLE_PASSENGERS, null, values);
        if (newRowId != -1) {
            Toast.makeText(this, "乘客信息保存成功", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "乘客信息保存失败", Toast.LENGTH_SHORT).show();
        }
    }
}
