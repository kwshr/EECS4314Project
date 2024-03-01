package com.eauction.auctionservice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.common.User;

import io.micrometer.core.ipc.http.HttpSender.Response;

public class AuctionController {

    @Autowired
    private AuctionImpl auctionImpl;

    @RequestMapping(value="/startAuction/{itemId}", method=RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> auctionStart(@RequestBody int itemId) {
         Map<String,Object>  response = new HashMap<>();
         try{
            auctionImpl.startAuction(itemId);
            response.put("message","Auction started succesfully");
            return ResponseEntity.ok(response);
         } catch (AuctionException e){
            response.put("message","Failed to start auction"+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
         }
    }

    @RequestMapping(value="/getRemainingTime/{itemId}", method=RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> getRemainingTime(@RequestBody int itemId) {
         Map<String,Object>  response = new HashMap<>();
         try{
         String time = auctionImpl.getRemainingTimeUpdate(itemId);
         response.put("message","Remaining time: "+ time);
         return ResponseEntity.ok(response);
        }catch(Exception e){
            response.put("message","Failed to retrive the remaining time");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @RequestMapping(value="/endAuction/{itemId}", method=RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> endAuction(@RequestBody int itemId) {
         Map<String,Object>  response = new HashMap<>();
         try{
         auctionImpl.endAuction(itemId);
         response.put("message","Auction ended succesfully");
            return ResponseEntity.ok(response);
         } catch (AuctionException e){
            response.put("message","Failed to end auction"+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
         }
    }
}


