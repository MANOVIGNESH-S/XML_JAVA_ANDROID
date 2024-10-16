package com.example.groundonline;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class UserSignUpActivity extends AppCompatActivity {

    private EditText userNameInput, userAddressInput, userEmailInput, userMobileInput, userPasswordInput;
    private Spinner userGenderSpinner;
    private Button userRegisterButton;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);

        // Initialize the UI components
        userNameInput = findViewById(R.id.userNameInput);
        userAddressInput = findViewById(R.id.userAddressInput);
        userGenderSpinner = findViewById(R.id.userGenderSpinner);
        userEmailInput = findViewById(R.id.userEmailInput);
        userMobileInput = findViewById(R.id.userMobileInput);
        userPasswordInput = findViewById(R.id.userPasswordInput);
        userRegisterButton = findViewById(R.id.userRegisterButton);

        // Initialize the UserRepository
        userRepository = new UserRepository(this);
        userRepository.open();

        // Set register button click listener
        userRegisterButton.setOnClickListener(v -> {
            String name = userNameInput.getText().toString().trim();
            String address = userAddressInput.getText().toString().trim();
            String gender = userGenderSpinner.getSelectedItem().toString();
            String email = userEmailInput.getText().toString().trim();
            String mobile = userMobileInput.getText().toString().trim();
            String password = userPasswordInput.getText().toString().trim();

            // Validate inputs
            if (name.isEmpty() || address.isEmpty() || email.isEmpty() || mobile.isEmpty() || password.isEmpty()) {
                Toast.makeText(UserSignUpActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
            } else if (!isValidEmail(email)) {
                Toast.makeText(UserSignUpActivity.this, "Invalid email address", Toast.LENGTH_SHORT).show();
            } else if (!isValidMobile(mobile)) {
                Toast.makeText(UserSignUpActivity.this, "Mobile number must be at least 10 digits and contain only numbers", Toast.LENGTH_SHORT).show();
            } else {
                // Store user data in the database
                long result = userRepository.insertUser(name, address, gender, email, mobile, password);
                if (result != -1) {
                    Toast.makeText(UserSignUpActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                    // Navigate to UserDashboardActivity
                    Intent intent = new Intent(UserSignUpActivity.this, UserDashboardActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(UserSignUpActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Method to validate email
    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Method to validate mobile number
    private boolean isValidMobile(String mobile) {
        return mobile.matches("\\d{10,}");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userRepository.close();
    }
}