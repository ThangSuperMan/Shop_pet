create table if not exists books (
    id serial,
    title varchar(45) not null,
    author varchar(45) not null,
    price real not null,
    primary key (id)
);
