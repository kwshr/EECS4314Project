package com.eauction.itemcatalogueservice;

public class ItemCatalogueImpl implements ItemCatalogue{

    @Override
    public  ItemCatalogueQueryResult Search(String keyword) {
       return new ItemCatalogueQueryResult(ItemCatalogueQueryResultStatus.NOT_IMPLEMENTED,"not implemented");
    }

    @Override
    public ItemCatalogueQueryResult getAuctionedItems() {
        return new ItemCatalogueQueryResult(ItemCatalogueQueryResultStatus.NOT_IMPLEMENTED,"not implemented");
    }

    @Override
    public ItemCatalogueQueryResult getItem(int itemId) {
        return new ItemCatalogueQueryResult(ItemCatalogueQueryResultStatus.NOT_IMPLEMENTED,"not implemented");
    }

    @Override
    public ItemCatalogueQueryResult addItem(Item item) {
        return new ItemCatalogueQueryResult(ItemCatalogueQueryResultStatus.NOT_IMPLEMENTED,"not implemented");
    }
    
}
