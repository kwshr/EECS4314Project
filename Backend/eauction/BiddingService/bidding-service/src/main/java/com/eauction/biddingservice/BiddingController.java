package com.eauction.biddingservice;

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
public class BiddingController {
    
    @Autowired
    private BiddingImpl biddingImpl;

    public BiddingController(BiddingImpl biddingImpl){
        this.biddingImpl = biddingImpl;
    }

    @RequestMapping(value="/placeBid/{userName}/{itemId}/{newPrice}", method=RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> generateReceipt(@PathVariable("userName") String userName,@PathVariable("itemId") int itemId, @PathVariable("newPrice") Double newPrice ) {
        Map<String,Object> response = new HashMap<String,Object>();
        BiddingQueryResult biddingQueryResult= biddingImpl.placeBid(itemId, newPrice, userName);
        response.put("message",biddingQueryResult.getMessage());
        response.put("queryStatus", biddingQueryResult.getItemCatalogueQueryResultStatus());
        return HttpResponseStatus.setResponse(response);
    }
}
