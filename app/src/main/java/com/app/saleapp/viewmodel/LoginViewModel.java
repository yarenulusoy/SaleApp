package com.app.saleapp.viewmodel;

import androidx.lifecycle.ViewModel;

import com.app.saleapp.model.User;

public class LoginViewModel extends ViewModel {

    public boolean isValidUser(User user) {
        String expectedPassword = "ABC" + user.getUserId() + (user.getUserId() + 1);
        return user.getPassword().equals(expectedPassword);
    }
}