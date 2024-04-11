package com.eauction.sellerservice;

import org.springframework.stereotype.Service;

import com.common.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;

@Service
public class SellerImpl implements Seller {

    private final DatabaseConnection databaseConnection;

    public SellerImpl(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public SellerQueryResult addSellItems(Item item, int sellerId) {
        if(item.getAuctionType().equalsIgnoreCase("forward")){
            return addForwardItem(item, sellerId);
        }
        else if(item.getAuctionType().equalsIgnoreCase("dutch")){
            return addDutchItem(item, sellerId);
        }
        else{
            return new SellerQueryResult(SellerServiceQueryStatus.INVALID_INPUT, "Auction Type can only be forward or dutch. Please, try again!");
        }
    }

    private SellerQueryResult addForwardItem(Item item, int sellerId){
        String itemName = item.getItemName();
        String itemDescription = item.getItemDescription();
        String auctionType = item.getAuctionType();
        double price = item.getPrice();
        int shippingTime = item.getShippingTime();
        double shippingCost = item.getShippingCost();
        double expeditedShippingCost = item.getExpeditedShippingCost();
        double finalShippingCost = 0;

        if(itemName == null || itemDescription == null || auctionType == null || itemName == "" || itemDescription == "" || auctionType == "" ||
        price <= 0 || shippingTime <= 0 || shippingCost <= 0 || expeditedShippingCost <= 0 ||sellerId <= 0){
            return new SellerQueryResult(SellerServiceQueryStatus.INVALID_INPUT, "Invalid values provided, Please try again");
        }

        try (Connection connection = databaseConnection.connect()) {
            String query = "INSERT INTO Items " +
               "(ItemName, ItemDescription, AuctionType, Price, ShippingTime, ShippingCost, " +
               "ExpeditedShippingCost, FinalShippingCost, SellerID) " + 
               "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, itemName);
                preparedStatement.setString(2, itemDescription);
                preparedStatement.setString(3, auctionType);
                preparedStatement.setDouble(4, price);
                preparedStatement.setInt(5, shippingTime);
                preparedStatement.setDouble(6, shippingCost);
                preparedStatement.setDouble(7, expeditedShippingCost);
                preparedStatement.setDouble(8, finalShippingCost);
                preparedStatement.setInt(9, sellerId);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected == 0) {
                    return new SellerQueryResult(SellerServiceQueryStatus.ERROR, "Failed to add item: " + itemName);
                } else {
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if(generatedKeys.next()){
                    int itemId = generatedKeys.getInt(1);
                    SellerQueryResult sellerQueryResult = new SellerQueryResult(SellerServiceQueryStatus.SUCCESS, "Item added successfully: " + itemName);
                    sellerQueryResult.setData(itemId); 
                    return sellerQueryResult;
                }
                else{
                    return new SellerQueryResult(SellerServiceQueryStatus.ERROR, "No ID obtained for the newly added item: "+itemName);
                }
            }
        }
        } catch (Exception e) {
            return new SellerQueryResult(SellerServiceQueryStatus.ERROR, "Failed to add item: " + e.getMessage());
        }
    }

    private SellerQueryResult addDutchItem(Item item, int sellerId){
        String itemName = item.getItemName();
        String itemDescription = item.getItemDescription();
        String auctionType = item.getAuctionType();
        long price = item.getPrice();
        int shippingTime = item.getShippingTime();
        double shippingCost = item.getShippingCost();
        double expeditedShippingCost = item.getExpeditedShippingCost();
        double finalShippingCost = item.getFinalShippingCost();
        long dutchReservedPrice = item.getDutchReservedPrice();
        Time dutchEndTimer = item.getDutchEndTimer();
        int hours = dutchEndTimer.toLocalTime().getHour();
        int minutes = dutchEndTimer.toLocalTime().getMinute();
        int seconds = dutchEndTimer.toLocalTime().getSecond();
        long totalMilliseconds = (hours * 60 * 60 + minutes * 60 + seconds) * 1000;

        if(itemName == null || itemDescription == null || auctionType == null || itemName == "" || itemDescription == "" || auctionType == "" ||
        price <= 0 || shippingTime <= 0 || shippingCost <= 0 || expeditedShippingCost <= 0 ||
        finalShippingCost <= 0 || totalMilliseconds <=0 || totalMilliseconds >=30 * 60 * 1000 || sellerId <= 0){
            return new SellerQueryResult(SellerServiceQueryStatus.INVALID_INPUT, "Invalid values provided, Please try again");
        }

        try (Connection connection = databaseConnection.connect()) {
            String query = "INSERT INTO Items " +
                    "(ItemName, ItemDescription, AuctionType, Price, ShippingTime, ShippingCost, " +
                    "ExpeditedShippingCost, FinalShippingCost, DutchReservedPrice, " +
                    "DutchEndTimer, SellerID) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, itemName);
                preparedStatement.setString(2, itemDescription);
                preparedStatement.setString(3, auctionType);
                preparedStatement.setLong(4, price);
                preparedStatement.setInt(5, shippingTime);
                preparedStatement.setDouble(6, shippingCost);
                preparedStatement.setDouble(7, expeditedShippingCost);
                preparedStatement.setDouble(8, finalShippingCost);
                preparedStatement.setLong(9, dutchReservedPrice);
                preparedStatement.setTime(10, dutchEndTimer);
                preparedStatement.setInt(11, sellerId);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected == 0) {
                    return new SellerQueryResult(SellerServiceQueryStatus.ERROR, "Failed to add item: " + itemName);
                } else {
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if(generatedKeys.next()){
                    int itemId = generatedKeys.getInt(1);
                    SellerQueryResult sellerQueryResult = new SellerQueryResult(SellerServiceQueryStatus.SUCCESS, "Item added successfully: " + itemName);
                    sellerQueryResult.setData(itemId); 
                    return sellerQueryResult;
                    } else{
                        return new SellerQueryResult(SellerServiceQueryStatus.ERROR, "No ID obtained for the newly added item");
                    }
                }
            }
        } catch (Exception e) {
            return new SellerQueryResult(SellerServiceQueryStatus.ERROR, "Failed to add item: " + e.getMessage());
        }
    }

    @Override
    public SellerQueryResult updateDutchAuctionprice(int itemId, double newPrice) {
        if(itemId<=0 || newPrice <=0 ){
            return new SellerQueryResult(SellerServiceQueryStatus.INVALID_INPUT, "ItemId and newPrice cannot be less than 1. Please, try again");
        }
        try (Connection connection = databaseConnection.connect()) {
            String query = "UPDATE Auctions SET CurrentPrice = ?, WinningBidder = NULL WHERE ItemID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setDouble(1, newPrice);
                preparedStatement.setInt(2, itemId);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected == 0) {
                    return new SellerQueryResult(SellerServiceQueryStatus.NOT_FOUND, "Item not found");
                } else {
                    return new SellerQueryResult(SellerServiceQueryStatus.SUCCESS, "Dutch auction price updated successfully");
                }
            }
        } catch (Exception e) {
            return new SellerQueryResult(SellerServiceQueryStatus.ERROR, "Failed to update Dutch auction price: " + e.getMessage());
        }
    }
}
