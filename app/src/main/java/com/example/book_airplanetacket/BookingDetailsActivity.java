package com.example.book_airplanetacket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.book_airplanetacket.R;

public class BookingDetailsActivity extends AppCompatActivity {

    private TextView tvTicketInfo;
    private Button btnOrderTicket;

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

        btnOrderTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 在这里实现订购机票的逻辑
                orderTicket();
            }
        });
    }

    private void orderTicket() {
        // 实现订购机票的逻辑
        // 这里只是简单地显示一个 Toast
        Toast.makeText(this, "Ticket ordered successfully!", Toast.LENGTH_SHORT).show();
    }
}