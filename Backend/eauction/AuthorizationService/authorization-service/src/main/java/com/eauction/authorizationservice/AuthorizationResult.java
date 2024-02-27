package com.eauction.authorizationservice;

public class AuthorizationResult {
    private String message;
    private AuthorizationResultStatus authorizationResultStatus;

    public AuthorizationResult(AuthorizationResultStatus authorizationResultStatus, String message){
        this.authorizationResultStatus = authorizationResultStatus;
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public AuthorizationResultStatus getAuthorizationStatus(){
        return authorizationResultStatus;
    }
    public void setAuthorizationStatus(AuthorizationResultStatus authorizationResultStatus){
        this.authorizationResultStatus = authorizationResultStatus;
    }
}
