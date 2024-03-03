package com.eauction.biddingservice;

public class BiddingQueryResult {
    private String message;
    private BiddingQueryResultStatus biddingQueryResultStatus;

    public BiddingQueryResult(BiddingQueryResultStatus biddingQueryResultStatus, String message){
        this.biddingQueryResultStatus = biddingQueryResultStatus;
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public BiddingQueryResultStatus getItemCatalogueQueryResultStatus(){
        return biddingQueryResultStatus;
    }
    public void setBiddingQueryResultStatus(BiddingQueryResultStatus biddingQueryResultStatus){
        this.biddingQueryResultStatus = biddingQueryResultStatus;
    }
}
