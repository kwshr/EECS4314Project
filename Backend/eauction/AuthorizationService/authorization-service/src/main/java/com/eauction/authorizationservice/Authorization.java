package com.eauction.authorizationservice;
import com.common.*;

public interface Authorization {
    AuthorizationQueryResult signUp(User user, String accountType);
    AuthorizationQueryResult getUserId(String userName);
    AuthorizationQueryResult getSellerId(String userName);
    AuthorizationQueryResult signIn(String userName, String password,String accountType);
    AuthorizationQueryResult passwordReset(String username, String newPassword);
    AuthorizationQueryResult getUserDetails(int userId);
}
