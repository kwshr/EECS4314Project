package com.eauction.biddingservice;

public interface Bidding {
    BiddingQueryResult placeBid (int itemId, double newPrice, String userName);
    BiddingQueryResult updateForwardAuctionprice(int itemId, double newprice,String userName);
}
