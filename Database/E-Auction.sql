CREATE SCHEMA IF NOT EXISTS eauction;
USE eauction;
CREATE TABLE Users (
    UserID       INT AUTO_INCREMENT PRIMARY KEY,
    UserName     VARCHAR(255) NOT NULL UNIQUE,
    Password     VARCHAR(255) NOT NULL,
    FirstName    VARCHAR(255),
    LastName     VARCHAR(255),
    StreetName   VARCHAR(255),
    StreetNumber VARCHAR(255),
    City         VARCHAR(255),
    Country      VARCHAR(255),
    PostalCode   VARCHAR(255),
    CreatedAt    TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE Sellers (
    SellerID       INT AUTO_INCREMENT PRIMARY KEY,
    SellerUsername VARCHAR(255) UNIQUE NOT NULL,
    Name           VARCHAR(255) NOT NULL,
    ItemID         INT,
    FOREIGN KEY (ItemID) REFERENCES Items (ItemID)
);
CREATE TABLE Items (
    ItemID                     INT AUTO_INCREMENT PRIMARY KEY,
    ItemName                   VARCHAR(255) NOT NULL,
    ItemDescription            TEXT,
    AuctionType                VARCHAR(10) CHECK (AuctionType IN ('Forward', 'Dutch')) NOT NULL,
    Price                      BIGINT NOT NULL,
    ShippingTime               INT NOT NULL,
    ShippingCost               DOUBLE NOT NULL,
    ExpeditedShippingCost      DOUBLE NOT NULL,
    FinalShippingCost          DOUBLE NOT NULL,
    FixedTimeLimit             INT,
    DutchReservedPrice         BIGINT,
    DutchDecrementAmount       BIGINT,
    DutchDecrementTimeInterval INT
);
ALTER TABLE Items
ADD COLUMN SellerID INT,
ADD CONSTRAINT FK_SellerID FOREIGN KEY (SellerID) REFERENCES Sellers(SellerID);
CREATE TABLE Auctions (
    AuctionID     INTEGER PRIMARY KEY AUTO_INCREMENT,
    ItemID        INTEGER,
    StartDateTime TIMESTAMP NOT NULL,
    EndDateTime   TIMESTAMP NOT NULL,
    CurrentPrice  DECIMAL(10, 2),
    AuctionStatus        VARCHAR(10) CHECK (AuctionStatus IN ('NotStarted', 'Active', 'Ended', 'Paid')) DEFAULT 'NotStarted' NOT NULL,
    AuctionType   VARCHAR(10) CHECK (AuctionType IN ('Forward', 'Dutch')) DEFAULT 'Forward' NOT NULL,
    WinnerID      INTEGER,
    FOREIGN KEY (ItemID) REFERENCES Items(ItemID),
    FOREIGN KEY (WinnerID) REFERENCES Users(UserID)
);
CREATE TABLE Bids (
    BidID       INT AUTO_INCREMENT PRIMARY KEY,
    AuctionID   INT,
    BidderID    INT,
    BidAmount   DECIMAL(10, 2) NOT NULL,
    BidDateTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (AuctionID) REFERENCES Auctions(AuctionID),
    FOREIGN KEY (BidderID) REFERENCES Users(UserID)
);
CREATE TABLE Payments (
    PaymentID       INT AUTO_INCREMENT PRIMARY KEY,
    AuctionID       INT,
    PayerID         INT,
    Amount          DECIMAL(10, 2) NOT NULL,
    PaymentDateTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (AuctionID) REFERENCES Auctions(AuctionID),
    FOREIGN KEY (PayerID) REFERENCES Users(UserID)
);
CREATE TABLE Shipping (
    ShippingID            INT AUTO_INCREMENT PRIMARY KEY,
    PaymentID             INT,
    ShippingAddress       TEXT NOT NULL,
    EstimatedDeliveryTime INT,
    ExpeditedShipment     BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (PaymentID) REFERENCES Payments(PaymentID)
);



