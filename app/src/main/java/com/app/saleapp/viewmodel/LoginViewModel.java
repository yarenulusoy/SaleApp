package com.app.saleapp.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.saleapp.model.User;


public class LoginViewModel extends ViewModel {
    public ObservableField<String> username = new ObservableField<>();
    public ObservableField<String> password = new ObservableField<>();
    public MutableLiveData<Boolean> loginSuccess = new MutableLiveData<>();

    public boolean isValidUser(User user) {
        int userId = user.getUserId();
        String regexPattern = "^ABC" + userId + (userId + 1);

        return user.getPassword().matches(regexPattern);
    }

    public void onLoginClick() {

        String enteredUsername = username.get();
        String enteredPassword = password.get();

        if (enteredUsername == null || enteredPassword == null) {
            loginSuccess.setValue(false);
        } else {
            try {
                int userId = Integer.parseInt(enteredUsername);
                User user = new User(userId, enteredPassword);
                loginSuccess.setValue(isValidUser(user));
            } catch (NumberFormatException e) {
                loginSuccess.setValue(false);
            }
        }

    }
}