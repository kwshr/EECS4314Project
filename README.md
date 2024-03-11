# Application Backend Setup Guide

Before proceeding with the installation instructions, please note that the required backend services are located in the `Backend/` folder. The backend implementation, consisting of essential microservices and configurations, can be found within this directory. Make sure to follow the subsequent steps to set up and run these services effectively for seamless integration with your application.

## Prerequisites
Before setting up the backend for your application, ensure that the following prerequisites are met:

1. **MySQL Database**: [Install MySQL](https://dev.mysql.com/downloads/mysql/) and [MySQL Workbench](https://www.mysql.com/products/workbench/).
2. **Maven**: [Install Maven](https://maven.apache.org/install.html) for building and managing the project.

## Database Setup
### 1. Create Database Connection
   - Open MySQL Workbench.
   - Create a new connection and name it "eauction."

### 2. Import SQL Schema
   - Locate the SQL file in the `Database` folder.
   - Open the newly created "eauction" connection.
   - Copy the content of the SQL file and execute the query to populate the database and tables.

## Microservices Configuration
### 1. Edit Application Properties
   - Navigate to each microservice's subfolder within `Backend\eauction`.
   - Open the `src/main/resources/application.properties` file.
   - Set the appropriate `username` and `password` for the MySQL database connection.

## Build Common Library
### 1. Build Common Library
   - Navigate to the `Backend\eauction\Common-Library` folder.
   - Open a terminal and run the following command:
     ```
     mvn clean install
     ```

   - Ensure the build is successful before proceeding.

## Microservices Setup
### 1. Build and Run Microservices
   - Navigate to each individual microservice's subfolder within `Backend\eauction`.
   - Open a terminal and run the following commands:
     ```
     mvn clean install
     mvn spring-boot:run
     ```

   - Repeat the above steps for each microservice.
   - Ensure that the common library is successfully built before building the microservices.

Your application backend is now set up and running. Ensure that the necessary configurations and dependencies are in place for each microservice, and you are ready to start using your application.

## Integration Note
### 1. AuctionService and SellerService

The `createAuction` endpoint of the AuctionService is seamlessly integrated with the `addItem` endpoint of the SellerService. Here's how the integration works:

1. When the `addItem` endpoint of the SellerService successfully adds an item to the item table, it automatically triggers a call to the `createAuction` endpoint of the AuctionService.

2. The `createAuction` endpoint of the AuctionService then processes the request and adds the newly added item to the auction, making it available for bidding.

### Service Details

- **SellerService:**
  - Base URL: `http://localhost:8081`
  - Endpoint: `/addItem`

- **AuctionService:**
  - Base URL: `http://localhost:8082`
  - Endpoint: `/createAuction`

### Running Both Services

To ensure seamless integration, make sure to run both services simultaneously. The SellerService should be running on port 8081, and the AuctionService on port 8080.

You can start both services with the following commands:
```bash
# Start SellerService on port 8081
$ cd path/to/SellerService
$ mvn spring-boot:run

# Start AuctionService on port 8082
$ cd path/to/AuctionService
$ mvn spring-boot:run
```

### 2. AuctionService and ItemCatalogueService

The AuctionService is a component of our system that facilitates the auctioning of items. Below are key details about the AuctionService:

- **Port:** 8082

- **Auction Scheduler:**
  - The `AuctionService` features an auction scheduler that runs every 3 minutes.
  - It invokes the `ItemCatalogueService` on port 8080 using the endpoint `/getAuctionedItems`.

- **Integration with ItemCatalogue Service:**
  - The `AuctionService` integrates with the `ItemCatalogueService` to retrieve a list of auctioned items.
  - It calls the `/getAuctionedItems` endpoint on the `ItemCatalogueService` to obtain information about the current auction items.

- **Auction Timer Logic:**
  - Upon receiving the list of auctioned items, the `AuctionService` processes the data.
  - It matches the auction start time and end time with the current time to determine whether to start or end an auction.
  - This timer functionality ensures that auctions progress based on predefined schedules.

- **Simultaneous Execution:**
  - The `AuctionService` runs on port 8082, and the `ItemCatalogueService` runs on port 8080.
  - Both services should be run simultaneously to achieve the desired results.
  - Ensure that both services are up and running concurrently for effective auction management.

**Note:** Make sure to start both services simultaneously for seamless integration and proper functioning of the auction scheduler.
