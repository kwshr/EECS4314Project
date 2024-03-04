package com.eauction.itemcatalogueservice;

import org.springframework.context.annotation.Configuration;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

@Configuration
public class DatabaseConnection {

    private final DataSource dataSource;

    public DatabaseConnection(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection connect() {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}

