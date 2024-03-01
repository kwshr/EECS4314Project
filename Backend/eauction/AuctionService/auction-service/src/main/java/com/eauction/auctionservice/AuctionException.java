package com.eauction.auctionservice;

public class AuctionException extends RuntimeException{
    public AuctionException(String message){
        super(message);
    }

    public AuctionException(String message,Throwable cause){
        super(message, cause);
    }

}