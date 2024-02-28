package com.eauction.authorizationservice;

import com.common.*;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationImpl implements Authorization{

    @Override
    public AuthorizationResult SignUp(User user) {
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
        return new AuthorizationResult(AuthorizationResultStatus.NOT_IMPLEMENTED,"Implementation in progress");
    }

    @Override
    public AuthorizationResult SignIn(String userName, String password) {
        return new AuthorizationResult(AuthorizationResultStatus.NOT_IMPLEMENTED,"Implementation in progress");
    }

    @Override
    public AuthorizationResult PasswordReset(String username, String newPassword) {
        return new AuthorizationResult(AuthorizationResultStatus.NOT_IMPLEMENTED,"Implementation in progress");
    }
     
}
