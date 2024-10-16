package com.example.groundonline;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AdminLoginActivity extends AppCompatActivity {

    private EditText adminEmailInput, adminPasswordInput;
    private Button adminLoginButton;
    private TextView adminSignUpLink;
    private AdminRepository adminRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        // Initialize the UI components
        adminEmailInput = findViewById(R.id.adminEmailInput);
        adminPasswordInput = findViewById(R.id.adminPasswordInput);
        adminLoginButton = findViewById(R.id.adminLoginButton);
        adminSignUpLink = findViewById(R.id.adminSignUpLink);

        // Initialize the repository
        adminRepository = new AdminRepository(this);
        adminRepository.open();

        // Set login button click listener
        adminLoginButton.setOnClickListener(v -> {
            String email = adminEmailInput.getText().toString().trim();
            String password = adminPasswordInput.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(AdminLoginActivity.this, "Email and password are required", Toast.LENGTH_SHORT).show();
                return;
            }

            int loginResult = adminRepository.validateAdmin(email, password);
            if (loginResult == AdminRepository.LOGIN_SUCCESS) {
                Toast.makeText(AdminLoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AdminLoginActivity.this, AdminDashboardActivity.class);
                startActivity(intent);
                finish();
            } else if (loginResult == AdminRepository.LOGIN_INVALID_EMAIL) {
                Toast.makeText(AdminLoginActivity.this, "Invalid email", Toast.LENGTH_SHORT).show();
            } else if (loginResult == AdminRepository.LOGIN_INVALID_PASSWORD) {
                Toast.makeText(AdminLoginActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AdminLoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
            }
        });

        // Set sign-up link listener
        adminSignUpLink.setOnClickListener(v -> {
            Intent intent = new Intent(AdminLoginActivity.this, AdminSignUpActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adminRepository.close();
    }
}
