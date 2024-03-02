package com.eauction.paymentservice;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class HttpResponseStatus {
    public static ResponseEntity<Map<String,Object>> setResponse(Map<String,Object> response){
        HttpStatus status = HttpStatus.PROCESSING;
        if(response.get("queryStatus")== PaymentQueryResultStatus.PAYMENT_FAILED)
        status = HttpStatus.NOT_ACCEPTABLE;
        else if(response.get("queryStatus")== PaymentQueryResultStatus.PAYMENT_SUCCESS)
        status = HttpStatus.OK;
        else
        status = HttpStatus.NOT_IMPLEMENTED;
        response.put("status", status);
        return ResponseEntity.status(status).body(response);
    }
}
