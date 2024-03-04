package com.eauction.authorizationservice;

public class AuthorizationQueryResult {
    private String message;
    private AuthorizationQueryResultStatus authorizationResultStatus;
    private Object data = null;

    public AuthorizationQueryResult(AuthorizationQueryResultStatus authorizationResultStatus, String message){
        this.authorizationResultStatus = authorizationResultStatus;
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public AuthorizationQueryResultStatus getAuthorizationStatus(){
        return authorizationResultStatus;
    }

    public void setAuthorizationStatus(AuthorizationQueryResultStatus authorizationResultStatus){
        this.authorizationResultStatus = authorizationResultStatus;
    }
    
    public Object getData() {
		return data;
	}

	public void setData(Object obj) {
		this.data = obj;
	}
}
