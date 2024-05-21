package com.example.book_airplanetacket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.book_airplanetacket.R;

public class PaymentActivity extends AppCompatActivity {

    private RadioGroup radioGroupPayment;
    private Button btnProceedPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        radioGroupPayment = findViewById(R.id.radioGroupPayment);
        btnProceedPayment = findViewById(R.id.btnProceedPayment);

        btnProceedPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceedPayment();
            }
        });
    }

    private void proceedPayment() {
        // 获取选中的支付方式
        int selectedId = radioGroupPayment.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(selectedId);

        // 检查是否选择了支付方式
        if (selectedId == -1) {
            Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show();
        } else {
            // 获取选中的支付方式的文本
            String paymentMethod = radioButton.getText().toString();
            // 在这里执行支付操作
            // 这里只是一个示例，假设支付成功后显示一个 Toast 消息
            Toast.makeText(this, "Payment successful! Method: " + paymentMethod, Toast.LENGTH_SHORT).show();
        }
    }
}