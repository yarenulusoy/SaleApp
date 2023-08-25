package com.app.saleapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.app.saleapp.constants.Constants;
import com.app.saleapp.viewmodel.SaleViewModel;


public class SaleBroadcastReceiver extends BroadcastReceiver {

    private final SaleViewModel saleViewModel;

    public SaleBroadcastReceiver(SaleViewModel viewModel) {
        saleViewModel = viewModel;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int status = intent.getIntExtra(Constants.STATUS, Constants.RECEIVER_STATUS);
        int paymentType = intent.getIntExtra(Constants.PAYMENT_TYPE, Constants.RECEIVER_TYPE);
        saleViewModel.handlePaymentResponse(context, status, paymentType);
    }
}
