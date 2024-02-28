package com.eauction.authorizationservice;
import com.common.*;

public interface Authorization {
    AuthorizationResult SignUp(User user);
    AuthorizationResult SignIn(String userName, String password);
    AuthorizationResult PasswordReset(String username, String newPassword);
}
