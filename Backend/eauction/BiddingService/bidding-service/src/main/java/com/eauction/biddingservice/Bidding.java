package com.eauction.biddingservice;

import com.common.User;

public interface Bidding {
    public void placeBid (int itemId, double newPrice, User user);
    public Double updateForwardAuctionprice(int itemId, double newprice);
}
