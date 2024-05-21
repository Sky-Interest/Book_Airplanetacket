package com.example.book_airplanetacket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.book_airplanetacket.R;

public class OrderConfirmationActivity extends AppCompatActivity {

    private TextView tvFlightInfo, tvPassengerInfo, tvTotalAmount;
    private Button btnAddPassenger, btnCalculateTotal, btnCheckout;
    private CheckBox chkInsurance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

        tvFlightInfo = findViewById(R.id.tvFlightInfo);
        tvPassengerInfo = findViewById(R.id.tvPassengerInfo);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        btnAddPassenger = findViewById(R.id.btnAddPassenger);
        btnCalculateTotal = findViewById(R.id.btnCalculateTotal);
        btnCheckout = findViewById(R.id.btnCheckout);
        chkInsurance = findViewById(R.id.chkInsurance);

        // 获取从上一个界面传递过来的航班信息和乘客信息
        String flightInfo = getIntent().getStringExtra("flight_info");
        String passengerInfo = getIntent().getStringExtra("passenger_info");

        // 在 TextView 中显示航班信息和乘客信息
        tvFlightInfo.setText(flightInfo);
        tvPassengerInfo.setText(passengerInfo);

        btnAddPassenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 在这里实现添加乘客的逻辑
                addPassenger();
            }
        });

        btnCalculateTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 在这里计算总金额的逻辑
                calculateTotal();
            }
        });

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 在这里实现结算的逻辑
                checkout();
            }
        });
    }

    private void addPassenger() {
        // 在这里实现添加乘客的逻辑
        // 这里只是简单地显示一个 Toast
        Toast.makeText(this, "Add Passenger", Toast.LENGTH_SHORT).show();
    }

    private void calculateTotal() {
        // 在这里计算总金额的逻辑
        // 这里只是简单地显示一个 Toast
        // Toast.makeText(this, "Calculate Total", Toast.LENGTH_SHORT).show();

        
        // 这里只是一个示例，假设每个机票价格为 100 元，航班险价格为 20 元
        double ticketPrice = 100; // 假设每个机票价格为 100 元
        double insurancePrice = 0;

        if (chkInsurance.isChecked()) {
            insurancePrice = 20; // 如果选择了航班险，则价格为 20 元
        }

        // 计算总金额
        double totalAmount = ticketPrice + insurancePrice;

        // 将总金额显示在 TextView 中
        tvTotalAmount.setText("Total Amount: " + totalAmount + "元");
    }

    private void checkout() {
        // 在这里实现结算的逻辑
        // 这里只是简单地显示一个 Toast
        Toast.makeText(this, "Checkout", Toast.LENGTH_SHORT).show();
    }
}