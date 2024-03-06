package com.eauction.authorizationservice;

import com.common.*;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class AuthorizationImpl implements Authorization {

    private final DatabaseConnection databaseConnection;

    public AuthorizationImpl(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public AuthorizationQueryResult SignUp(User user) {
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
            return new AuthorizationQueryResult(AuthorizationQueryResultStatus.ERROR, "Signup values cannot be null or empty. Please, try again");
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
    public AuthorizationQueryResult SignIn(String userName, String password) {
        if (userName == null || password == null || userName == "" || password == "")
            return new AuthorizationQueryResult(AuthorizationQueryResultStatus.ERROR, "Invalid parameters: userName and password must not be null");
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
    public AuthorizationQueryResult PasswordReset(String userName, String newPassword) {
        if (userName == null || newPassword == null || userName == "" || newPassword == "")
            return new AuthorizationQueryResult(AuthorizationQueryResultStatus.ERROR, "Invalid parameters: userName and password must not be null");
        else {
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
    }
}
