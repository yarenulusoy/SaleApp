package com.app.saleapp.utils;

import android.app.AlertDialog;
import android.content.Context;

public class DialogUtils {

    public static void showDialog(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}