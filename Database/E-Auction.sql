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
CREATE TABLE Items (
    ItemID                     INT AUTO_INCREMENT PRIMARY KEY,
    ItemName                   VARCHAR(255) NOT NULL,
    ItemDescription            TEXT NOT NULL,
    AuctionType                VARCHAR(10) CHECK (AuctionType IN ('Forward', 'Dutch')) NOT NULL,
    Price                      DOUBLE NOT NULL,
    ShippingTime               INT NOT NULL,
    ExpeditedShipping          BOOLEAN DEFAULT false,
    ShippingCost               DOUBLE NOT NULL,
    ExpeditedShippingCost      DOUBLE NOT NULL,
    FinalShippingCost          DOUBLE NOT NULL,
    DutchReservedPrice         DOUBLE,
    -- timer suposed to start afer the dutchReservedPrice is reached and then end auction after the time ends
    DutchEndTimer              Time
);

CREATE TABLE Sellers (
    SellerID       INT AUTO_INCREMENT PRIMARY KEY,
    SellerUsername VARCHAR(255) UNIQUE NOT NULL,
    Password     VARCHAR(255) NOT NULL,
    FirstName    VARCHAR(255) NOT NULL,
    LastName     VARCHAR(255) NOT NULL,
    ItemID         INT,
    FOREIGN KEY (ItemID) REFERENCES Items (ItemID)
);

ALTER TABLE Items
ADD COLUMN SellerID INT NOT NULL,
ADD CONSTRAINT FK_SellerID FOREIGN KEY (SellerID) REFERENCES Sellers(SellerID);

CREATE TABLE Auctions (
    AuctionID     INT PRIMARY KEY AUTO_INCREMENT,
    ItemID        INT NOT NULL,
    StartDateTime TIMESTAMP NOT NULL,
    EndDateTime   TIMESTAMP NOT NULL,
    CurrentPrice  DOUBLE NOT NULL,
    AuctionStatus        VARCHAR(10) CHECK (AuctionStatus IN ('NotStarted', 'Active', 'Ended', 'Paid')) DEFAULT 'NotStarted' NOT NULL,
    AuctionType   VARCHAR(10) CHECK (AuctionType IN ('Forward', 'Dutch')) DEFAULT 'Forward' NOT NULL,
    WinningBidder  INT,
    FOREIGN KEY (ItemID) REFERENCES Items(ItemID),
    FOREIGN KEY (WinningBidder) REFERENCES Users(UserID)
);

CREATE TABLE Payments (
    PaymentID       INT AUTO_INCREMENT PRIMARY KEY,
    AuctionID       INT,
    PayerID         INT,
    Amount          DOUBLE NOT NULL,
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



