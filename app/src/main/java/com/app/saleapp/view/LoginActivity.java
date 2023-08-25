package com.app.saleapp.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.app.saleapp.utils.DialogUtils;
import com.app.saleapp.viewmodel.LoginViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.app.saleapp.R;
import com.app.saleapp.databinding.ActivityLoginBinding;


public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViews();
    }

    private void initViews() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        binding.setViewModel(loginViewModel);
        binding.setLifecycleOwner(this);

        loginViewModel.loginSuccess.observe(this, this::login);
    }

    private void login(Boolean success) {
        if (success) {
            Intent intent = new Intent(LoginActivity.this, SaleActivity.class);
            startActivity(intent);
        } else {
            DialogUtils.showDialog(LoginActivity.this, "Invalid Username or Password");
        }
    }

}