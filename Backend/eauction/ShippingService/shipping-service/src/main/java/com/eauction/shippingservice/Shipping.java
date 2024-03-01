package com.eauction.shippingservice;

public interface Shipping {
    ShippingQueryResult calculateShippingCost(int itemId, String shippingType);
    ShippingQueryResult displayShippingDetails(int itemId);
}
