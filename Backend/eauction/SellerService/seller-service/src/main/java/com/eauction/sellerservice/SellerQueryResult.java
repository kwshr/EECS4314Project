package com.eauction.sellerservice;

public class SellerQueryResult {
    private String message;
    private SellerServiceQueryStatus sellerServiceQueryStatus;
    private Object data = null;

    public SellerQueryResult(SellerServiceQueryStatus sellerServiceQueryStatus, String message){
        this.sellerServiceQueryStatus = sellerServiceQueryStatus;
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public SellerServiceQueryStatus getSellerServiceQueryStatus(){
        return sellerServiceQueryStatus;
    }
    public void setSellerServiceQueryStatus(SellerServiceQueryStatus sellerServiceQueryStatus){
        this.sellerServiceQueryStatus = sellerServiceQueryStatus;
    }
    public Object getData() {
		return data;
	}
	public void setData(Object obj) {
		this.data = obj;
	}
}
