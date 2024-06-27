create table if not exists students
(
    id      int primary key,
    name    varchar(50) not null,
    lessons int         null
);
insert into students (id, name, lessons)
values (1, 'Adlan', 10),
       (2, 'Semen', 20),
       (3, 'Alex', 30);