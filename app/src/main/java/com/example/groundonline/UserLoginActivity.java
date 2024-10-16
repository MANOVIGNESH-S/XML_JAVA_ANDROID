package com.example.groundonline;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class UserLoginActivity extends AppCompatActivity {

    private EditText userEmailInput, userPasswordInput;
    private Button userLoginButton;
    private TextView userSignUpLink;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        // Initialize UI components
        userEmailInput = findViewById(R.id.userUsernameInput); // Now userUsernameInput is in the layout
        userPasswordInput = findViewById(R.id.userPasswordInput);
        userLoginButton = findViewById(R.id.userLoginButton);
        userSignUpLink = findViewById(R.id.userSignUpLink);

        // Initialize the repository
        userRepository = new UserRepository(this);
        userRepository.open();

        // Set login button listener
        userLoginButton.setOnClickListener(v -> {
            String email = userEmailInput.getText().toString().trim();
            String password = userPasswordInput.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(UserLoginActivity.this, "Email and password are required", Toast.LENGTH_SHORT).show();
                return;
            }

            int loginResult = userRepository.validateUser(email, password);
            switch (loginResult) {
                case UserRepository.LOGIN_SUCCESS:
                    Toast.makeText(UserLoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UserLoginActivity.this, UserDashboardActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case UserRepository.LOGIN_INVALID_EMAIL:
                    Toast.makeText(UserLoginActivity.this, "Invalid email", Toast.LENGTH_SHORT).show();
                    break;
                case UserRepository.LOGIN_INVALID_PASSWORD:
                    Toast.makeText(UserLoginActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(UserLoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                    break;
            }
        });

        // Set sign-up link listener
        userSignUpLink.setOnClickListener(v -> {
            Intent intent = new Intent(UserLoginActivity.this, UserSignUpActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userRepository.close();
    }
}
