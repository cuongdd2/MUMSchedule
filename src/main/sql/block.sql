SET foreign_key_checks = 0;
USE cs425;
TRUNCATE TABLE block;

INSERT INTO block (name, start_date, end_date) VALUES ('October 2017', '2017-10-02', '2017-10-26');
INSERT INTO block (name, start_date, end_date) VALUES ('November 2017', '2017-10-30', '2017-11-22');
INSERT INTO block (name, start_date, end_date) VALUES ('December 2017', '2017-11-27', '2017-12-20');
INSERT INTO block (name, start_date, end_date) VALUES ('January 2017', '2018-01-15', '2018-02-08');
INSERT INTO block (name, start_date, end_date) VALUES ('February 2018', '2018-02-12', '2018-02-23');
INSERT INTO block (name, start_date, end_date) VALUES ('March 2018', '2018-02-26', '2018-03-22');
INSERT INTO block (name, start_date, end_date) VALUES ('April 2018', '2018-04-02', '2018-04-26');
INSERT INTO block (name, start_date, end_date) VALUES ('May 2018', '2018-04-30', '2018-05-24');

select * from professor where id in (select prof_id from prof_course where course_id = 2);

select * from professor where id in (select prof_id from prof_course where course_id = 1 and prof_id in (select prof_id from prof_block where block_id = 1));

select * from course where id in (select distinct course_pre from course_pre);

select * from course where level = 'L500';
select * from course where level = 'L400' and code <> 'CS401';

SELECT * FROM block WHERE start_date >= '2017-08-03' AND start_date < '2018-05-03' ORDER BY start_date LIMIT 7