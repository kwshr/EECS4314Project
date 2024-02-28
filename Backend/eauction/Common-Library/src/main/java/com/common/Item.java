package com.common;

public class Item {
    private int itemId;
    private String itemName;
    private String itemDescription;
    private String auctionType;
    private long price;
    private int fixedTimeLimit;
    private long dutchReservedPrice;
    private long dutchDecrementAmount;
    private int dutchDecrementTimeInterval;

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

    public int getFixedTimeLimit() {
        return fixedTimeLimit;
    }

    public long getDutchReservedPrice() {
        return dutchReservedPrice;
    }

    public long getDutchDecrementAmount() {
        return dutchDecrementAmount;
    }

    public int getDutchDecrementTimeInterval() {
        return dutchDecrementTimeInterval;
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

    public void setFixedTimeLimit(int fixedTimeLimit) {
        this.fixedTimeLimit = fixedTimeLimit;
    }

    public void setDutchReservedPrice(long dutchReservedPrice) {
        this.dutchReservedPrice = dutchReservedPrice;
    }

    public void setDutchDecrementAmount(long dutchDecrementAmount) {
        this.dutchDecrementAmount = dutchDecrementAmount;
    }

    public void setDutchDecrementTimeInterval(int dutchDecrementTimeInterval) {
        this.dutchDecrementTimeInterval = dutchDecrementTimeInterval;
    }

}
