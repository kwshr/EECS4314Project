package com.eauction.auctionservice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class AuctionImpl implements Auction {

    private final DatabaseConnection databaseConnection;

    public AuctionImpl(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public AuctionQueryResult startAuction(int itemId) {
        if(itemId <=0 ){
            return new AuctionQueryResult(AuctionQueryResultStatus.ERROR,"Item id cannot be less than or equal to zero");
        }
        else{
            try (Connection connection = databaseConnection.connect()) {
                String query = "SELECT StartDateTime FROM Auctions WHERE ItemID = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setInt(1, itemId);
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            Timestamp startDateTime = resultSet.getTimestamp("StartDateTime");
                            LocalDateTime now = LocalDateTime.now();
                            LocalDateTime auctionStartDateTime = startDateTime.toLocalDateTime();
    
                            if (now.isAfter(auctionStartDateTime) || now.isEqual(auctionStartDateTime)) {
                                String updateSql = "UPDATE Auctions SET AuctionStatus = 'Active' WHERE ItemID = ?";
                                try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
                                    updateStatement.setInt(1, itemId);
                                    int rowsAffected = updateStatement.executeUpdate();
                                    if (rowsAffected > 0) {
                                        return new AuctionQueryResult(AuctionQueryResultStatus.SUCCESS, "Auction for itemID: " + itemId + " is now Active");
                                    } else {
                                        return new AuctionQueryResult(AuctionQueryResultStatus.ERROR, "Failed to update auction status for itemID: " + itemId);
                                    }
                                } catch (SQLException e) {
                                    throw new AuctionException("Error starting auction", e);
                                }
                            } else {
                                return new AuctionQueryResult(AuctionQueryResultStatus.ERROR, "Auction cannot be started before its scheduled start time");
                            }
                        } else {
                            return new AuctionQueryResult(AuctionQueryResultStatus.ERROR, "Auction not found for itemID: " + itemId);
                        }
                    }
                }
            } catch (SQLException e) {
                throw new AuctionException("Error starting auction", e);
            }
        }
    }

    @Override
    public AuctionQueryResult getRemainingTimeUpdate(int itemId) {
        try (Connection connection = databaseConnection.connect()) {
            String query = "SELECT EndDateTime FROM Auctions WHERE ItemID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, itemId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        Timestamp endDateTime = resultSet.getTimestamp("EndDateTime");
                        LocalDateTime now = LocalDateTime.now();
                        LocalDateTime auctionEndDateTime = endDateTime.toLocalDateTime();

                        if (now.isBefore(auctionEndDateTime)) {
                            Duration remainingTime = Duration.between(now, auctionEndDateTime);
                            long hours = remainingTime.toHours();
                            long minutes = remainingTime.toMinutesPart();
                            long seconds = remainingTime.toSecondsPart();

                            String message = String.format("Auction for itemID %d is active. Remaining time: %d hours, %d minutes, %d seconds",
                                    itemId, hours, minutes, seconds);
                            AuctionQueryResult result = new AuctionQueryResult(AuctionQueryResultStatus.SUCCESS, message);
                            result.setData(remainingTime);
                            return result;
                        } else {
                            return new AuctionQueryResult(AuctionQueryResultStatus.ERROR, "Auction for itemID " + itemId + " has already ended.");
                        }
                    } else {
                        return new AuctionQueryResult(AuctionQueryResultStatus.ERROR, "Auction not found for itemID: " + itemId);
                    }
                }
            }
        } catch (SQLException e) {
            throw new AuctionException("Error getting remaining time for auction", e);
        }
    }

    @Override
    public AuctionQueryResult endAuction(int itemId) {
        if(itemId <=0 ){
            return new AuctionQueryResult(AuctionQueryResultStatus.ERROR,"Item id cannot be less than or equal to zero");
        }
        else{
            try(Connection connection = databaseConnection.connect() ){
                String query = "SELECT EndDateTime FROM Auctions WHERE ItemID = ?";
                try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
                    preparedStatement.setInt(1,itemId);
                    try(ResultSet resultSet = preparedStatement.executeQuery()){
                        if(resultSet.next()){
                            Timestamp endDateTime = resultSet.getTimestamp("EndDateTime");
                            LocalDateTime now = LocalDateTime.now();
                            LocalDateTime auctionEndDateTime = endDateTime.toLocalDateTime();
                            if(now.isAfter(auctionEndDateTime) || now.isEqual(auctionEndDateTime)){
                                String updateSql = "UPDATE Auctions SET AuctionStatus = 'Ended' WHERE ItemID = ?";
                                try(PreparedStatement updateStatement = connection.prepareStatement(updateSql)){
                                    updateStatement.setInt(1,itemId);
                                    int rowsAffected = updateStatement.executeUpdate();
                                    if(rowsAffected>0){
                                        return new AuctionQueryResult(AuctionQueryResultStatus.SUCCESS, "Auction for itemID: " + itemId + " is now Ended");
                                    } else {
                                        return new AuctionQueryResult(AuctionQueryResultStatus.ERROR, "Failed to update auction status for itemID: " + itemId);
                                    } 
                                } catch (SQLException e) {
                                    throw new AuctionException("Error ending auction", e);
                                }
                            } else {
                                return new AuctionQueryResult(AuctionQueryResultStatus.ERROR, "Auction cannot be ended before its scheduled end time");
                            }
                        } else {
                            return new AuctionQueryResult(AuctionQueryResultStatus.ERROR, "Auction not found for itemID: " + itemId);
                        }
                    }
                }
            } catch (SQLException e) {
                throw new AuctionException("Error ending auction", e);
            }
        }
    }

        public AuctionQueryResult getWinner(int itemId){
            try (Connection connection = databaseConnection.connect()) {
                String query = "SELECT WinningBidder FROM Auctions WHERE ItemID = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setInt(1, itemId);
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            int winnerId = resultSet.getInt("WinningBidder");
                            AuctionQueryResult result = new AuctionQueryResult(AuctionQueryResultStatus.SUCCESS, "Winner ID retrieved successfully");
                            result.setData(winnerId);
                            return result;
                        } else {
                            return new AuctionQueryResult(AuctionQueryResultStatus.ERROR, "No winner found for item with ID: " + itemId);
                        }
                    }
                }
            } catch (SQLException e) {
                return new AuctionQueryResult(AuctionQueryResultStatus.ERROR, "Error retrieving winner ID for item with ID: " + itemId);
            }
        }

        @Override
        public AuctionQueryResult createAuction(Map<String,String> auction) {
            int itemId = Integer.valueOf(auction.get("itemId"));
            long currentPrice = Long.valueOf(auction.get("currentPrice"));
            String auctionType = auction.get("auctionType");
            if(itemId <=0 ){
                return new AuctionQueryResult(AuctionQueryResultStatus.INVALID_INPUT,"Item id cannot be less than or equal to zero");
            }
            else{
                try (Connection connection = databaseConnection.connect()) {
                    Random random = new Random();
                    int day = random.nextInt(4);
                    int hours = random.nextInt(24);
                    LocalDateTime starDateTime = LocalDateTime.now().plusDays(day).plusHours(hours);
                    LocalDateTime enDateTime = starDateTime.plusHours(1);
                    String query = "INSERT INTO Auctions (ItemID, StartDateTime, EndDateTime, CurrentPrice, AuctionType) VALUES (?, ?, ?, ?, ?)";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                        preparedStatement.setInt(1, itemId);
                        preparedStatement.setTimestamp(2, Timestamp.valueOf(starDateTime));
                        preparedStatement.setTimestamp(3, Timestamp.valueOf(enDateTime));
                        preparedStatement.setDouble(4, currentPrice);
                        preparedStatement.setString(5, auctionType);
                        int rowsAffected = preparedStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            return new AuctionQueryResult(AuctionQueryResultStatus.SUCCESS, "Auction added successfully");
                        } else {
                            return new AuctionQueryResult(AuctionQueryResultStatus.ERROR, "Error adding auction to the database");
                        }
                } 
            }
                catch (SQLException e) {
                    return new AuctionQueryResult(AuctionQueryResultStatus.ERROR, "Error adding auction to the database");
            }
        }
    }

        @Override
        public AuctionQueryResult getAuctionedItemDetails(int itemId) {
            try (Connection connection = databaseConnection.connect()) {
                String query = "SELECT * FROM Auctions WHERE ItemID = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setInt(1, itemId);
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            int auctionId = resultSet.getInt("AuctionID");
                            Timestamp startDateTime = resultSet.getTimestamp("StartDateTime");
                            Timestamp endDateTime = resultSet.getTimestamp("EndDateTime");
                            double currentPrice = resultSet.getDouble("CurrentPrice");
                            String auctionStatus = resultSet.getString("AuctionStatus");
                            String auctionType = resultSet.getString("AuctionType");
                            int winningBidderId = resultSet.getInt("WinningBidder");
                            Map<String, Object> auctionDetails = new HashMap<>();
                            auctionDetails.put("AuctionID", auctionId);
                            auctionDetails.put("ItemID", itemId);
                            auctionDetails.put("StartDateTime", startDateTime);
                            auctionDetails.put("EndDateTime", endDateTime);
                            auctionDetails.put("CurrentPrice", currentPrice);
                            auctionDetails.put("AuctionStatus", auctionStatus);
                            auctionDetails.put("AuctionType", auctionType);
                            auctionDetails.put("WinningBidder", winningBidderId);
        
                            AuctionQueryResult result = new AuctionQueryResult(AuctionQueryResultStatus.SUCCESS, "Auction details retrieved successfully");
                            result.setData(auctionDetails);
                            return result;
                        } else {
                            return new AuctionQueryResult(AuctionQueryResultStatus.ERROR, "No auction found for item with ID: " + itemId);
                        }
                    }
                }
            } catch (SQLException e) {
                return new AuctionQueryResult(AuctionQueryResultStatus.ERROR, "Error retrieving auction details for item with ID: " + itemId);
            }
        }

        @Override
        public AuctionQueryResult endAuction(int itemId, int userID) {
            try (Connection connection = databaseConnection.connect()) {
                String query = "UPDATE Auctions SET WinningBidder = ? WHERE ItemID = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setInt(1, userID);
                    preparedStatement.setInt(2, itemId);
                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        return new AuctionQueryResult(AuctionQueryResultStatus.SUCCESS, "Winner bidder update successful");
                    } else {
                        return new AuctionQueryResult(AuctionQueryResultStatus.NOT_FOUND, "Winner bidder update unsuccessful");
                    }
                }
            } catch (SQLException e) {
                return new AuctionQueryResult(AuctionQueryResultStatus.ERROR, "Failed to reset winnerbidde" + e.getMessage());
            }
        }
    
        }
        
    
