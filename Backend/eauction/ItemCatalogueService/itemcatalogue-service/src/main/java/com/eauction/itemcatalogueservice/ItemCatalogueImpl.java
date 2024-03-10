package com.eauction.itemcatalogueservice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.common.Item;

@Service
public class ItemCatalogueImpl implements ItemCatalogue{

    private final DatabaseConnection databaseConnection;

    public ItemCatalogueImpl(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public  ItemCatalogueQueryResult Search(String keyword) {
        if(keyword == null){
            return new ItemCatalogueQueryResult(ItemCatalogueQueryResultStatus.NO_INPUT, "Please enter a keyword");
        }
        else{
            try (Connection connection = databaseConnection.connect()) {
                String query = "SELECT * FROM Items WHERE ItemName LIKE ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, "%" + keyword + "%");
                    ResultSet resultSet = preparedStatement.executeQuery();
            
                    List<Item> items = new ArrayList<>();
                    while (resultSet.next()) {
                        Item item = new Item();
                        item.setItemId(resultSet.getInt("ItemID"));
                        item.setItemName(resultSet.getString("ItemName"));
                        item.setItemDescription(resultSet.getString("ItemDescription"));
                        item.setAuctionType(resultSet.getString("AuctionType"));
                        item.setPrice(resultSet.getLong("Price"));
                        item.setShippingTime(resultSet.getInt("ShippingTime"));
                        item.setShippingCost(resultSet.getDouble("ShippingCost"));
                        item.setExpeditedShippingCost(resultSet.getDouble("ExpeditedShippingCost"));
                        item.setFinalShippingCost(resultSet.getDouble("FinalShippingCost"));
                        item.setSellerId(resultSet.getInt("SellerID"));
                        items.add(item);
                    }
                    ItemCatalogueQueryResult result = new ItemCatalogueQueryResult(ItemCatalogueQueryResultStatus.SUCCESS, "Search successful");
                    if(items.equals(null))
                    result.setData("No Items found for the keyword: "+keyword);
                    else
                    result.setData(items);
                    return result;
                }
            }            
            catch (SQLException e) {
                return new ItemCatalogueQueryResult(ItemCatalogueQueryResultStatus.ERROR, "Failed to execute search: " + e.getMessage());
            }
        }
    }

    @Override
    public ItemCatalogueQueryResult getAuctionedItems() {
        try (Connection connection = databaseConnection.connect()) {
            String query = "SELECT * FROM Auctions WHERE AuctionStatus IN ('NotStarted', 'Active')";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();
    
                Map<Integer, Map<String, Object>> auctions = new HashMap<>();
                while (resultSet.next()) {
                    int auctionID = resultSet.getInt("AuctionID");
                    Map<String, Object> auctionData = new HashMap<>();
                    auctionData.put("ItemID", resultSet.getInt("ItemID"));
                    auctionData.put("StartDateTime", resultSet.getTimestamp("StartDateTime").toLocalDateTime());
                    auctionData.put("EndDateTime", resultSet.getTimestamp("EndDateTime").toLocalDateTime());
                    auctionData.put("CurrentPrice", resultSet.getDouble("CurrentPrice"));
                    auctionData.put("AuctionStatus", resultSet.getString("AuctionStatus"));
                    auctionData.put("AuctionType", resultSet.getString("AuctionType"));
                    auctions.put(auctionID, auctionData);
                }
                ItemCatalogueQueryResult result = new ItemCatalogueQueryResult(ItemCatalogueQueryResultStatus.SUCCESS, "Retrieved active auctions successfully");
                result.setData(auctions);
                return result;
            }
        } catch (SQLException e) {
            return new ItemCatalogueQueryResult(ItemCatalogueQueryResultStatus.ERROR, "Failed to retrieve active auctions: " + e.getMessage());
        }
    }

    @Override
    public ItemCatalogueQueryResult getItem(int itemId) {
        if(itemId <= 0){
            return new ItemCatalogueQueryResult(ItemCatalogueQueryResultStatus.NO_INPUT, "Itemid should be more than 0");
        }
        try (Connection connection = databaseConnection.connect()) {
            String query = "SELECT * FROM Items WHERE ItemID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, itemId);
                ResultSet resultSet = preparedStatement.executeQuery();
    
                if (resultSet.next()) {
                    Item item = new Item();
                    item.setItemId(resultSet.getInt("ItemID"));
                    item.setItemName(resultSet.getString("ItemName"));
                    item.setItemDescription(resultSet.getString("ItemDescription"));
                    item.setAuctionType(resultSet.getString("AuctionType"));
                    item.setPrice(resultSet.getLong("Price"));
                    item.setShippingTime(resultSet.getInt("ShippingTime"));
                    item.setShippingCost(resultSet.getDouble("ShippingCost"));
                    item.setExpeditedShippingCost(resultSet.getDouble("ExpeditedShippingCost"));
                    item.setFinalShippingCost(resultSet.getDouble("FinalShippingCost"));
                    item.setSellerId(resultSet.getInt("SellerID"));
    
                    ItemCatalogueQueryResult result = new ItemCatalogueQueryResult(ItemCatalogueQueryResultStatus.SUCCESS, "Item retrieved successfully");
                    result.setData(item);
                    return result;
                } else {
                    return new ItemCatalogueQueryResult(ItemCatalogueQueryResultStatus.NOT_FOUND, "Item not found");
                }
            }
        } catch (SQLException e) {
            return new ItemCatalogueQueryResult(ItemCatalogueQueryResultStatus.ERROR, "Failed to retrieve item: " + e.getMessage());
        }
    }    
}
