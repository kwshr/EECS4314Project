package com.eauction.auctionservice;

import java.util.Map;

public interface Auction {
    AuctionQueryResult startAuction(int itemId);
    AuctionQueryResult getRemainingTimeUpdate(int itemId); 
    AuctionQueryResult endAuction (int itemId);
    AuctionQueryResult createAuction(Map<String,String> auction);
}
