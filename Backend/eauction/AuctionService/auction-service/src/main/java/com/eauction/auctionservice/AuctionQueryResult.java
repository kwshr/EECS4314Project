package com.eauction.auctionservice;

public class AuctionQueryResult {
    private String message;
    private AuctionQueryResultStatus auctionQueryResultStatus;

    public AuctionQueryResult(AuctionQueryResultStatus auctionQueryResultStatus, String message){
        this.auctionQueryResultStatus = auctionQueryResultStatus;
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public AuctionQueryResultStatus getAuthorizationStatus(){
        return auctionQueryResultStatus;
    }
    public void setAuthorizationStatus(AuctionQueryResultStatus auctionQueryResultStatus){
        this.auctionQueryResultStatus = auctionQueryResultStatus;
    }
}
