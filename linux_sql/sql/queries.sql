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

SELECT memid, surname, firstname, joindate FROM cd.members
WHERE joindate >= '2012-09-01';

SELECT surname FROM cd.members
UNION
SELECT name FROM cd.facilities;

SELECT starttime FROM cd.bookings
INNER JOIN cd.members ON cd.members.memid = cd.bookings.memid
WHERE cd.members.firstname = 'David'
AND cd.members.surname = 'Farrell';

SELECT cd.bookings.starttime, cd.facilities.name  FROM cd.bookings
INNER JOIN cd.facilities ON cd.facilities.facid = cd.bookings.facid
WHERE cd.facilities.name in ('Tennis Court 2', 'Tennis Court 1') AND
cd.bookings.starttime >= '2012-09-21' AND
cd.bookings.starttime < '2012-09-22'
ORDER BY cd.bookings.starttime;

SELECT mems.firstname, mems.surname, recs.firstname, recs.surname FROM cd.members mems
LEFT OUTER JOIN cd.members recs 
ON recs.memid = mems.recommendedby
ORDER BY mems.surname, mems.firstname;

SELECT DISTINCT recs.firstname, recs.surname FROM cd.members mems  
INNER JOIN cd.members recs  
ON recs.memid = mems.recommendedby  
ORDER BY surname, firstname;

SELECT DISTINCT mems.firstname || ' ' || mems.surname as member,
(SELECT recs.firstname || ' ' || recs.surname as recommender FROM cd.members recs
 WHERE recs.memid = mems.recommendedby
 )
 FROM cd.members mems
 ORDER BY member;

SELECT recommendedby, count(*) FROM cd.members
WHERE recommendedby IS NOT NULL
GROUP BY recommendedby
ORDER BY recommendedby;

SELECT facid, sum(slots) AS "Total Slots" FROM cd.bookings
GROUP BY facid
ORDER BY facid;

SELECT facid, sum(slots) AS "Total Slots" FROM cd.bookings
WHERE starttime >= '2012-09-01'
AND starttime < '2012-10-01'
GROUP BY facid
ORDER BY sum(slots);
