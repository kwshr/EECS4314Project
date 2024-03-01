package com.eauction.orderprocessingservice;

public class OrderProcessingQueryResult {
    private String message;
    private OrderProcessingQueryResultStatus orderProcessingQueryResultStatus;

    public OrderProcessingQueryResult(OrderProcessingQueryResultStatus orderProcessingQueryResultStatus, String message){
        this.orderProcessingQueryResultStatus = orderProcessingQueryResultStatus;
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public OrderProcessingQueryResultStatus getOrderProcessingQueryResultStatus(){
        return orderProcessingQueryResultStatus;
    }
    public void setItemCatalogueQueryResultStatus(OrderProcessingQueryResultStatus orderProcessingQueryResultStatus){
        this.orderProcessingQueryResultStatus = orderProcessingQueryResultStatus;
    }
}
