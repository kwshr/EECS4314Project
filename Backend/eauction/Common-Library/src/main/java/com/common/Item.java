package com.common;
import java.sql.Time;

public class Item {
    private int itemId;
    private String itemName;
    private String itemDescription;
    private String auctionType;
    private long price;
    private int shippingTime;
    private boolean expeditedShipping;
    private double shippingCost;
    private double expeditedShippingCost;
    private double finalShippingCost;
    private long dutchReservedPrice;
    private Time dutchEndTimer;
    private int sellerId;

    public String getItemName() {
        return itemName;
    }

    public int getItemId() {
        return itemId;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public String getAuctionType() {
        return auctionType;
    }

    public long getPrice() {
        return price;
    }

    public int getShippingTime() {
        return shippingTime;
    }

    public boolean getExpeditedShipping(){
        return expeditedShipping;
    }

    public double getShippingCost() {
        return shippingCost;
    }

    public double getExpeditedShippingCost() {
        return expeditedShippingCost;
    }
    
    public double getFinalShippingCost() {
        return finalShippingCost;
    }

    public long getDutchReservedPrice() {
        return dutchReservedPrice;
    }

    public Time getDutchEndTimer(){
        return dutchEndTimer;
    }

    public int getSellerId(){
        return sellerId;
    }
    
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public void setAuctionType(String auctionType) {
        this.auctionType = auctionType;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public void setShippingTime(int shippingTime) {
        this.shippingTime = shippingTime;
    }

    public void setExpeditedShipping(boolean expeditedShipping){
        this.expeditedShipping = expeditedShipping;
    }
    
    public void setShippingCost(double shippingCost) {
        this.shippingCost = shippingCost;
    }

    public void setExpeditedShippingCost(double expeditedShippingCost) {
        this.expeditedShippingCost = expeditedShippingCost;
    }

    public void setFinalShippingCost(double finalShippingCost) {
        this.finalShippingCost = finalShippingCost;
    }

    public void setDutchReservedPrice(long dutchReservedPrice) {
        this.dutchReservedPrice = dutchReservedPrice;
    }

    public void setDUtchEndTimer(Time dutchEndTimer){
        this.dutchEndTimer = dutchEndTimer;
    }

    public void setSellerId(int sellerId){
        this.sellerId = sellerId;
    }

}
