package com.example.qoing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.qoing.R;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextUsername, editTextPassword;
    private ProgressBar progressBar;
    private Button bttnLogin;
    private TextView reg, logo, login, desc, tvreg;
    private SharedPreferences sharedPreferences;
    private Handler backgroundHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.edit_text_username);
        editTextPassword = findViewById(R.id.edit_text_password);
        bttnLogin = findViewById(R.id.bttn_login);
        reg = findViewById(R.id.reg);
        logo = findViewById(R.id.judul);
        login = findViewById(R.id.tv_login);
        desc = findViewById(R.id.tv_desc);
        tvreg = findViewById(R.id.tv_reg);
        progressBar = findViewById(R.id.progress_bar);

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        checkLoginStatus();

        // Membuat dan memulai HandlerThread
        HandlerThread handlerThread = new HandlerThread("BackgroundThread");
        handlerThread.start();
        backgroundHandler = new Handler(handlerThread.getLooper());

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        bttnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        String savedUsername = sharedPreferences.getString("username", "");
        String savedPassword = sharedPreferences.getString("password", "");

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (username.equals(savedUsername) && password.equals(savedPassword)) {
            progressBar.setVisibility(View.VISIBLE);

            editTextUsername.setVisibility(View.GONE);
            bttnLogin.setVisibility(View.GONE);
            logo.setVisibility(View.GONE);
            login.setVisibility(View.GONE);
            desc.setVisibility(View.GONE);
            tvreg.setVisibility(View.GONE);
            reg.setVisibility(View.GONE);
            editTextPassword.setVisibility(View.GONE);

            afterlogin(true);

            // Menjalankan kode di background thread dengan delay
            backgroundHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Kembali ke UI thread untuk memperbarui UI
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }, 3000); // 3 seconds delay
        } else {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkLoginStatus() {
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        if (isLoggedIn) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void afterlogin(boolean isLoggedIn) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", isLoggedIn);
        editor.apply();
    }
}

