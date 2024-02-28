package com.eauction.itemcatalogueservice;
import com.common.Item;

public interface ItemCatalogue {
    ItemCatalogueQueryResult Search (String keyword);
    ItemCatalogueQueryResult getAuctionedItems(); 
    ItemCatalogueQueryResult getItem(int itemId);
    ItemCatalogueQueryResult addItem(Item item);
}
