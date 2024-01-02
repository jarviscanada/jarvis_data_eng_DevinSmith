# SQL Query Practice
## 1. Introduction
The SQL Query Practice Project is a practice project to use and learn examples of SQL database interaction. This is done by developing a basic table,
creating a postgreSQL table as explained in the project. CURRENTLY this README.md is a shell from the previous project, and it will be updated alongside the project as needed.

## 2. Quickstart
### 1. Database and Table Initialization
Before running the bash agent, the PosgreSQL instance is allocated by creating and starting up a docker container, creating the psql instance and then creating the host_info and host_usage tables.
```
# From the repository's home directory, allocate and create a psql instance with docker
./linux_sql/scripts/psql_docker.sh start db_password
# Do note that you can also use "stop" instead of "start" to stop the instance

# Initialize the database and tables
psql -h psql_host -U psql_user -W -f linux_sql/sql/ddl.sql
```
### 2. host_info.sh Usage

This script only needs to be run once per node to insert the node's hardware specifications into the host_info table
```
# Insert node hardware specifications into the host_info table
./linux_sql/scripts/host_info.sh psql_host psql_port db_name psql_user psql_password
```
### 3. host_usage.sh Usage

This script inserts a snapshot of the node's current resource usage into the host_usage table and can be ran manually
```
# Insert a snapshot of the node's resource usage into the host_usage table
./linux_sql/scripts/host_usage.sh psql_host psql_port db_name psql_user psql_password
```
### 4. crontab Setup

A crontab job can be created to repeatedly run the host_usage.sh script over a specified interval
```
# Run this to edit your current crontab jobs
crontab -e

# Enter the following line in the opened file to set up the job
* * * * * bash [path]/host_usage.sh psql_host psql_port db_name psql_user psql_password > /tmp/host_usage.log

# Verify that the job was successfully created by listing crontab jobs
crontab -ls

# Verify that the script is running as intended by checking the log file
cat /tmp/host_usage.log
```

## 3. Implementation
These are the steps that were taken in implementing this project
### 1. The first step in implementing the project is establishing a github repo for code management
Once the repo is established we can make a development branch, and furthermore the appropriate feature branches.
### 2. The second step is getting a basic Docker undestanding a creating a container
The containerization allows us to work with the psql in an enclosed environment
### 3. The third step is is to initializing a database inside the container and establishing the tables host_info and host usage
The correct data types for each of the fields in the table allow us to confirm the correct data is being input.
### 4. The fourth step is setting up the crontab in order to automate running the `host_usage.sh` script
Setting up a crontab job allows the `host_usage.sh` script can run every minute to continously collect usage data.

## 4. Architecture
![An image showing the architecture of the node network](/linux_sql/assets/ArchitectureImage.png)

## 5. Scripts
+ `NEEDS TO BE UPDATED` Filler statements to keep the shell 2
+ `NEEDS TO BE UPDATED 2: THE RETURN` Filler statements to keep the shell 2


## 6. DataBase Modeling [UPDATED]
### Table Setup (DDL)
     CREATE TABLE IF NOT EXISTS cd.members (
          memid              INTEGER NOT NULL,
          surname            CHARACTER VARYING(200) NOT NULL,
          firstname          CHARACTER VARYING(200) NOT NULL,
          address            CHARACTER VARYING(300) NOT NULL,
          zipcode            INTEGER NOT NULL,
          telephone          CHARACTER VARYING(20) NOT NULL,
          recommendedby      INTEGER,
          joindate           timestamp NOT NULL,
          CONSTRAINT members_pk PRIMARY KEY (memid),
          CONSTRAINT fk_members_recommendedby FOREIGN KEY (recommendedby)
          REFERENCES cd.member(memid) ON DELETE SET NULL
          );

     CREATE TABLE IF NOT EXISTS cd.facilities (
          facid              INTEGER NOT NULL,
          name               CHARACTER VARYING(100) NOT NULL,
          membercost         NUMERIC NOT NULL,
          guestcost          NUMERIC NOT NULL,
          initialoutlay      NUMERIC NOT NULL,
          monthlymaintenance NUMERIC NOT NULL,
          CONSTRAINT facilities_pk PIMRARY KEY (facid)
          );

     CREATE TABLE IF NOT EXISTS cd.bookings (
          bookid             INTEGER NOT NULL,
          facid              INTEGER NOT NULL,
          memid              INTEGER NOT NULL,
          starttime          TIMESTAMP NOT NULL,
          slots              INTEGER NOT NULL,
          CONSTRAINT bookings_pk PRIMARY KEY (bookid),
          CONSTRAINT fk_bookings_facid FOREIGN KEY (facid)
          REFERENCES cd.facilities(facid),
          CONSTRAINT fk_bookings_memid FOREIGN KEY (memid)
          REFERENCES cd.members(memid)
        );
*Note the given table did not have the bookid for the cd.bookings, and had to be added from the reference afterwards.

This project contains three tables for the database, as seen in the DDL shown above, cd.members, cd.bookings, and cd.facilities, with the following schema:
`cd.members`:
+ `memid`: an id number assigned to each member, is a primary key in the table and auto incremented by Postgresql and the Primary Key for the table
+ `surname`: The surname of the member, a character string
+ `firstname`: The first name of the member, a character string
+ `address`: The members address, a character string
+ `zipcode`: The members zipcode, an integer
+ `telephone`: The members telephone number, an integer string
+ `recommendedby`: If the member was recommended, the memid of the recommending member, a foreign key
+ `joindate`: A timestamp of when the member joined in UTC format

`cd.facilities`:
+ `facid`: The facilities id value as an int, primary key
+ `name`: The facilities name, a character string
+ `membercost`: The booking cost of the facility for a member, a numeric value
+ `guestcost`: The booking cost of the facility for a guest, a numeric value
+ `initialoutlay`: The cost of building the facility, a numeric value
+ `monthlymaintenance`: The estimated upkeep cost of the facility, a numeric value

`cd.bookings`:
+ `bookid`: The id value given to the booking
+ `facid`: The facility id for bookings, a primary key
+ `memid`: The member id for the bookings, a foreign key with cd.members
+ `starttime`: The start time of the booking, a UTC timestamp
+ `slots`: The amount of half hour "blocks" the booking covers 
  
## 7. Testing
Testing this code was in two parts, the bash scripts were tested while coding through the use of `echo` statements on the bash scripts. These are further confirmed using docker and psql commands to check that the docker instance is running, and that the tables are generated. host_info.sh is manually called to populate the host_info table, as it is needed to run host_usage.sh. Afterwards, with crontab running the host_usage.log can be checked with cat to confirm the appropriate data is being generated, and you can check the host_usage table to see that it is being populated.

## 8. Deployment
The code is deployable by downloading the Github repository, then following the quickstart commands, which will obtain and implement a docker container with a psql instance generated within, and create the relevent tables. Once the `host_info.sh` script is run, and the crontab job is created, it runs and collects data autonomously.

## 9. Improvements
Some impovements I would suggest for this project are:
  + handle hardware updates
  + color encoding for the different hardware specs to differentiate nodes
  + automate the first running of the host_info.sh bash script
