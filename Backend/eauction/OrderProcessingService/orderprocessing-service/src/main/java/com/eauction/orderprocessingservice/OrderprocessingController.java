package com.eauction.orderprocessingservice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class OrderprocessingController {

    @Autowired
    private OrderProcessingImpl orderProcessingImpl;

    @RequestMapping(value="/generateReceipt", method=RequestMethod.GET)
    public ResponseEntity<Map<String,String>> generateReceipt(@RequestBody OrderRequest request) {
        Map<String,String> response = new HashMap<String,String>();
        try{
         response = orderProcessingImpl.generateReceipt(request);
         return ResponseEntity.ok(response);
        }catch(Exception e){
            response.put("message","Failed to generate reciept at this time");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        
    }

    @RequestMapping(value="/updateItem/{itemId}", method=RequestMethod.DELETE)
    public ResponseEntity<Map<String,Object>> signUpUser(@PathVariable("itemId") int itemId) {
        OrderProcessingQueryResult orderProcessingQueryResult = orderProcessingImpl.updateItems(itemId);
        Map<String,Object> response = new HashMap<String,Object>();
        response.put("message",orderProcessingQueryResult.getMessage());
        response.put("queryStatus", orderProcessingQueryResult.getOrderProcessingQueryResultStatus());
        return HttpResponseStatus.setResponse(response);
    }
}
