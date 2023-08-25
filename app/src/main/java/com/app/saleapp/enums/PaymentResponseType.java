package com.app.saleapp.enums;

import com.app.saleapp.constants.Constants;

public enum PaymentResponseType {
    TRY_AGAIN(Constants.TRY_NUMBER), CANCEL_PROCESS(Constants.CANCEL_NUMBER);
    private final int index;

    PaymentResponseType(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}