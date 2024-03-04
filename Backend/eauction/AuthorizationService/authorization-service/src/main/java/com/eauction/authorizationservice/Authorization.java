package com.eauction.authorizationservice;
import com.common.*;

public interface Authorization {
    AuthorizationQueryResult SignUp(User user);
    AuthorizationQueryResult SignIn(String userName, String password);
    AuthorizationQueryResult PasswordReset(String username, String newPassword);
}
