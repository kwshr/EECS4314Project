package com.eauction.paymentservice;

import org.springframework.stereotype.Service;

@Service
public class PaymentImpl implements Payment{

    @Override
    public PaymentQueryResult processPayment(String userName,int itemId) {
        // make a table for processed payments
        throw new UnsupportedOperationException("Unimplemented method 'processPayment'");
    }
    
}
