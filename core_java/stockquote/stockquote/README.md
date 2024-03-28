# Introduction
This app is a tool to poll API data from an end point and storing it in a sql server, functionally this means it is
collecting stock quote information and stored into a server. It then stores the data into an sql server and you can simulate buying and selling stocks based on the collected data.
It is written in java and built using Maven. 

The connection to the endpoint is facilitated with the OKHTTP library for the JDBC. The resulting JSON files
are parsed using the JACKSON databind library. They are then stored into a postgreSQL server instance with two tables, one to store stockquote information,
and the other used to store positions the user has bought into. Furthermore it is unit tested and integration tested using the Junit and Mockito.

# Implementaiton
## ER Diagram
![image](https://github.com/jarviscanada/jarvis_data_eng_DevinSmith/assets/66887499/a09c2444-f0f8-42b9-b865-192349abaa75)

## Design Patterns
In this project, I created two DAO implementations. There is a java class called CrudDao that my PositionDao and my QuoteDao implement.
These DAOs implement the four core functions of persistent storage. These are faciliatated by prepared statements handed to methods to inact them.


`T save(T entity)`

The save method allows the creation of a new entry into their tables, though if the primary key is already found it will instead update the entry.
The QuoteDAO is also the class that accesses and pulls the data off of the API endpoint for insertion into the Quote table.
The save method therefore implements CREATE and UPDATE. 

`Optional<T> findById(String id)`

`Iterable<T> findAll()`

The DAO's feature a findByID method, which checks the respective table based on the entry's 
ID to see if there is an entry that matches the ID. This is the implementation of READ. There is also findAll which returns everything in the table.

`void deleteById(ID id)`

`void deleteAll()`

Then there is a deleteByID and a deleteAll method for each DAO.
These implement DELETE for the sql tables, deleting a single element by ID or deleting the entire tables contents, respectively. 

# Test
## Setting up the Database
Using the postgreSQL terminal, the following command creates the database

`DROP DATABASE IF EXISTS stock_quote;`

`CREATE DATABASE stock_quote;`

Then to instantiate the tables, the following commands were used, first for quotes:
DROP TABLE IF EXISTS quote;
`CREATE TABLE quote (`

`    symbol              VARCHAR(10) PRIMARY KEY,`

`    open                DECIMAL(10, 2) NOT NULL,`

`    high                DECIMAL(10, 2) NOT NULL,`

`    low                 DECIMAL(10, 2) NOT NULL,`

`    price               DECIMAL(10, 2) NOT NULL,`

`    volume              INT NOT NULL,`

`    latest_trading_day  DATE NOT NULL,`

`    previous_close      DECIMAL(10, 2) NOT NULL,`

`    change              DECIMAL(10, 2) NOT NULL,`

`    change_percent      VARCHAR(10) NOT NULL,`

`    timestamp           TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,`\

`);`

Then for positions:

`DROP TABLE IF EXISTS position;`

`CREATE TABLE position (`

`    symbol                VARCHAR(10) PRIMARY KEY,`

`    number_of_shares      INT NOT NULL,`

`    value_paid            DECIMAL(10, 2) NOT NULL,`

`    CONSTRAINT symbol_fk	FOREIGN KEY (symbol) REFERENCES quote(symbol)`

`);`

In testing, I would manually insert data into the quote table using commands in the sql terminal. Then when I did search, delete and delete all I could make other checks
with findByID to see if the data had been interacted with. I would do similar with the position table as well, however I needed data input into the quote table when testing the position table as it was dependent on the quote tables ID value because it contained the primary key. 

During testing I would insert data into quote then compare the data I inserted with the data being returned by findByID. I would do similar for the postions table. I have tested at the DAO and service layers, for both the position and quote components. For testing the controller and main, because they were essentially scripts, I just ran through them with different combinations of inputs to see if I could find any odd output. This testing was facilitated by the use of Junit and Mockito. Junit covered the general testing with assertions, while Mockito allowed me to do unit testing through mocks and stubs.
