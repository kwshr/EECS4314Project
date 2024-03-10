package com.eauction.orderprocessingservice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class OrderProcessingImpl implements OrderProcessing {

    private DatabaseConnection databaseConnection;

    public OrderProcessingImpl(DatabaseConnection databaseConnection){
        this.databaseConnection = databaseConnection;
    }

    @Override
    public OrderProcessingQueryResult generateReceipt(int itemId, String userName) {
        if(itemId<=0 || userName == null){
            return new OrderProcessingQueryResult(OrderProcessingQueryResultStatus.INVALID_INPUT, "ItemId should be more than one and userName cannot be null");
        }
        try (Connection connection = databaseConnection.connect()) {
            Map<String, String> userInfo = getUserInformation(connection, userName);
            Double currentPrice = getCurrentPrice(connection, itemId);
            Double finalShippingCost = getFinalShippingCost(connection, itemId);
            Double totalPrice = currentPrice+finalShippingCost;
            Map<String, String> receiptInfo = new HashMap<>();
            receiptInfo.put("FirstName", userInfo.get("FirstName"));
            receiptInfo.put("LastName", userInfo.get("LastName"));
            receiptInfo.put("StreetName", userInfo.get("StreetName"));
            receiptInfo.put("StreetNumber", userInfo.get("StreetNumber"));
            receiptInfo.put("City", userInfo.get("City"));
            receiptInfo.put("Country", userInfo.get("Country"));
            receiptInfo.put("PostalCode", userInfo.get("PostalCode"));
            receiptInfo.put("TotalPaid", totalPrice.toString());
            OrderProcessingQueryResult result = new OrderProcessingQueryResult(OrderProcessingQueryResultStatus.SUCCESS, "Receipt generated successfully");
            result.setData(receiptInfo);  
            return result;   
            } catch (Exception e) {
            return new OrderProcessingQueryResult(OrderProcessingQueryResultStatus.ERROR, "Error generating receipt");
        }
    }

    private Map<String, String> getUserInformation(Connection connection, String userName) throws Exception {
        String query = "SELECT FirstName, LastName, StreetName, StreetNumber, City, Country, PostalCode FROM Users WHERE UserName = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Map<String, String> userInfo = new HashMap<>();
                    userInfo.put("FirstName", resultSet.getString("FirstName"));
                    userInfo.put("LastName", resultSet.getString("LastName"));
                    userInfo.put("StreetName", resultSet.getString("StreetName"));
                    userInfo.put("StreetNumber", resultSet.getString("StreetNumber"));
                    userInfo.put("City", resultSet.getString("City"));
                    userInfo.put("Country", resultSet.getString("Country"));
                    userInfo.put("PostalCode", resultSet.getString("PostalCode"));
                    return userInfo;
                } else {
                    throw new Exception("User not found for userName: " + userName);
                }
            }
        }
    }

    private Double getCurrentPrice(Connection connection, int itemId) throws Exception {
        String query = "SELECT CurrentPrice FROM Auctions WHERE ItemID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, itemId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("CurrentPrice");
                } else {
                    throw new Exception("Item not found for itemId: " + itemId);
                }
            }
        }
    }  
    
    private Double getFinalShippingCost(Connection connection, int itemId) throws Exception {
        String query = "SELECT FinalShippingCost FROM Items WHERE ItemID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, itemId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("FinalShippingCost");
                } else {
                    throw new Exception("Item not found for itemId: " + itemId);
                }
            }
        }
    } 

    @Override
    public OrderProcessingQueryResult updateItems(int itemId) {
        if(itemId<=0){
            return new OrderProcessingQueryResult(OrderProcessingQueryResultStatus.ERROR,"ItemId is null");
        }
        else{
            try (Connection connection = databaseConnection.connect()) {
                String updateSql = "UPDATE Auctions SET AuctionStatus = 'Paid' WHERE ItemID = ?";
                try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
                    updateStatement.setInt(1, itemId);
                    int rowsAffected = updateStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        return new OrderProcessingQueryResult(OrderProcessingQueryResultStatus.SUCCESS, "Auction for itemID: " + itemId + " is now marked as Paid");
                    } else {
                        return new OrderProcessingQueryResult(OrderProcessingQueryResultStatus.ERROR, "Failed to update auction status to 'Paid' for itemID: " + itemId);
                    }
                }
            } catch (SQLException e) {
                return new OrderProcessingQueryResult(OrderProcessingQueryResultStatus.ERROR,"Error updating auction status to 'Paid'"+ e.getMessage());
            }
        }
    }
}
