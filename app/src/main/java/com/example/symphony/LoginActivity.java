package com.example.symphony;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;

public class LoginActivity extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private DatabaseManager dbManager;  // Instance DatabaseManager
    private ImageButton btnTogglePassword, btnCreateAccount;
    private Button btnLogin;
    private boolean isPasswordVisible = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // param from widget
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);

        btnLogin = findViewById(R.id.btn_login);
        btnCreateAccount = findViewById(R.id.btn_create_account);
        btnTogglePassword = findViewById(R.id.btn_toggle_password);

        // DatabaseManager
        dbManager = new DatabaseManager(this);

        btnTogglePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility();
            }
        });

        // Event klik tombol login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        // Event klik tombol buat akun
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }


//    login user
    private void loginUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Masukkan Email dan Password!", Toast.LENGTH_SHORT).show();
        } else {
            boolean success = dbManager.loginUser(email, password);
            if (success) {
                Toast.makeText(this, "Login Berhasil!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, DashboardActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Email atau Password salah!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // visibility password
    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            btnTogglePassword.setImageResource(R.drawable.baseline_visibility_off_24);
        } else {
            etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            btnTogglePassword.setImageResource(R.drawable.baseline_visibility_24);
        }
        isPasswordVisible = !isPasswordVisible;
        etPassword.setSelection(etPassword.getText().length()); // Tetap fokus di akhir teks
    }
}
