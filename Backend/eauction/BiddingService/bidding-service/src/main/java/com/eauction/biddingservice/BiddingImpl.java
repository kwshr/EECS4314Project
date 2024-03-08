package com.eauction.biddingservice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

@Service
public class BiddingImpl implements Bidding {

    private final DatabaseConnection databaseConnection;

    public BiddingImpl(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public BiddingQueryResult placeBid(int itemId, double newPrice, String userName) {
        if(itemId <=0 || newPrice<=0 || userName==null){
            return new BiddingQueryResult(BiddingQueryResultStatus.INVALID_INPUT, "Please enter the correct values");
        }
        else{
            try (Connection connection = databaseConnection.connect()) {
                int userId = getUserIdByUserName(connection, userName);
                String query = "UPDATE Auctions SET CurrentPrice = ?, WinningBidder = ? WHERE ItemID = ?";
                try (PreparedStatement updateStatement = connection.prepareStatement(query)) {
                    updateStatement.setDouble(1, newPrice);
                    updateStatement.setInt(2, userId);
                    updateStatement.setInt(3, itemId);
                    int rowsAffected = updateStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        return new BiddingQueryResult(BiddingQueryResultStatus.SUCCESS, "Bid placed successfully for itemID: " + itemId);
                    } else {
                        return new BiddingQueryResult(BiddingQueryResultStatus.ERROR, "Failed to place bid for itemID: " + itemId);
                    }
                }
            } catch (SQLException e) {
                return new BiddingQueryResult(BiddingQueryResultStatus.ERROR, "Failed to place bid for itemID: " + itemId + e.getMessage());
            }
        }
    }

    private int getUserIdByUserName(Connection connection, String userName) throws SQLException {
        String query = "SELECT UserID FROM Users WHERE UserName = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() ? resultSet.getInt("UserID") : -1;
            }
        }
    }
    
}
