package com.eauction.shippingservice;

public class ShippingQueryResult {
    private String message;
    private ShippingQueryResultStatus shippingQueryResultStatus;
    private Object data = null;

    public ShippingQueryResult(ShippingQueryResultStatus shippingQueryResultStatus, String message){
        this.shippingQueryResultStatus = shippingQueryResultStatus;
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public ShippingQueryResultStatus getItemCatalogueQueryResultStatus(){
        return shippingQueryResultStatus;
    }
    public void setItemCatalogueQueryResultStatus(ShippingQueryResultStatus shippingQueryResultStatus){
        this.shippingQueryResultStatus = shippingQueryResultStatus;
    }
    public Object getData() {
		return data;
	}
	public void setData(Object obj) {
		this.data = obj;
	}
}
