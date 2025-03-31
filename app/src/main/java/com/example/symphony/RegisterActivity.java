package com.example.symphony;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageButton;

public class RegisterActivity extends AppCompatActivity {
    private EditText etEmail, etPassword, etBirthday;
    private DatabaseManager dbManager;  // Instance DatabaseManager
    private ImageButton btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        // Inisialisasi EditText
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etBirthday = findViewById(R.id.et_birthday);
        Button btnRegister = findViewById(R.id.btn_create_account);
        btnBack = findViewById(R.id.btn_back);


        // Inisialisasi DatabaseManager
        dbManager = new DatabaseManager(this);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        // Event klik tombol back
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Kembali ke LoginActivity
            }
        });
    }

    private void registerUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String birthday = etBirthday.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty() || birthday.isEmpty()) {
            Toast.makeText(this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show();
        } else {
            boolean success = dbManager.registerUser(email, password, birthday);
            if (success) {
                Toast.makeText(this, "Registrasi Berhasil!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Registrasi Gagal! Email sudah digunakan.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
