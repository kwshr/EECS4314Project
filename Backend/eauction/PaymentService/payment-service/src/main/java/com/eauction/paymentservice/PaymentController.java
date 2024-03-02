package com.eauction.paymentservice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class PaymentController{
    
    @Autowired
    private PaymentImpl paymentImpl;

    @RequestMapping(value="/processPayment/{userName}/{itemId}", method=RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> auctionStart(@PathVariable("userName") String userName,@PathVariable("itemId") int itemId) {
         Map<String,Object>  response = new HashMap<>();
         PaymentQueryResult paymentQueryResult = paymentImpl.processPayment(userName, itemId);
         response.put("message",paymentQueryResult.getMessage());
         response.put("status",paymentQueryResult.getPaymentQueryResultStatus());
         return HttpResponseStatus.setResponse(response);
    }
}