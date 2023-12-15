     CREATE TABLE IF NOT EXISTS cd.members (
          memid              SERIAL NOT NULL,
          surname            CHARACTER VARYING(200) NOT NULL,
          firstname          CHARACTER VARYING(200) NOT NULL,
          address            CHARACTER VARYING(300) NOT NULL,
          zipcode            INT NOT NULL,
          telephone          CHARACTER VARYING(20) NOT NULL,
          recommendedby      INT4 NOT NULL,
          joindate           INT NOT NULL,
          CONSTRAINT host_info_pl PRIMARY KEY (memid),
          CONSTRAINT host_info_un FOREIGN KEY (recommendedby)
          );

     CREATE TABLE IF NOT EXISTS cd.bookings (
          facid              INT NOT NULL,
          memid              INT NOT NULL,
          starttime          TIMESTAMP NOT NULL,
          slots              INT NOT NULL,
          );

     CREATE TABLE IF NOT EXISTS cd.facilities (
          facid              INT NOT NULL,
          name               CHARACTER VARYING(100) NOT NULL,
          membercost
          guestcost
          initialoutlay
          monthlymaintenance
          );
                                                              1,0-1         Top

