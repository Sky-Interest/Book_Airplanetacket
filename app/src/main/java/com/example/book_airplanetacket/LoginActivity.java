package com.example.book_airplanetacket;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.book_airplanetacket.User;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView tvRegister;

    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);

        btnLogin.setOnClickListener(v -> {
            Log.d(TAG, "Login button clicked");
            loginUser();
        });

        tvRegister.setOnClickListener(v -> {
            Log.d(TAG, "Register TextView clicked");
            startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
        });
    }

    private void loginUser() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        User authenticatedUser = User.authenticateUser(this, username, password);

        if (authenticatedUser != null) {
            User.setCurrentUser(authenticatedUser);
            Log.d(TAG, "Login successful, starting TicketBookingActivity");
            startActivity(new Intent(LoginActivity.this, TicketBookingActivity.class));
            finish();
        } else {
            Toast.makeText(LoginActivity.this, "用户名或密码错误！", Toast.LENGTH_SHORT).show();
        }
    }
}
