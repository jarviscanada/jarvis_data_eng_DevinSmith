INSERT INTO cd.facilities ( facid, name, membercost, guestcost, initialoutlay, monthlymaintenance)
VALUES (9, 'Spa', 20, 30, 100000, 800);

INSERT INTO cd.facilities ( facid, name, membercost, guestcost, initialoutlay, monthlymaintenance)
SELECT (SELECT max(facid) from cd.facilities)+1, 'Spa', 20, 30, 100000, 800;

UPDATE cd.facilities
SET initialoutlay = 10000
WHERE name = 'Tennis Court 2';

UPDATE cd.facilities
SET membercost = (SELECT membercost * 1.1 FROM cd.facilities WHERE name = 'Tennis Court 1'),
    guestcost = (SELECT guestcost * 1.1 FROM cd.facilities WHERE name = 'Tennis Court 1')
WHERE name = 'Tennis Court 2';

DELETE FROM cd.bookings;

DELETE FROM cd.members WHERE memid = 37;

SELECT facid, name, membercost, monthlymaintenance FROM cd.facilities
WHERE membercost > 0 and
      (membercost < monthlymaintenance/50);

SELECT * FROM cd.facilities
WHERE name LIKE '%Tennis%';

SELECT * FROM cd.facilities
WHERE name like '%2';
