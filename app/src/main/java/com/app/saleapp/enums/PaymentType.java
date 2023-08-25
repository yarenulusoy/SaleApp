package com.app.saleapp.enums;

import com.app.saleapp.constants.Constants;

public enum PaymentType {
    CASH(Constants.CASH_NUMBER), CREDIT(Constants.CREDIT_NUMBER), QR(Constants.QR_NUMBER);

    private final int index;

    PaymentType(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}