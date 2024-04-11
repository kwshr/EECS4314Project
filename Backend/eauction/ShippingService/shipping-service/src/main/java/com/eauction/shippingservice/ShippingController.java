package com.eauction.shippingservice;

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
public class ShippingController {

    @Autowired
    private ShippingImpl shippingImpl;

    public ShippingController(ShippingImpl shippingImpl){
        this.shippingImpl = shippingImpl;
    }

    @RequestMapping(value="/calculateShippingCost/{itemId}", method=RequestMethod.PUT)
    public ResponseEntity<Map<String,Object>> calculateShippingCost(@PathVariable("itemId") int itemId ) {
        ShippingQueryResult shippingQueryResult = shippingImpl.calculateShippingCost(itemId);
        Map<String,Object> response = new HashMap<String,Object>();
        response.put("message",shippingQueryResult.getMessage());
        response.put("queryStatus", shippingQueryResult.getItemCatalogueQueryResultStatus());
        response.put("data",shippingQueryResult.getData());
        return HttpResponseStatus.setResponse(response);
    }

    @RequestMapping(value="/displayShippingDetails/{itemId}", method=RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> displayShippingDetails(@PathVariable("itemId") int itemId) {
        ShippingQueryResult shippingQueryResult = shippingImpl.displayShippingDetails(itemId);
        Map<String,Object> response = new HashMap<String,Object>();
        response.put("message",shippingQueryResult.getMessage());
        response.put("queryStatus", shippingQueryResult.getItemCatalogueQueryResultStatus());
        response.put("data",shippingQueryResult.getData());
        return HttpResponseStatus.setResponse(response);
    }

    @RequestMapping(value="/expeditedShipping/{itemId}", method=RequestMethod.PUT)
    public ResponseEntity<Map<String,Object>> expeditedhipping(@PathVariable("itemId") int itemId) {
        ShippingQueryResult shippingQueryResult = shippingImpl.setExpeditedShipping(itemId);
        Map<String,Object> response = new HashMap<String,Object>();
        response.put("message",shippingQueryResult.getMessage());
        response.put("queryStatus", shippingQueryResult.getItemCatalogueQueryResultStatus());
        response.put("data",shippingQueryResult.getData());
        return HttpResponseStatus.setResponse(response);
    }
}
