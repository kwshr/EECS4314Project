package com.eauction.sellerservice;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.common.Item;

@Service
public class SellerImpl implements Seller {

    private final JdbcTemplate jdbcTemplate;

    public SellerImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public SellerQueryResult addSellItems(Item item) {
         String itemName = item.getItemName();
         String itemDescription = item.getItemDescription();
         String auctionType = item.getAuctionType();
         long price = item.getPrice();
         int shippingTime = item.getShippingTime();
         double shippingCost = item.getShippingCost();
         double expeditedShippingCost = item.getExpeditedShippingCost();
         double finalShippingCost = item.getFinalShippingCost();
         int fixedTimeLimit = item.getFixedTimeLimit();
         long dutchReservedPrice = item.getDutchReservedPrice();
         long dutchDecrementAmount = item.getDutchDecrementAmount();
         int dutchDecrementTimeInterval = item.getDutchDecrementTimeInterval();
         int sellerId = item.getSellerId();
        try{
            String query = "INSERT INTO Items " +
            "(ItemName, ItemDescription, AuctionType, Price, ShippingTime, ShippingCost, " +
            "ExpeditedShippingCost, FinalShippingCost, FixedTimeLimit, DutchReservedPrice, " +
            "DutchDecrementAmount, DutchDecrementTimeInterval, SellerID) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            int rowsAffected = jdbcTemplate.update(query,itemName, itemDescription, auctionType, 
            price, shippingTime, shippingCost,expeditedShippingCost, finalShippingCost, fixedTimeLimit,
            dutchReservedPrice,dutchDecrementAmount, dutchDecrementTimeInterval, sellerId);
            if(rowsAffected == 0){
                return new SellerQueryResult(SellerServiceQueryStatus.ERROR, "Failed to add item :"+ itemName);
            }
            else{
                return new SellerQueryResult(SellerServiceQueryStatus.SUCCESS, "Item added successfully :"+ itemName);
            }
        } catch (Exception e){
            return new SellerQueryResult(SellerServiceQueryStatus.ERROR, "Failed to add item :"+ e.getMessage());
        }
    }

    @Override
    public SellerQueryResult updateDutchAuctionprice(int itemId, double newprice) {
        try{
            String query = "UPDATE Auctions SET CurrentPrice = ? WHERE ItemID = ?";
            int rowsAffected = jdbcTemplate.update(query, newprice,itemId);
            if(rowsAffected == 0){
                return new SellerQueryResult(SellerServiceQueryStatus.NOT_FOUND, "Item not found");
            }
            else {
                return new SellerQueryResult(SellerServiceQueryStatus.SUCCESS, "Dutch auction price updated successfully");
            }
        } catch (Exception e){
            return new SellerQueryResult(SellerServiceQueryStatus.ERROR, "Failed to update dutch auction price");
        }
    }
    
}
