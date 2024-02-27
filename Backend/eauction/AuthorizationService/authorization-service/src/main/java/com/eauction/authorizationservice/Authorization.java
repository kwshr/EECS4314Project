package com.eauction.authorizationservice;

public interface Authorization {
    AuthorizationResult SignUp(User user);
    AuthorizationResult SignIn(String userName, String password);
    AuthorizationResult PasswordReset(String username, String newPassword);
}
