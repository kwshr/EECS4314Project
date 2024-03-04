package com.eauction.auctionservice;

import org.springframework.jdbc.core.JdbcTemplate;

public class AuctionImpl implements Auction {

    private final JdbcTemplate jdbcTemplate;

    public AuctionImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public AuctionQueryResult startAuction(int itemId) {
        // TODO Auto-generated method stub
        //add item in the auction table
        throw new UnsupportedOperationException("Unimplemented method 'startAuction'");
    }

    @Override
    public AuctionQueryResult getRemainingTimeUpdate(int itemId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRemainingTimeUpdate'");
    }

    @Override
    public AuctionQueryResult endAuction(int itenId) {
        // TODO Auto-generated method stub
        //add type in the auction table
        throw new UnsupportedOperationException("Unimplemented method 'endAuction'");
    }
    
}
