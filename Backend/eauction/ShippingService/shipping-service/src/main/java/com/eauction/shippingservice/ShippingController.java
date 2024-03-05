package com.eauction.shippingservice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class ShippingController {

    @Autowired
    private ShippingImpl shippingImpl;

    @RequestMapping(value="/calculateShippingCost/{itemid}", method=RequestMethod.PUT)
    public ResponseEntity<Map<String,Object>> calculateShippingCost(@PathVariable("itemId") int itemId ) {
        ShippingQueryResult shippingQueryResult = shippingImpl.calculateShippingCost(itemId);
        Map<String,Object> response = new HashMap<String,Object>();
        response.put("message",shippingQueryResult.getMessage());
        response.put("queryStatus", shippingQueryResult.getItemCatalogueQueryResultStatus());
        response.put("data",shippingQueryResult.getData());
        return HttpResponseStatus.setResponse(response);
    }

    @RequestMapping(value="/displayShippingDetails/{itemid}", method=RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> displayShippingDetails(@PathVariable("itemId") int itemId) {
        ShippingQueryResult shippingQueryResult = shippingImpl.displayShippingDetails(itemId);
        Map<String,Object> response = new HashMap<String,Object>();
        response.put("message",shippingQueryResult.getMessage());
        response.put("queryStatus", shippingQueryResult.getItemCatalogueQueryResultStatus());
        response.put("data",shippingQueryResult.getData());
        return HttpResponseStatus.setResponse(response);
    }

    @RequestMapping(value="/expeditedShipping/{itemId}", method=RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> expeditedhipping(@PathVariable("itemId") int itemId) {
        ShippingQueryResult shippingQueryResult = shippingImpl.setExpeditedShipping(itemId);
        Map<String,Object> response = new HashMap<String,Object>();
        response.put("message",shippingQueryResult.getMessage());
        response.put("queryStatus", shippingQueryResult.getItemCatalogueQueryResultStatus());
        response.put("data",shippingQueryResult.getData());
        return HttpResponseStatus.setResponse(response);
    }
}
