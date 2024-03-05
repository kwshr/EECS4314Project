package com.eauction.orderprocessingservice;

public class OrderProcessingQueryResult {
    private String message;
    private OrderProcessingQueryResultStatus orderProcessingQueryResultStatus;
    private Object obj;

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

    public void setData(Object obj){
        this.obj = obj;
    }

    public OrderProcessingQueryResultStatus getOrderProcessingQueryResultStatus(){
        return orderProcessingQueryResultStatus;
    }

    public void setOrderProcessingQueryResultStatus(OrderProcessingQueryResultStatus orderProcessingQueryResultStatus){
        this.orderProcessingQueryResultStatus = orderProcessingQueryResultStatus;
    }

    public Object getData(){
        return obj;
    }
}
