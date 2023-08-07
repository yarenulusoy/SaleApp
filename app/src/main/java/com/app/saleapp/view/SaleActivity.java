package com.app.saleapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.app.saleapp.R;
import com.app.saleapp.viewmodel.SaleViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SaleActivity extends AppCompatActivity {

    private EditText productIdEditText;
    private EditText productNameEditText;
    private EditText priceEditText;
    private EditText vatRateEditText;

    private SaleViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);

        viewModel = new ViewModelProvider(this).get(SaleViewModel.class);

        productIdEditText = findViewById(R.id.editTextProductId);
        productNameEditText = findViewById(R.id.editTextProductName);
        priceEditText = findViewById(R.id.editTextPrice);
        vatRateEditText = findViewById(R.id.editTextVatRate);
        Button cancelButton = findViewById(R.id.buttonCancel);
        Button submitButton = findViewById(R.id.buttonSubmit);

        cancelButton.setOnClickListener(v -> clearForm());

        submitButton.setOnClickListener(v -> {
            if (validateForm()) {
                sendToRegistry();
            } else {
                Toast.makeText(SaleActivity.this, "Please correct invalid values", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearForm() {
        productIdEditText.setText("");
        productNameEditText.setText("");
        priceEditText.setText("");
        vatRateEditText.setText("");
    }

    private boolean validateForm() {
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
        int productId = Integer.parseInt(productIdEditText.getText().toString());
        String productName = productNameEditText.getText().toString();
        double price = Double.parseDouble(priceEditText.getText().toString());
        int vatRate = Integer.parseInt(vatRateEditText.getText().toString());

        Intent intent = new Intent();
        intent.setAction("com.app.registryApp.SALE_ACTION");
        Bundle bundle = new Bundle();

        bundle.putInt("PRODUCT_ID", productId);
        bundle.putString("PRODUCT_NAME", productName);
        bundle.putDouble("PRICE", price);
        bundle.putInt("VAT_RATE", vatRate);

        intent.putExtras(bundle);
        sendBroadcast(intent);


    }
}
