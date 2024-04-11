package com.eauction.orderprocessingservice;

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
public class OrderprocessingController {

    @Autowired
    private OrderProcessingImpl orderProcessingImpl;

    public OrderprocessingController(OrderProcessingImpl orderProcessingImpl){
        this.orderProcessingImpl = orderProcessingImpl;
    }

    @RequestMapping(value="/generateReceipt/{userName}/{itemId}", method=RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> generateReceipt(@PathVariable("itemId") int itemId,@PathVariable("userName") String userName) {
        OrderProcessingQueryResult orderProcessingQueryResult = orderProcessingImpl.generateReceipt(itemId, userName);
        Map<String,Object> response = new HashMap<String,Object>();
        response.put("message",orderProcessingQueryResult.getMessage());
        response.put("queryStatus", orderProcessingQueryResult.getOrderProcessingQueryResultStatus());
        response.put("data", orderProcessingQueryResult.getData());
        return HttpResponseStatus.setResponse(response);
        
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
