package com.eauction.shippingservice;

public interface Shipping {
    public double calculateShippingCost(int itemId, String shippingType);
    public String displayShippingDetails(int itemId, Details userDetails);
}
