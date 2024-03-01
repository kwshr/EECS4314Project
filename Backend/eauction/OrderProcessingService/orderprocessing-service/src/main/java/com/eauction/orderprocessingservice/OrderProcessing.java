package com.eauction.orderprocessingservice;

import java.util.Map;

public interface OrderProcessing {
    public Map<String,String> generateReceipt (OrderRequest request);
    OrderProcessingQueryResult updateItems(int itemId);
}
