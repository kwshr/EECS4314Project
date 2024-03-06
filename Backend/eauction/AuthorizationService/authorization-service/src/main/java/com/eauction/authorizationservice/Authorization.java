package com.eauction.authorizationservice;
import com.common.*;

public interface Authorization {
    AuthorizationQueryResult signUp(User user);
    AuthorizationQueryResult signIn(String userName, String password);
    AuthorizationQueryResult passwordReset(String username, String newPassword);
    AuthorizationQueryResult getUserDetails(int userId);
}
