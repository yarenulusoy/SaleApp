package com.app.saleapp.utils;

import android.app.AlertDialog;
import android.content.Context;

import com.app.saleapp.constants.Constants;

public class DialogUtils {
    public static void showDialog(Context context, String message) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton(Constants.OK, null)
                .show();
    }
}