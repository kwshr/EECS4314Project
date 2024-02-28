package com.eauction.sellerservice;

import org.springframework.stereotype.Service;

import com.common.Item;

@Service
public class SellerImpl implements Seller {

    @Override
    public SellerQueryResult addSellItems(Item item) {
        return new SellerQueryResult(null, null);
    }

    @Override
    public SellerQueryResult updateDutchAuctionprice(int itemId, double newprice) {
        return new SellerQueryResult(null, null);
    }
    
}
