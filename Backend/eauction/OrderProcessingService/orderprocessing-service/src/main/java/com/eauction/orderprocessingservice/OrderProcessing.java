package com.eauction.orderprocessingservice;

import java.util.Map;

public interface OrderProcessing {
    OrderProcessingQueryResult generateReceipt (int itemId, String userName);
    OrderProcessingQueryResult updateItems(int itemId);
}
