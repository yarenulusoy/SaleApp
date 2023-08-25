package com.app.saleapp.viewmodel;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.saleapp.constants.Constants;
import com.app.saleapp.enums.PaymentResponseType;
import com.app.saleapp.enums.PaymentType;
import com.app.saleapp.receiver.SaleBroadcastReceiver;
import com.app.saleapp.utils.DialogUtils;


public class SaleViewModel extends ViewModel {
    public ObservableField<String> productId = new ObservableField<>();
    public ObservableField<String> productName = new ObservableField<>();
    public ObservableField<String> productPrice = new ObservableField<>();
    public ObservableField<String> productVat = new ObservableField<>();
    public MutableLiveData<Boolean> productSuccess = new MutableLiveData<>();

    int productIdInt;
    double priceDouble;
    int vatRateInt;

    private SaleBroadcastReceiver saleBroadcastReceiver;


    public void initializeReceiver(Context context) {
        saleBroadcastReceiver = new SaleBroadcastReceiver(this);
        IntentFilter filter = new IntentFilter(Constants.RESPONSE_ACTION);
        context.registerReceiver(saleBroadcastReceiver, filter);
    }

    public void releaseReceiver(Context context) {
        context.unregisterReceiver(saleBroadcastReceiver);
    }

    public void onSubmitClick() {

        String enteredProductId = productId.get();
        String enteredProductName = productName.get();
        String enteredProductPrice = productPrice.get();
        String enteredProductVat = productPrice.get();

        productSuccess.setValue(validateForm(enteredProductId, enteredProductName, enteredProductPrice, enteredProductVat));
    }

    private boolean validateForm(String productId, String productName, String productPrice, String productVat) {
        if (productId == null || productName == null || productPrice == null || productVat == null) {
            return false;
        }

        try {
            productIdInt = Integer.parseInt(productId);
            priceDouble = Double.parseDouble(productPrice);
            vatRateInt = Integer.parseInt(productVat);
        } catch (NumberFormatException e) {
            return false;
        }

        if (productIdInt < Constants.FIRST_PRODUCT_ID || productIdInt > Constants.END_PRODUCT_ID) {
            return false;
        }

        if (productName.length() > Constants.MAX_LENGTH) {
            return false;
        }

        if (priceDouble < Constants.FIRST_PRICE_DOUBLE || priceDouble > Constants.END_PRICE_DOUBLE) {
            return false;
        }

        if (vatRateInt < Constants.FIRST_VAT_RATE || vatRateInt > Constants.END_VAT_RATE) {
            return false;
        }
        return true;
    }

    public void clearForm() {
        productId.set("");
        productName.set("");
        productPrice.set("");
        productVat.set("");
    }

    public void sendToRegistry(Context context) {
        productIdInt = Integer.parseInt(productId.get());
        priceDouble = Double.parseDouble(productPrice.get());
        vatRateInt = Integer.parseInt(productVat.get());
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.GET_PRODUCT_ID, productIdInt);
        bundle.putString(Constants.GET_PRODUCT_NAME, productName.get());
        bundle.putDouble(Constants.GET_PRODUCT_AMOUNT, priceDouble);
        bundle.putInt(Constants.GET_PRODUCT_VAT, vatRateInt);

        Intent intent = new Intent(Constants.ACTION_NAME);
        intent.setPackage(Constants.targetAppPackageName);
        intent.putExtras(bundle);

        // Servisi başlatın
        context.startForegroundService(intent);
    }

    public void handlePaymentResponse(Context context, int status, int paymentType) {
        PaymentResponseType responseType = null;

        for (PaymentResponseType type : PaymentResponseType.values()) {
            if (type.getIndex() == status) {
                responseType = type;
                break;
            }
        }

        if (responseType != null) {
            switch (responseType) {
                case TRY_AGAIN:
                    DialogUtils.showDialog(context, Constants.TRY_AGAIN);
                    break;
                case CANCEL_PROCESS:
                    clearForm();
                    DialogUtils.showDialog(context, Constants.CANCEL_PROCESS);
                    break;
            }
        } else {
            clearForm();
            displayPaymentMessage(paymentType, context);
        }
    }


    private void displayPaymentMessage(int index, Context context) {
        String paymentMessage = "";
        PaymentType responseType = null;

        for (PaymentType type : PaymentType.values()) {
            if (type.getIndex() == index) {
                responseType = type;
                break;
            }
        }
        switch (responseType) {
            case CASH:
                paymentMessage = Constants.CASH;
                break;
            case CREDIT:
                paymentMessage = Constants.CREDIT;
                break;
            case QR:
                paymentMessage = Constants.QR;
                break;
        }
        DialogUtils.showDialog(context, paymentMessage);
    }


}



