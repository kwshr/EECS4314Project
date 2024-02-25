package com.eauction.authorizationservice;

public interface Authorization {
    public void SignUp(User user);
    public void SignIn(Credential cred);
    public String PasswordReset(String username);
}
