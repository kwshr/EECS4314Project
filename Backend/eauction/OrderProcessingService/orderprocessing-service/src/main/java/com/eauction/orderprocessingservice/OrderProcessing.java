package com.eauction.orderprocessingservice;

public interface OrderProcessing {
    OrderProcessingQueryResult generateReceipt (int itemId, String userName);
    OrderProcessingQueryResult updateItems(int itemId);
}
