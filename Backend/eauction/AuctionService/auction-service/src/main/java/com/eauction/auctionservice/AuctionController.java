package com.eauction.auctionservice;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
@CrossOrigin(origins = {"https://kwshr.github.io/*","http://localhost:3000/*" })
@RestController
@RequestMapping
public class AuctionController {

    @Autowired
    private AuctionImpl auctionImpl;

    public AuctionController(AuctionImpl auctionImpl){
      this.auctionImpl = auctionImpl;
    }

    @RequestMapping(value="/startAuction/{itemId}", method=RequestMethod.PUT)
    public ResponseEntity<Map<String,Object>> auctionStart(@PathVariable("itemId") int itemId) {
         Map<String,Object>  response = new HashMap<>();
         AuctionQueryResult auctionQueryResult = auctionImpl.startAuction(itemId);
         response.put("message", auctionQueryResult.getMessage());
         response.put("queryStatus", auctionQueryResult.getAuctionStatus());
         return HttpResponseStatus.setResponse(response);
    }

    @RequestMapping(value="/createAuction", method=RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> createAuction(@RequestBody Map<String,String> auction) {
         Map<String,Object>  response = new HashMap<>();
         AuctionQueryResult auctionQueryResult = auctionImpl.createAuction(auction);
         response.put("message", auctionQueryResult.getMessage());
         response.put("queryStatus", auctionQueryResult.getAuctionStatus());
         return HttpResponseStatus.setResponse(response);
    }

    @RequestMapping(value="/endAuction/{itemId}/{userID}", method=RequestMethod.PUT)
    public ResponseEntity<Map<String,Object>> updateWinnerDucth(@PathVariable("itemId") int itemId, @PathVariable("userID") int userID) {
         Map<String,Object>  response = new HashMap<>();
         AuctionQueryResult auctionQueryResult = auctionImpl.endAuction(itemId,userID);
         response.put("message", auctionQueryResult.getMessage());
         response.put("queryStatus", auctionQueryResult.getAuctionStatus());
         return HttpResponseStatus.setResponse(response);
    }

    @RequestMapping(value="/getRemainingTime/{itemId}", method=RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> getRemainingTime(@PathVariable("itemId") int itemId) {
         Map<String,Object> response = new HashMap<String,Object>();
         AuctionQueryResult auctionQueryResult = auctionImpl.getRemainingTimeUpdate(itemId);
         response.put("message",auctionQueryResult.getMessage());
         response.put("queryStatus", auctionQueryResult.getAuctionStatus());
         response.put("data", auctionQueryResult.getData());
         return HttpResponseStatus.setResponse(response);
    }

    @RequestMapping(value="/endAuction/{itemId}", method=RequestMethod.PUT)
    public ResponseEntity<Map<String,Object>> endAuction(@PathVariable("itemId") int itemId) {
         Map<String,Object>  response = new HashMap<>();
         try{
            AuctionQueryResult endResult = auctionImpl.endAuction(itemId);
            if (endResult.getAuctionStatus() == AuctionQueryResultStatus.SUCCESS) {
               AuctionQueryResult winner = auctionImpl.getWinner(itemId);
               response.put("Ending Auction Status", endResult.getMessage());
               response.put("message", winner.getMessage());
               response.put("data",winner.getData());
               return ResponseEntity.ok(response);
            } else {
                response.put("message", "Failed to end auction: " + endResult.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
         } catch (AuctionException e){
            response.put("message","Failed to end auction"+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
         }
    }
    @RequestMapping(value="/getAuctionedItemDetails/{itemId}", method=RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> getAuctionedItemDetails(@PathVariable("itemId") int itemId) {
     Map<String,Object> response = new HashMap<String,Object>();
     AuctionQueryResult auctionQueryResult = auctionImpl.getAuctionedItemDetails(itemId);
     response.put("message",auctionQueryResult.getMessage());
     response.put("queryStatus", auctionQueryResult.getAuctionStatus());
     response.put("data", auctionQueryResult.getData());
     return HttpResponseStatus.setResponse(response);
    }
}


