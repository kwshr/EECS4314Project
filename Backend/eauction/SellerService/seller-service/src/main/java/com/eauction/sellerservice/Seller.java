package com.eauction.sellerservice;

public interface Seller {
    public void uploadSellItems(ItemDetails details);
    public void updateDutchAuctionprice(int itemId, double newprice);
}
