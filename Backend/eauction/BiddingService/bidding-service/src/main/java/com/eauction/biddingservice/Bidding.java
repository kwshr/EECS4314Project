package com.eauction.biddingservice;

public interface Bidding {
    public void placeBid (int itemId, double newPrice, User user);
    public Double updateForwardAuctionprice(int itemId, double newprice);
}
