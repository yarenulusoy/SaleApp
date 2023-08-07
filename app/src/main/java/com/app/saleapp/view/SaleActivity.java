package com.app.saleapp.view;

import androidx.appcompat.app.AppCompatActivity;

import com.app.saleapp.R;
import com.app.saleapp.utils.DialogUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

public class SaleActivity extends AppCompatActivity {

    private EditText productIdEditText;
    private EditText productNameEditText;
    private EditText priceEditText;
    private EditText vatRateEditText;


    private final BroadcastReceiver responseReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int status = intent.getIntExtra("status", -1);
            int paymentType = intent.getIntExtra("paymentType", -1);
            Log.d("Activity", "Alınan cevap: " + status + " " + paymentType);
            handlePaymentResponse(status, paymentType);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);

        productIdEditText = findViewById(R.id.editTextProductId);
        productNameEditText = findViewById(R.id.editTextProductName);
        priceEditText = findViewById(R.id.editTextPrice);
        vatRateEditText = findViewById(R.id.editTextVatRate);
        Button cancelButton = findViewById(R.id.buttonCancel);
        Button submitButton = findViewById(R.id.buttonSubmit);

        IntentFilter filter = new IntentFilter("RESPONSE_ACTION");
        registerReceiver(responseReceiver, filter);

        cancelButton.setOnClickListener(v -> clearForm());

        submitButton.setOnClickListener(v -> {

            if (validateForm()) {
                sendToRegistry();
            } else {
                DialogUtils.showDialog(this, "Please correct invalid values");
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(responseReceiver);
    }


    private void clearForm() {
        productIdEditText.setText("");
        productNameEditText.setText("");
        priceEditText.setText("");
        vatRateEditText.setText("");
    }

    private boolean validateForm() { //viewmodele alınacak
        String productId = productIdEditText.getText().toString();
        String productName = productNameEditText.getText().toString();
        String price = priceEditText.getText().toString();
        String vatRate = vatRateEditText.getText().toString();

        if (productId.isEmpty() || productName.isEmpty() || price.isEmpty() || vatRate.isEmpty()) {
            return false;
        }

        int productIdInt;
        double priceDouble;
        int vatRateInt;

        try {
            productIdInt = Integer.parseInt(productId);
            priceDouble = Double.parseDouble(price);
            vatRateInt = Integer.parseInt(vatRate);
        } catch (NumberFormatException e) {
            return false;
        }

        if (productIdInt < 1 || productIdInt > 9999) {
            return false;
        }

        if (productName.length() > 20) {
            return false;
        }

        if (priceDouble < 0.01 || priceDouble > 99.99) {
            return false;
        }

        if (vatRateInt < 0 || vatRateInt > 99) {
            return false;
        }

        return true;
    }


    private void sendToRegistry() {
        String packageName = "com.app.registryapp";
        int productId = Integer.parseInt(productIdEditText.getText().toString());
        String productName = productNameEditText.getText().toString();
        double price = Double.parseDouble(priceEditText.getText().toString());
        int vatRate = Integer.parseInt(vatRateEditText.getText().toString());

        Bundle bundle = new Bundle();
        bundle.putInt("PRODUCT_ID", productId);
        bundle.putString("PRODUCT_NAME", productName);
        bundle.putDouble("PRICE", price);
        bundle.putInt("VAT_RATE", vatRate);

        Intent intent = new Intent("SALE_ACTION");
        intent.setPackage(packageName);
        intent.putExtras(bundle);

        // Servisi başlatın
        startForegroundService(intent);



    }

    private void handlePaymentResponse(int status, int paymentType) {
        if (status == 1) {
            displayMessage("Tekrar Deneyiniz");
        } else if (status == 2) {
            clearForm();
            displayMessage("İşlem İptal Edildi");
        } else if (status == 3) {
            clearForm();
            displayPaymentMessage(paymentType);
        }
    }

    private void displayMessage(String message) {
        DialogUtils.showDialog(this, message);
    }

    private void displayPaymentMessage(int paymentType) {
        String paymentMessage = "";
        switch (paymentType) {
            case 1:
                paymentMessage = "Ödeme Tamamlandı, Ödeme Türü: Nakit";
                break;
            case 2:
                paymentMessage = "Ödeme Tamamlandı, Ödeme Türü: Kredi";
                break;
            case 3:
                paymentMessage = "Ödeme Tamamlandı, Ödeme Türü: Karekod";
                break;
            default:
                paymentMessage = "Ödeme Tamamlanmadı.";
        }
        displayMessage(paymentMessage);
    }
}

