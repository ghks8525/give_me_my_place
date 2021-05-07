CREATE TABLE student (
    student_id VARCHAR(20),
    name VARCHAR(20),
    level INTEGER DEFAULT 1,
    PRIMARY KEY (student_id)
);

CREATE TABLE reservation(
   student_id varchar(20),
    seat_id integer,
    start_time datetime,
    end_time datetime,
    primary key(student_id),
    foreign key(student_id) references student(student_id)
);

INSERT INTO student values(
student_id=18011630,
name="김수빈",
level=2
)