package com.eauction.auctionservice;

public class AuctionQueryResult {
    private String message;
    private AuctionQueryResultStatus auctionQueryResultStatus;
    private Object obj;

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

    public AuctionQueryResultStatus getAuctionStatus(){
        return auctionQueryResultStatus;
    }

    public void setAuctionStatus(AuctionQueryResultStatus auctionQueryResultStatus){
        this.auctionQueryResultStatus = auctionQueryResultStatus;
    }

    public void setData(Object obj){
        this.obj = obj;
    }

    public Object getData(){
        return this.obj;
    }

}
