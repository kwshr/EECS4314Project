package com.eauction.authorizationservice;

import com.common.*;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Service
public class AuthorizationImpl implements Authorization {

    private final DatabaseConnection databaseConnection;

    public AuthorizationImpl(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public AuthorizationQueryResult signUp(User user, String accountType) {
        if(accountType.equalsIgnoreCase("user")){
            return signUpUser(user);
        }
        else if(accountType.equalsIgnoreCase("seller")){
            return signUpSeller(user);
        }
        else{
            return new AuthorizationQueryResult(AuthorizationQueryResultStatus.INVALID_INPUT, "Invalid account type chosen");
        }
    }

    private AuthorizationQueryResult signUpSeller(User user){
        String userName = user.getUserName();
        String password = user.getPassword();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        if((userName == null || password == null || firstName == null || lastName == null || userName == "" || password == "" || 
        firstName == "" || lastName == "" )){
            return new AuthorizationQueryResult(AuthorizationQueryResultStatus.INVALID_INPUT, "Signup values cannot be null or empty. Please, try again");
        }
        try {
            try (Connection connection = databaseConnection.connect()) {
                String query = "INSERT INTO Sellers (sellerUsername, password, firstName, lastName) " +
                        "VALUES (?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, userName);
                    preparedStatement.setString(2, password);
                    preparedStatement.setString(3, firstName);
                    preparedStatement.setString(4, lastName);
                    preparedStatement.executeUpdate();
                }
            }
            return new AuthorizationQueryResult(AuthorizationQueryResultStatus.SUCCESS, "Seller signed up successfully");
        } catch (Exception e) {
            return new AuthorizationQueryResult(AuthorizationQueryResultStatus.ERROR, "Failed to sign up seller: " + e.getMessage());
        }
    }

    private AuthorizationQueryResult signUpUser(User user){
        String userName = user.getUserName();
        String password = user.getPassword();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        ShippingAddress shippingAddress = user.getShippingAddress();
        String streetName = shippingAddress.getStreetName();
        int streetNumber = shippingAddress.getStreetNumber();
        String city = shippingAddress.getCity();
        String country = shippingAddress.getCountry();
        String postalCode = shippingAddress.getPostalCode();
        if((userName == null || password == null || firstName == null || lastName == null || streetName == null ||
        streetNumber <=0 || city == null || country == null || postalCode == null ||userName == "" || password == "" || 
        firstName == "" || lastName == "" || streetName == "" || city == "" || country == "" || postalCode == "" )){
            return new AuthorizationQueryResult(AuthorizationQueryResultStatus.INVALID_INPUT, "Signup values cannot be null or empty. Please, try again");
        }
        try {
            try (Connection connection = databaseConnection.connect()) {
                String query = "INSERT INTO Users (userName, password, firstName, lastName, streetName, streetNumber, city, country, postalCode) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, userName);
                    preparedStatement.setString(2, password);
                    preparedStatement.setString(3, firstName);
                    preparedStatement.setString(4, lastName);
                    preparedStatement.setString(5, streetName);
                    preparedStatement.setInt(6, streetNumber);
                    preparedStatement.setString(7, city);
                    preparedStatement.setString(8, country);
                    preparedStatement.setString(9, postalCode);

                    preparedStatement.executeUpdate();
                }
            }
            return new AuthorizationQueryResult(AuthorizationQueryResultStatus.SUCCESS, "User signed up successfully");
        } catch (Exception e) {
            return new AuthorizationQueryResult(AuthorizationQueryResultStatus.ERROR, "Failed to sign up user: " + e.getMessage());
        }
    }

    @Override
    public AuthorizationQueryResult signIn(String userName, String password,String accountType){
        if(accountType.equalsIgnoreCase("user")){
            return signInUser(userName,password);
        }
        else if(accountType.equalsIgnoreCase("seller")){
            return signInSeller(userName,password);
        }
        else{
            return new AuthorizationQueryResult(AuthorizationQueryResultStatus.INVALID_INPUT, "Invalid account type chosen");
        }
    }

    public AuthorizationQueryResult signInSeller(String userName, String password) {
        if (userName == null || password == null || userName == "" || password == "")
            return new AuthorizationQueryResult(AuthorizationQueryResultStatus.INVALID_INPUT, "Invalid parameters: userName and password must not be null");
        else {
            try (Connection connection = databaseConnection.connect()) {
                String query = "SELECT SellerID FROM Sellers WHERE SellerUsername = ? AND password = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, userName);
                    preparedStatement.setString(2, password);

                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            return new AuthorizationQueryResult(AuthorizationQueryResultStatus.SUCCESS, "Sign-in successful");
                        } else {
                            return new AuthorizationQueryResult(AuthorizationQueryResultStatus.NOT_FOUND, "Invalid username or password");
                        }
                    }
                }
            } catch (SQLException e) {
                return new AuthorizationQueryResult(AuthorizationQueryResultStatus.ERROR, "Failed to sign in: " + e.getMessage());
            }
        }
    }

    public AuthorizationQueryResult signInUser(String userName, String password) {
        if (userName == null || password == null || userName == "" || password == "")
            return new AuthorizationQueryResult(AuthorizationQueryResultStatus.INVALID_INPUT, "Invalid parameters: userName and password must not be null");
        else {
            try (Connection connection = databaseConnection.connect()) {
                String query = "SELECT userId FROM Users WHERE userName = ? AND password = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, userName);
                    preparedStatement.setString(2, password);

                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            return new AuthorizationQueryResult(AuthorizationQueryResultStatus.SUCCESS, "Sign-in successful");
                        } else {
                            return new AuthorizationQueryResult(AuthorizationQueryResultStatus.NOT_FOUND, "Invalid username or password");
                        }
                    }
                }
            } catch (SQLException e) {
                return new AuthorizationQueryResult(AuthorizationQueryResultStatus.ERROR, "Failed to sign in: " + e.getMessage());
            }
        }
    }

    @Override
    public AuthorizationQueryResult passwordReset(String userName, String newPassword) {
        if (userName == null || newPassword == null || userName == "" || newPassword == ""){
            return new AuthorizationQueryResult(AuthorizationQueryResultStatus.INVALID_INPUT, "Invalid parameters: userName and password must not be null");
        }
        try (Connection connection = databaseConnection.connect()) {
            String query = "UPDATE Users SET password = ? WHERE userName = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, newPassword);
                preparedStatement.setString(2, userName);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    return new AuthorizationQueryResult(AuthorizationQueryResultStatus.SUCCESS, "Password reset successful");
                } else {
                    return new AuthorizationQueryResult(AuthorizationQueryResultStatus.NOT_FOUND, "User not found with the specified username");
                }
            }
        } catch (SQLException e) {
            return new AuthorizationQueryResult(AuthorizationQueryResultStatus.ERROR, "Failed to reset password" + e.getMessage());
        }
    }

    @Override
    public AuthorizationQueryResult getUserDetails(int userId){
        if(userId<=0){
            return new AuthorizationQueryResult(AuthorizationQueryResultStatus.INVALID_INPUT, "Invalid parameters: userName must not be null"); 
        }
        try (Connection connection = databaseConnection.connect()){
            String sql = "SELECT UserName, FirstName, LastName, StreetName, StreetNumber, City, Country, PostalCode " +
                         "FROM Users " +
                         "WHERE UserID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, userId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    Map<String, String> userDetails = new HashMap<String,String>();
                    if (resultSet.next()) {
                        userDetails.put("UserName", resultSet.getString("UserName"));
                        userDetails.put("FirstName", resultSet.getString("FirstName"));
                        userDetails.put("LastName",resultSet.getString("LastName"));
                        userDetails.put("StreetName", resultSet.getString("StreetName"));
                        userDetails.put("StreetNumber",resultSet.getString("StreetNumber"));
                        userDetails.put("City",resultSet.getString("City"));
                        userDetails.put("Country",resultSet.getString("Country"));
                        userDetails.put("PostalCode",resultSet.getString("PostalCode"));
                        AuthorizationQueryResult result = new AuthorizationQueryResult(AuthorizationQueryResultStatus.SUCCESS, "User's details fetched successfully");
                        result.setData(userDetails);
                        return result;
                    } else {
                        return new AuthorizationQueryResult(AuthorizationQueryResultStatus.NOT_FOUND, "Could not retrive the User details. Please double-check the UserId");
                    }
                }
            }
        } catch (SQLException e) {
            return new AuthorizationQueryResult(AuthorizationQueryResultStatus.ERROR, "Failed to fetch Users details" + e.getMessage());
        }
    }

    @Override
    public AuthorizationQueryResult getUserId(String userName){
        try (Connection connection = databaseConnection.connect()){
            String sql = "SELECT UserID " +
                         "FROM Users " +
                         "WHERE UserName = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, userName);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    Map<String, String> userDetails = new HashMap<String,String>();
                    if (resultSet.next()) {
                        userDetails.put("UserId", resultSet.getString("UserId"));
                        AuthorizationQueryResult result = new AuthorizationQueryResult(AuthorizationQueryResultStatus.SUCCESS, "User's id fetched successfully");
                        result.setData(userDetails);
                        return result;
                    } else {
                        return new AuthorizationQueryResult(AuthorizationQueryResultStatus.NOT_FOUND, "Could not retrive the User id. Please double-check the UserId");
                    }
                }
            }
        } catch (SQLException e) {
            return new AuthorizationQueryResult(AuthorizationQueryResultStatus.ERROR, "Failed to fetch Users details" + e.getMessage());
        }
    }

    @Override
    public AuthorizationQueryResult getSellerId(String userName){
        try (Connection connection = databaseConnection.connect()){
            String sql = "SELECT SellerID " +
                         "FROM Sellers " +
                         "WHERE SellerUsername = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, userName);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    Map<String, String> userDetails = new HashMap<String,String>();
                    if (resultSet.next()) {
                        userDetails.put("SellerID", resultSet.getString("SellerID"));
                        AuthorizationQueryResult result = new AuthorizationQueryResult(AuthorizationQueryResultStatus.SUCCESS, "Seller's id fetched successfully");
                        result.setData(userDetails);
                        return result;
                    } else {
                        return new AuthorizationQueryResult(AuthorizationQueryResultStatus.NOT_FOUND, "Could not retrive the Seller.");
                    }
                }
            }
        } catch (SQLException e) {
            return new AuthorizationQueryResult(AuthorizationQueryResultStatus.ERROR, "Failed to fetch seller details" + e.getMessage());
        }
    }
}
