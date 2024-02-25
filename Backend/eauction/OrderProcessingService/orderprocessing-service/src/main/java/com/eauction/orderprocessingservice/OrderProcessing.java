package com.eauction.orderprocessingservice;

public interface OrderProcessing {
    public Receipt generateReceipt (int itemId, User user);
    public void updateItems(int itemId);
}
