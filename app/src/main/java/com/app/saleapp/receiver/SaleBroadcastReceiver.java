package com.app.saleapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class SaleBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String response = intent.getStringExtra("SALE_RESULT_ACTION");
        if (response != null) {
            Log.d("Activity", "Alınan cevap: " + response);
        }
    }
}