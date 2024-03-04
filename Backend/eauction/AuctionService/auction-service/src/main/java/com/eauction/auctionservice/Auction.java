package com.eauction.auctionservice;

public interface Auction {
    AuctionQueryResult startAuction(int itemId);
    AuctionQueryResult getRemainingTimeUpdate(int itemId); 
    AuctionQueryResult endAuction (int itemId);

}
