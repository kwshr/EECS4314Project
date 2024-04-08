package com.eauction.shippingservice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class ShippingImpl implements Shipping{

    private DatabaseConnection databaseConnection;

    public ShippingImpl(DatabaseConnection databaseConnection){
        this.databaseConnection = databaseConnection;
    }

    @Override
    public ShippingQueryResult calculateShippingCost(int itemId) {
        if(itemId<=0){
            return new ShippingQueryResult(ShippingQueryResultStatus.INVALID_INPUT, "ItemId cannot be less than 1");
        }
        try (Connection connection = databaseConnection.connect()) {
            if (!itemExists(connection, itemId)) {
                return new ShippingQueryResult(ShippingQueryResultStatus.ERROR, "Item not found for ID: " + itemId);
            }
            Map<String, Object> itemInfo = getItemInformation(connection, itemId);
            double shippingCost = calculateShippingCost(itemInfo);
            updateFinalShippingCost(connection, itemId, shippingCost);
            ShippingQueryResult shippingQueryResult = new ShippingQueryResult(ShippingQueryResultStatus.SUCCESS, "Shipping cost calculated: " + shippingCost);
            shippingQueryResult.setData(shippingCost);
            return shippingQueryResult;
        } catch (SQLException e) {
            return new ShippingQueryResult(ShippingQueryResultStatus.ERROR, "Error calculating or updating shipping cost");
        }
    }

    private void updateFinalShippingCost(Connection connection, int itemId, double shippingCost) throws SQLException {
        String updateQuery = "UPDATE Items SET FinalShippingCost = ? WHERE ItemID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setDouble(1, shippingCost);
            preparedStatement.setInt(2, itemId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected <= 0) {
                throw new SQLException("Failed to update FinalShippingCost for item with ID: " + itemId);
            }
        }
    }

    private Map<String, Object> getItemInformation(Connection connection, int itemId) throws SQLException {
        String query = "SELECT ExpeditedShipping, ExpeditedShippingCost, ShippingCost FROM Items WHERE ItemID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, itemId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Map<String, Object> itemInfo = new HashMap<>();
                    itemInfo.put("ExpeditedShipping", resultSet.getBoolean("ExpeditedShipping"));
                    itemInfo.put("ExpeditedShippingCost", resultSet.getDouble("ExpeditedShippingCost"));
                    itemInfo.put("NormalShippingCost", resultSet.getDouble("ShippingCost"));
                    return itemInfo;
                } else {
                    throw new SQLException("Item not found for itemId: " + itemId);
                }
            }
        }
    }
    
    private Double calculateShippingCost(Map<String, Object> itemInfo) {
        boolean expeditedShipping = (boolean) itemInfo.get("ExpeditedShipping");
        double expeditedShippingCost = (double) itemInfo.get("ExpeditedShippingCost");
        double normalShippingCost = (double) itemInfo.get("NormalShippingCost");
    
        return expeditedShipping ? expeditedShippingCost : normalShippingCost;
    }

    @Override
    public ShippingQueryResult setExpeditedShipping(int itemId){
        if(itemId<=0){
            return new ShippingQueryResult(ShippingQueryResultStatus.INVALID_INPUT, "ItemId cannot be less than 1");
        }
        try (Connection connection = databaseConnection.connect()) {
            if (!itemExists(connection, itemId)) {
                return new ShippingQueryResult(ShippingQueryResultStatus.ERROR, "Item not found for ID: " + itemId);
            }
            String updateQuery = "UPDATE Items SET ExpeditedShipping = true WHERE ItemID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setInt(1, itemId);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                     return new ShippingQueryResult(ShippingQueryResultStatus.SUCCESS, "Expedited shipping set to true for item with ID: " + itemId);
                    } else {
                        return new ShippingQueryResult(ShippingQueryResultStatus.ERROR, "Failed to update expedited shipping for item with ID: " + itemId);
                    }
                }
            } catch (SQLException e) {
                return new ShippingQueryResult(ShippingQueryResultStatus.ERROR, "Error setting expedited shipping");
            }
    }

    private boolean itemExists(Connection connection, int itemId) throws SQLException {
    String checkQuery = "SELECT 1 FROM Items WHERE ItemID = ?";
    try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
        checkStatement.setInt(1, itemId);
        try (ResultSet resultSet = checkStatement.executeQuery()) {
            return resultSet.next();
        }
    }
}

    @Override
    public ShippingQueryResult displayShippingDetails(int itemId) {
        if(itemId<=0){
            return new ShippingQueryResult(ShippingQueryResultStatus.INVALID_INPUT, "ItemId cannot be less than 1");
        }
        try (Connection connection = databaseConnection.connect()) {
            if (!itemExists(connection, itemId)) {
                return new ShippingQueryResult(ShippingQueryResultStatus.ERROR, "Item not found for ID: " + itemId);
            }
            String updateQuery = "SELECT ShippingTime FROM Items WHERE ItemID =?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setInt(1, itemId);
                
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int shippingTime = resultSet.getInt("ShippingTime");
                        ShippingQueryResult result = new ShippingQueryResult(ShippingQueryResultStatus.SUCCESS, "Retrieved shipping time successfully for item with ID: " + itemId);
                        result.setData(shippingTime);
                        return result;
                    } else {
                        return new ShippingQueryResult(ShippingQueryResultStatus.ERROR, "Failed to retrieve the shipping time for item with ID: " + itemId);
                    }
                }
            }
         } catch (SQLException e) {
                return new ShippingQueryResult(ShippingQueryResultStatus.ERROR, "Error retrieving shipping time");
            }
    }
    
}
