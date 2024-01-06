# SQL Query Practice
## 1. Introduction
The SQL Query Practice Project is a practice project to use and learn examples of SQL database interaction. This is done by developing a basic database,
creating the three Postgresql tables cd.members, cd.facilities and cd.bookings. These combined are a simulated database for a country club of some sorts, covering the facilities of the club,
the members of the club and the bookings of timeslots for the clubs facilities. This project uses Postgresql to create the database, PGAdmin 4 to administer and modify the database and containerized using docker. As well a ddl file was given to populate the database with information to manipulate for the practice questions.

## 2. SQL Queries
### Table Setup (DDL)
The following snippet of sql command code shows how the tables are initialized.

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

This project contains three tables for the database, as seen in the DDL code snippet above: cd.members, cd.bookings, and cd.facilities. The following is a brief explanation of the data columns of the tables:

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


## 3. Practice Questions

### Question 1:
 The club is adding a new facility - a spa. We need to add it into the facilities table. Use the following values:  
 facid: 9, Name: 'Spa', membercost: 20, guestcost: 30, initialoutlay: 100000, monthlymaintenance: 800.
### Answer SQL:
  INSERT INTO cd.facilities ( facid, name, membercost, guestcost, initialoutlay, monthlymaintenance)  
  VALUES (9, 'Spa', 20, 30, 100000, 800);
### Question 2:
Let's try adding the spa to the facilities table again. This time, though, we want to automatically generate the value for the next facid, rather than specifying it as a constant. Use the following values for everything else:  
Name: 'Spa', membercost: 20, guestcost: 30, initialoutlay: 100000, monthlymaintenance: 800.
### Answer SQL:
INSERT INTO cd.facilities ( facid, name, membercost, guestcost, initialoutlay, monthlymaintenance)  
SELECT (SELECT max(facid) from cd.facilities)+1, 'Spa', 20, 30, 100000, 800;  
*Note: This database did not have facid as a serial value, thus SELECT had to be used this way, otherwise it would just be another VALUES command with the section for facid being DEFAULT.  
### Question 3:
We made a mistake when entering the data for the second tennis court. The initial outlay was 10000 rather than 8000: you need to alter the data to fix the error.
### Answer SQL:
UPDATE cd.facilities  
SET initialoutlay = 10000  
WHERE name = 'Tennis Court 2';  
### Question 4:
We want to alter the price of the second tennis court so that it costs 10% more than the first one. Try to do this without using constant values for the prices, so that we can reuse the statement if we want to.
### Answer SQL:
UPDATE cd.facilities
SET membercost = (SELECT membercost * 1.1 FROM cd.facilities WHERE name = 'Tennis Court 1'),  
    guestcost = (SELECT guestcost * 1.1 FROM cd.facilities WHERE name = 'Tennis Court 1')  
WHERE name = 'Tennis Court 2';
### Question 5:
As part of a clearout of our database, we want to delete all bookings from the cd.bookings table. How can we accomplish this?
### Answer SQL:
DELETE FROM cd.bookings;
### Question 6:
## 7. Testing
Testing this code was in two parts, the bash scripts were tested while coding through the use of `echo` statements on the bash scripts. These are further confirmed using docker and psql commands to check that the docker instance is running, and that the tables are generated. host_info.sh is manually called to populate the host_info table, as it is needed to run host_usage.sh. Afterwards, with crontab running the host_usage.log can be checked with cat to confirm the appropriate data is being generated, and you can check the host_usage table to see that it is being populated.

## 8. Deployment
The code is deployable by downloading the Github repository, then following the quickstart commands, which will obtain and implement a docker container with a psql instance generated within, and create the relevent tables. Once the `host_info.sh` script is run, and the crontab job is created, it runs and collects data autonomously.

## 9. Improvements
Some impovements I would suggest for this project are:
  + handle hardware updates
  + color encoding for the different hardware specs to differentiate nodes
  + automate the first running of the host_info.sh bash script
