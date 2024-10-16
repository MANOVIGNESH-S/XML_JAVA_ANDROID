package com.example.groundonline;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AdminSignUpActivity extends AppCompatActivity {

    private EditText adminNameInput, adminEmailInput, adminMobileInput, adminPasswordInput;
    private Button adminRegisterButton;
    private AdminRepository adminRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_signup);

        // Initialize UI components
        adminNameInput = findViewById(R.id.adminNameInput);
        adminEmailInput = findViewById(R.id.adminEmailInput);
        adminMobileInput = findViewById(R.id.adminMobileInput);
        adminPasswordInput = findViewById(R.id.adminPasswordInput);
        adminRegisterButton = findViewById(R.id.adminRegisterButton);

        // Initialize the repository
        adminRepository = new AdminRepository(this);
        adminRepository.open();

        // Set register button click listener
        adminRegisterButton.setOnClickListener(v -> {
            String name = adminNameInput.getText().toString().trim();
            String email = adminEmailInput.getText().toString().trim();
            String mobile = adminMobileInput.getText().toString().trim();
            String password = adminPasswordInput.getText().toString().trim();

            // Validate fields
            if (name.isEmpty() || email.isEmpty() || mobile.isEmpty() || password.isEmpty()) {
                Toast.makeText(AdminSignUpActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else if (!isValidEmail(email)) {
                Toast.makeText(AdminSignUpActivity.this, "Invalid email format", Toast.LENGTH_SHORT).show();
            } else if (!isValidMobile(mobile)) {
                Toast.makeText(AdminSignUpActivity.this, "Invalid mobile number", Toast.LENGTH_SHORT).show();
            } else {
                long result = adminRepository.insertAdmin(name, email, mobile, password);
                if (result != -1) {
                    Toast.makeText(AdminSignUpActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AdminSignUpActivity.this, AdminDashboardActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AdminSignUpActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Method to validate email format
    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Method to validate mobile number (10 digits or more)
    private boolean isValidMobile(String mobile) {
        return mobile.matches("\\d{10,}");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adminRepository.close();
    }
}
