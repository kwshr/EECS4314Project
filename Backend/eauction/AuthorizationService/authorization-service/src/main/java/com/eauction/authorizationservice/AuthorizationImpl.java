package com.eauction.authorizationservice;

import com.common.*;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationImpl implements Authorization{

    private final JdbcTemplate jdbcTemplate;

    public AuthorizationImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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

        try{
            String query = "INSERT INTO Users (userName, password, firstName, lastName, streetName, streetNumber, city, country, postalCode) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(query, userName, password, firstName, lastName, streetName, streetNumber, city, country, postalCode);
            return new AuthorizationQueryResult(AuthorizationQueryResultStatus.SUCCESS, "User signed up successfully");
        } catch (Exception e) {
            return new AuthorizationQueryResult(AuthorizationQueryResultStatus.ERROR, "Failed to sign up user :"+ e.getMessage());
        }
     }

    @Override
    public AuthorizationQueryResult SignIn(String userName, String password) {
        if(userName==null || password==null)
		return new AuthorizationQueryResult(AuthorizationQueryResultStatus.ERROR, "Invalid parameters: userName and password must not be null");
        else{
            try{
                String query = "SELECT userId FROM Users WHERE userName = ? AND password = ?";
                Integer userId = jdbcTemplate.queryForObject(query, Integer.class, userName, password);
                if(userId!=null){
                    return new AuthorizationQueryResult(AuthorizationQueryResultStatus.SUCCESS, "Sign-in successful");
                } else {
                    return new AuthorizationQueryResult(AuthorizationQueryResultStatus.NOT_FOUND, "Invalid username or password");
                }
            } catch(Exception e){
                return new AuthorizationQueryResult(AuthorizationQueryResultStatus.ERROR, "Failed to sign in"+e.getMessage());
            }
        }
    }

    @Override
    public AuthorizationQueryResult PasswordReset(String userName, String newPassword) {
        if(userName==null || newPassword==null)
        return new AuthorizationQueryResult(AuthorizationQueryResultStatus.ERROR, "Invalid parameters: userName and password must not be null");
        else{
            try{
                String query = "UPDATE Users SET password = ? WHERE userName = ?";
                int rowsAffected = jdbcTemplate.update(query, newPassword, userName);
                 if (rowsAffected > 0) {
                return new AuthorizationQueryResult(AuthorizationQueryResultStatus.SUCCESS, "Password reset successful");
                 } else {
                    return new AuthorizationQueryResult(AuthorizationQueryResultStatus.NOT_FOUND, "User not found with the specified username");
                }
            } catch(Exception e){
                return new AuthorizationQueryResult(AuthorizationQueryResultStatus.ERROR, "Failed to reset password"+e.getMessage());
        }
    }
}
     
}
