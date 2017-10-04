CREATE TABLE course (
  id     INT AUTO_INCREMENT PRIMARY KEY,
  name   VARCHAR(100),
  code   VARCHAR(10),
  `desc` VARCHAR(5000),
  level  ENUM('400', '500'),
  no_pre INT
);

CREATE TABLE course_pre (
  course_id  INT,
  course_pre INT,
  FOREIGN KEY (course_id) REFERENCES course (id),
  FOREIGN KEY (course_pre) REFERENCES course (id)
);

CREATE TABLE block (
  id         INT AUTO_INCREMENT PRIMARY KEY,
  name       VARCHAR(20),
  start_date DATE,
  end_date   DATE
);

CREATE TABLE student (
  id       INT AUTO_INCREMENT PRIMARY KEY,
  name     VARCHAR(100),
  email    VARCHAR(100),
  dob      DATE,
  entry_id INT,
  track    ENUM ('FPP', 'MPP')
);

CREATE TABLE professor (
  id    INT AUTO_INCREMENT PRIMARY KEY,
  name  VARCHAR(100),
  title VARCHAR(100),
  email VARCHAR(100),
  phone VARCHAR(20),
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
  enrolled  INT
);