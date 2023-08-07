package com.app.saleapp.viewmodel;
import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.saleapp.model.Sale;

public class SaleViewModel extends ViewModel {
    private final MutableLiveData<Sale> saleModel = new MutableLiveData<>();

    public LiveData<Sale> getSaleModel() {
        return saleModel;
    }


}
/*
    private boolean isValidForm() {
        try {
            String productIdStr = productId.get();
            if (productIdStr == null) {
                return false;
            }
            int parsedProductId = Integer.parseInt(productIdStr);
            if (parsedProductId < 1 || parsedProductId > 9999) {
                return false;
            }

            String parsedProductName = productName.get();
            if (parsedProductName == null || parsedProductName.length() < 1 || parsedProductName.length() > 20) {
                return false;
            }

            String priceStr = price.get();
            if (priceStr == null) {
                return false;
            }
            double parsedPrice = Double.parseDouble(priceStr);
            if (parsedPrice < 0.01 || parsedPrice > 99.99) {
                return false;
            }

            String vatRateStr = vatRate.get();
            if (vatRateStr == null) {
                return false;
            }
            int parsedVatRate = Integer.parseInt(vatRateStr);
            return parsedVatRate >= 0 && parsedVatRate <= 99;
        } catch (NumberFormatException e) {
            return false;
        }
    }*/

