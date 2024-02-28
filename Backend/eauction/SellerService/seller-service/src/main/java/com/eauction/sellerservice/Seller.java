package com.eauction.sellerservice;

import com.common.Item;

public interface Seller {
    void uploadSellItems(Item item);
    public void updateDutchAuctionprice(int itemId, double newprice);
}
