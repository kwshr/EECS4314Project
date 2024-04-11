package com.eauction.paymentservice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"https://kwshr.github.io/*","http://localhost:3000/*" })
@RestController
@RequestMapping
public class PaymentController{
    
    @Autowired
    private PaymentImpl paymentImpl;

    public PaymentController(PaymentImpl paymentImpl){
        this.paymentImpl = paymentImpl;
    }
    
    @RequestMapping(value="/processPayment/{userName}/{itemId}", method=RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> auctionStart(@PathVariable("userName") String userName,@PathVariable("itemId") int itemId) {
         Map<String,Object>  response = new HashMap<>();
         PaymentQueryResult paymentQueryResult = paymentImpl.processPayment(userName, itemId);
         response.put("message",paymentQueryResult.getMessage());
         response.put("status",paymentQueryResult.getPaymentQueryResultStatus());
         return HttpResponseStatus.setResponse(response);
    }
}