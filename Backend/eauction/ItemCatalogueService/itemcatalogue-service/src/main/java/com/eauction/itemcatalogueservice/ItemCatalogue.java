package com.eauction.itemcatalogueservice;

import java.util.List;

public interface ItemCatalogue {
    public List<Item> Search (String keyword);
    public List<Item> getAuctionedItems();
    public Item getItem(int itemId);
}
