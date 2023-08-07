package com.app.saleapp.model;

public class Sale {
    private final int productId;
    private final String productName;
    private final double price;
    private final int vatRate;

    public Sale(int productId, String productName, double price, int vatRate) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.vatRate = vatRate;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public int getVatRate() {
        return vatRate;
    }
}
