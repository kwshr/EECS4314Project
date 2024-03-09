package com.eauction.sellerservice;

import java.util.*;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.common.Item;
import com.fasterxml.jackson.core.JsonProcessingException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@RestController
@RequestMapping
public class SellerController {

    @Autowired
    private SellerImpl sellerImpl;

    OkHttpClient client = new OkHttpClient();

    public SellerController(SellerImpl sellerImpl){
        this.sellerImpl = sellerImpl;
    }
    
    @RequestMapping(value="/addItem", method=RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> addSellItems(@RequestBody Item item) throws JsonProcessingException {
        Map<String,Object> response = new HashMap<String,Object>();
        SellerQueryResult sellerQueryResult = sellerImpl.addSellItems(item);
        if(sellerQueryResult.getSellerServiceQueryStatus() == SellerServiceQueryStatus.SUCCESS){
            int itemId = (Integer) sellerQueryResult.getData();
            String baseuri = "http://localhost:8080/createAuction";
            JSONObject formBody = new JSONObject();
            formBody.put("itemId",String.valueOf(itemId));
            formBody.put("currentPrice", String.valueOf(item.getPrice()));
            formBody.put("auctionType", item.getAuctionType());
            okhttp3.MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            okhttp3.RequestBody body = okhttp3.RequestBody.create(JSON,formBody.toString());
            Request request = new Request.Builder().url(baseuri).post(body).build();
            try{
                Response responseAuction = client.newCall(request).execute();
                String responseBody = responseAuction.body().string();
                JSONObject jsonResponse = new JSONObject(responseBody);
                String message = jsonResponse.getString("message");
                String queryStatus = jsonResponse.getString("message");
                response.put("AuctionMessage",message);
                response.put("AuctionQueryStatus",queryStatus);
            } catch (Exception e){
                response.put("ExceptionAuctionUpdateRequest",e.getMessage());
            }
        }
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
