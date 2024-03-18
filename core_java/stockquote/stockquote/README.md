# Introduction
This app is a tool to poll API data from an end point and storing it in a sql server, functionally this means it is
collecting stock quote information and stored into a server. It then stores the data into an sql server and you can simulate buying and selling stocks based on the collected data.
It is written in java and built using Maven. 
The connection to the endpoint is facilitated with the OKHTTP library for the JDBC. The resulting JSON files
are parsed using the JACKSON databind library. They are then stored into a postgreSQL server instance with two tables, one to store stockquote information,
and the other used to store positions the user has bought into. Furthermore it is unit tested and integration tested using the Junit and Mockito.

# Implementaiton
## ER Diagram
ER diagram

## Design Patterns
Discuss DAO and Repository design patterns (150-200 words)

# Test
How you test your app against the database? (e.g. database setup, test data set up, query result)
