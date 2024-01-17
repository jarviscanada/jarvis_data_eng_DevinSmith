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
                                1,0-1         Top

