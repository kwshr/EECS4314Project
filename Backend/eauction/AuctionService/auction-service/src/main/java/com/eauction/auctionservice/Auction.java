package com.eauction.auctionservice;

import java.util.Map;

public interface Auction {
    AuctionQueryResult startAuction(int itemId);
    AuctionQueryResult getAuctionedItemDetails(int itemId);
    AuctionQueryResult getRemainingTimeUpdate(int itemId); 
    AuctionQueryResult endAuction (int itemId);
    AuctionQueryResult endAuction (int itemId,int userID);
    AuctionQueryResult createAuction(Map<String,String> auction);
}
