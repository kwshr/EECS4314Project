package com.eauction.itemcatalogueservice;

public class ItemCatalogueQueryResult {
    private String message;
    private ItemCatalogueQueryResultStatus itemCatalogueQueryResultStatus;
    private Object data = null;

    public ItemCatalogueQueryResult(ItemCatalogueQueryResultStatus itemCatalogueQueryResultStatus, String message){
        this.itemCatalogueQueryResultStatus = itemCatalogueQueryResultStatus;
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public ItemCatalogueQueryResultStatus getItemCatalogueQueryResultStatus(){
        return itemCatalogueQueryResultStatus;
    }
    public void setItemCatalogueQueryResultStatus(ItemCatalogueQueryResultStatus itemCatalogueQueryResultStatus){
        this.itemCatalogueQueryResultStatus = itemCatalogueQueryResultStatus;
    }
    public Object getData() {
		return data;
	}
	public void setData(Object obj) {
		this.data = obj;
	}
}
