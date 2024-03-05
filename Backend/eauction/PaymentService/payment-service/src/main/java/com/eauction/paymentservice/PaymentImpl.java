package com.eauction.paymentservice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import com.common.*;
import org.springframework.stereotype.Service;

@Service
public class PaymentImpl implements Payment{

    private DatabaseConnection databaseConnection;

    public PaymentImpl(DatabaseConnection databaseConnection){
        this.databaseConnection = databaseConnection;
    }

    @Override
    public PaymentQueryResult processPayment(String userName,int itemId) {
        try(Connection connection = databaseConnection.connect()){
            String query = "INSERT INTO Payments (AuctionID, PayerID, Amount) " +
            "SELECT a.AuctionID, u.UserID, a.CurrentPrice " +
            "FROM Auctions a " +
            "JOIN Users u ON u.UserName = ? " +
            "WHERE a.ItemID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, userName);
                preparedStatement.setInt(2, itemId);
                int rowsAffected = preparedStatement.executeUpdate();
                if(rowsAffected>0){
                    String updateQuery = "UPDATE Auctions SET AuctionStatus = 'Paid' WHERE ItemID = ?";
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                        updateStatement.setInt(1, itemId);
                        updateStatement.executeUpdate();
                    }
                    return new PaymentQueryResult(PaymentQueryResultStatus.PAYMENT_SUCCESS, "Payment Successfull");
                }
                else{
                    return new PaymentQueryResult(PaymentQueryResultStatus.PAYMENT_FAILED, "Payment Failed");
                }
            }
        } catch (Exception e){
            return new PaymentQueryResult(PaymentQueryResultStatus.PAYMENT_FAILED, e.getMessage());
        }
    }
    
}
