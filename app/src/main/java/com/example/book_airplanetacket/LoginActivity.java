package com.example.book_airplanetacket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.book_airplanetacket.R;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView tvRegister;

    // 静态用户名和密码
    private static final String USERNAME = "user";
    private static final String PASSWORD = "pass";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 这里添加登录逻辑

                // 获取输入的用户名和密码
                String inputUsername = etUsername.getText().toString().trim();
                String inputPassword = etPassword.getText().toString().trim();

                // 验证用户名和密码是否匹配
                if (inputUsername.equals(USERNAME) && inputPassword.equals(PASSWORD)) {
                    // 登录成功，跳转到订票界面
                    startActivity(new Intent(LoginActivity.this, TicketBookingActivity.class));
                    finish(); // 关闭当前页面
                } else {
                    // 登录失败，显示错误消息
                    Toast.makeText(LoginActivity.this, "用户名或密码错误！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });
    }
}