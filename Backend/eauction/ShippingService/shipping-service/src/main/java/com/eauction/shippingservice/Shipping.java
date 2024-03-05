package com.eauction.shippingservice;

public interface Shipping {
    ShippingQueryResult calculateShippingCost(int itemId);
    ShippingQueryResult setExpeditedShipping(int itemId);
    ShippingQueryResult displayShippingDetails(int itemId);
}
