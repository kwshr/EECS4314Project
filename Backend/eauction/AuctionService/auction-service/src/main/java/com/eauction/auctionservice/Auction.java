package com.eauction.auctionservice;

public interface Auction {
    public void startAuction(int itemId);
    public String getRemainingTimeUpdate(int itemId); 
    public String endAuction (int itemId);

}
