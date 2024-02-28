package com.eauction.itemcatalogueservice;

public interface ItemCatalogue {
    ItemCatalogueQueryResult Search (String keyword);
    ItemCatalogueQueryResult getAuctionedItems(); 
    ItemCatalogueQueryResult getItem(int itemId);
}
