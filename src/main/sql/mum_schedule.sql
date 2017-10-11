DROP DATABASE cs425;
CREATE DATABASE cs425;
USE cs425;

CREATE TABLE course (
  id     INT AUTO_INCREMENT PRIMARY KEY,
  name   VARCHAR(100) UNIQUE,
  code   VARCHAR(10) UNIQUE,
  `desc` VARCHAR(5000),
  level  ENUM('L400', 'L500'),
  preNo INT DEFAULT 0
);

CREATE TABLE course_pre (
  course_id  INT,
  course_pre INT,
  FOREIGN KEY (course_id) REFERENCES course (id),
  FOREIGN KEY (course_pre) REFERENCES course (id)
);


CREATE TABLE entry (
  id         INT AUTO_INCREMENT PRIMARY KEY,
  name       VARCHAR(20) UNIQUE,
  start_date DATE
);

CREATE TABLE block (
  id         INT AUTO_INCREMENT PRIMARY KEY,
  name       VARCHAR(20) UNIQUE,
  start_date DATE,
  end_date   DATE
);

CREATE TABLE student (
  id       INT AUTO_INCREMENT PRIMARY KEY,
  name     VARCHAR(100),
  email    VARCHAR(100) UNIQUE,
  dob      DATE,
  entry_id INT,
  track    ENUM ('FPP', 'MPP')
);

CREATE TABLE professor (
  id    INT AUTO_INCREMENT PRIMARY KEY,
  name  VARCHAR(100),
  title VARCHAR(100),
  email VARCHAR(100) UNIQUE,
  phone VARCHAR(20) UNIQUE,
  about VARCHAR(1000)
);

CREATE TABLE prof_course (
  prof_id   INT,
  course_id INT,
  FOREIGN KEY (prof_id) REFERENCES professor (id),
  FOREIGN KEY (course_id) REFERENCES course (id)
);

CREATE TABLE prof_block (
  prof_id  INT,
  block_id INT,
  FOREIGN KEY (prof_id) REFERENCES professor (id),
  FOREIGN KEY (block_id) REFERENCES block (id)
);

CREATE TABLE class (
  id        INT AUTO_INCREMENT PRIMARY KEY,
  course_id INT,
  prof_id   INT,
  block_id  INT,
  capacity  INT,
  enrolled  INT,
  FOREIGN KEY (prof_id) REFERENCES professor (id),
  FOREIGN KEY (block_id) REFERENCES block (id),
  FOREIGN KEY (course_id) REFERENCES course (id)
);