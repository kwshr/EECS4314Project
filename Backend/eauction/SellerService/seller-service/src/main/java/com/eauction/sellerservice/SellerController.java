package com.eauction.sellerservice;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.common.Item;

public class SellerController {

    @Autowired
    private SellerImpl sellerImpl;
    
    @RequestMapping(value="/addItem", method=RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> addSellItems(@RequestBody Item item) {
        SellerQueryResult sellerQueryResult = sellerImpl.addSellItems(item);
        Map<String,Object> response = new HashMap<String,Object>();
        response.put("message",sellerQueryResult.getMessage());
        response.put("queryStatus", sellerQueryResult.getSellerServiceQueryStatus());
        return HttpResponseStatus.setResponse(response);
    }

    @RequestMapping(value="/updatePrice", method=RequestMethod.PUT)
    public ResponseEntity<Map<String,Object>> updateDutchAuctionPrice(@RequestBody Map<String, String> params) {
        int itemId = Integer.parseInt(params.get("itemId"));
        double newPrice = Double.parseDouble(params.get("newPrice"));
        SellerQueryResult sellerQueryResult = sellerImpl.updateDutchAuctionprice(itemId,newPrice);
        Map<String,Object> response = new HashMap<String,Object>();
        response.put("message",sellerQueryResult.getMessage());
        response.put("queryStatus", sellerQueryResult.getSellerServiceQueryStatus());
        return HttpResponseStatus.setResponse(response);
    }
}
