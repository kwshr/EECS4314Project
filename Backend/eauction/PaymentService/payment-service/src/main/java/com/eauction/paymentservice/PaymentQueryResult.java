package com.eauction.paymentservice;

public class PaymentQueryResult {
    private String message;
    private PaymentQueryResultStatus paymentQueryResultStatus;

    public PaymentQueryResult(PaymentQueryResultStatus paymentQueryResultStatus, String message){
        this.paymentQueryResultStatus = paymentQueryResultStatus;
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public PaymentQueryResultStatus getPaymentQueryResultStatus(){
        return paymentQueryResultStatus;
    }
    public void setPaymentQueryResultStatus(PaymentQueryResultStatus paymentQueryResultStatus){
        this.paymentQueryResultStatus = paymentQueryResultStatus;
    }
}
