package com.eauction.biddingservice;

public interface Bidding {
    BiddingQueryResult placeBid (int itemId, double newPrice, String userName);
}
