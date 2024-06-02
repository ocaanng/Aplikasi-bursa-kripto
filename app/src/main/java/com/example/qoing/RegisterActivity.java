package com.example.qoing;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.qoing.R;

public class RegisterActivity extends AppCompatActivity {
    private EditText editTextUsername, editTextPassword;
    private TextView back;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextUsername = findViewById(R.id.edit_text_username);
        editTextPassword = findViewById(R.id.edit_text_password);
        Button bttnreg = findViewById(R.id.bttnreg);

        back = findViewById(R.id.back);

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        bttnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (username.length() > 15 && password.length() > 8) {
            Toast.makeText(this, "Username max 15 huruf & Password max 8 huruf", Toast.LENGTH_SHORT).show();
            return;
        }

        if (username.length() > 15) {
            Toast.makeText(this, "Username tidak boleh melebihi 15 karakter", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() > 8) {
            Toast.makeText(this, "Password tidak boleh melebihi 8 karakter", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.putFloat("total_uang", 1000.00f);
        editor.apply();

        Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
        finish();
    }

}
