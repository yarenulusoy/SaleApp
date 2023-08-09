package com.app.saleapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.app.saleapp.R;
import com.app.saleapp.model.User;
import com.app.saleapp.utils.DialogUtils;
import com.app.saleapp.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (loginViewModel.login(username, password)) {
                Intent intent = new Intent(this, SaleActivity.class);
                startActivity(intent);
            } else {
                DialogUtils.showDialog(this, "Invalid Username or Password");
            }
        });
    }

}