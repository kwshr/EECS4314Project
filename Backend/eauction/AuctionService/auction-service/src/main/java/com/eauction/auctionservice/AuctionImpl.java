package com.eauction.auctionservice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;

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
                String query = "SELECT WinnerId FROM Auctions WHERE ItemID = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setInt(1, itemId);
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            int winnerId = resultSet.getInt("WinnerId");
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
    }
    
