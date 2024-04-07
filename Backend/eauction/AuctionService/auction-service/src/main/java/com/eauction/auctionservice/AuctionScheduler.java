package com.eauction.auctionservice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Component
public class AuctionScheduler {

    private AuctionImpl auctionImpl;

    public AuctionScheduler(AuctionImpl auctionImpl){
        this.auctionImpl = auctionImpl;
    }
    
    @Scheduled(fixedRate = 180000)
    public void scheduleAuctionController(){
        Map<Integer, Map<String,Object>> auctionedItems = getAllAuctionedItems();
        if(auctionedItems !=null){
            for(Map.Entry<Integer,Map<String,Object>> entry: auctionedItems.entrySet()){
                //int auctionID = entry.getKey();
                Map<String,Object> itemDetails = entry.getValue();
                int itemId = (int) itemDetails.get("ItemID");
                LocalDateTime startDateTime = (LocalDateTime) itemDetails.get("StartDateTime");
                LocalDateTime enDateTime = (LocalDateTime) itemDetails.get("EndDateTime");
                LocalDateTime curDateTime = LocalDateTime.now();
                System.out.println("Mock Auction Started for Item ID: " + itemId);
                if(curDateTime.isEqual(startDateTime)){
                    auctionImpl.startAuction(itemId);
                }
                else if(curDateTime.isEqual(enDateTime)){
                    auctionImpl.endAuction(itemId);
                }
            }
        }
        else if(auctionedItems == null ){
            System.out.println("Mock Auction Ended for Item ID: ");
                
        }
    }

     public Map<Integer, Map<String, Object>> getAllAuctionedItems(){
      OkHttpClient client = new OkHttpClient();
      String baseuri = "https://itemcatalogueservice.onrender.com/getAuctionedItems";
      Request request = new Request.Builder().url(baseuri).build();
      try{
          Response response = client.newCall(request).execute();
          String responseBody = response.body().string();
          JSONObject jsonResponse = new JSONObject(responseBody);
          Map<Integer, Map<String, Object>> auctions = new HashMap<>();
          JSONObject data = jsonResponse.getJSONObject("data");
          for(String key : data.keySet() ){
            int auctionId = Integer.parseInt(key);
            JSONObject auctionData = data.getJSONObject(key);
            Map<String,Object> auctionMap = new HashMap<>();
            for(String field : auctionData.keySet()){
                Object value = auctionData.get(field);
                auctionMap.put(field,value);
            }
            auctions.put(auctionId,auctionMap);
          }
          return auctions;
      } catch (Exception e){
          return null;
      }
    }
}
