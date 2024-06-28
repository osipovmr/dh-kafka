create table if not exists message
(
    uuid          uuid primary key,
    offset_number int          not null,
    "partition"   int          not null,
    message       varchar(255) not null
);
