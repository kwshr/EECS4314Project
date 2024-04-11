package com.eauction.itemcatalogueservice;

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
public class ItemCatalogueController {
    
    @Autowired
    private final ItemCatalogueImpl itemCatalogueImpl;
    
    public ItemCatalogueController(ItemCatalogueImpl itemCatalogueImpl){
        this.itemCatalogueImpl = itemCatalogueImpl;
    }

    @RequestMapping(value="/search/{keyword}", method=RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> signUpUser(@PathVariable("keyword") String keyword) {
        ItemCatalogueQueryResult itemCatalogueQueryResult = itemCatalogueImpl.Search(keyword);
        Map<String,Object> response = new HashMap<String,Object>();
        response.put("message",itemCatalogueQueryResult.getMessage());
        response.put("queryStatus", itemCatalogueQueryResult.getItemCatalogueQueryResultStatus());
        response.put("data",itemCatalogueQueryResult.getData());
        return HttpResponseStatus.setResponse(response);
    }

    @RequestMapping(value="/getAuctionedItems", method=RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> getAuctionedItems() {
        ItemCatalogueQueryResult itemCatalogueQueryResult = itemCatalogueImpl.getAuctionedItems();
        Map<String,Object> response = new HashMap<String,Object>();
        response.put("message",itemCatalogueQueryResult.getMessage());
        response.put("queryStatus", itemCatalogueQueryResult.getItemCatalogueQueryResultStatus());
        response.put("data",itemCatalogueQueryResult.getData());
        return HttpResponseStatus.setResponse(response);
    }

    @RequestMapping(value="/getItem/{itemId}", method=RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> passwordReset(@PathVariable("userName") int itemId) {
        ItemCatalogueQueryResult itemCatalogueQueryResult = itemCatalogueImpl.getItem(itemId);
        Map<String,Object> response = new HashMap<String,Object>();
        response.put("message",itemCatalogueQueryResult.getMessage());
        response.put("queryStatus", itemCatalogueQueryResult.getItemCatalogueQueryResultStatus());
        response.put("data",itemCatalogueQueryResult.getData());
        return HttpResponseStatus.setResponse(response);
    }
}
