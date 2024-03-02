package com.eauction.paymentservice;

public interface Payment {
    PaymentQueryResult processPayment(String userName,int itemId);
}
