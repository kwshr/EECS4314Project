# Application Backend Setup Guide

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
