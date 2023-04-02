set timezone = 'Asia/Saigon';

create table if not exists books (
    id serial,
    title varchar(45) not null,
    author varchar(45) not null,
    price real not null,
    created_at timestamp with time zone not null default now(),
    unique(title),
    primary key (id)
);

insert into books (title, author, price, created_at)
values ('Love for the imperfect things', 'Thang Jenny', 2.400, now());

create type role_enum as enum ('ADMIN', 'USER');

create table if not exists users (
    id serial,
    username varchar(45) not null,
    password varchar(75) not null,
    email varchar(45) not null,
    role role_enum default 'USER' not null,
    created_at timestamp not null default now(),
    updated_at timestamp not null default now(),
    unique(username),
    primary key (id)
);

insert into users (username, password, email, role) values ('thangphan', '1', 'thang@gmail.com', 'ADMIN');
insert into users (username, password, email, role) values ('ngocphan', '1', 'ngoc@gmail.com', 'USER');

-- create or replace function on_update_user()
--     return trigger
--     language plpgsql
--     as 
-- $$
-- declare current_date;
-- begin
--     select now() into current_date from users;
--     update users 
--     where updated_at = current_date
-- end;
-- $$;

-- insert into users(username, password, email)
-- values ('thangphan', '1', 'thangjenny2002@gmail.com');

-- drop trigger if exists set_update_user_profile

-- create trigger set_update_user_profile
--     after update 
--     on 

-- create table if not exists roles (
--     id serial,
--     name varchar(5) not null,
--     primary key(id)
-- );

-- Many to many relational 
-- between users and roles tables
-- One user can be admin and normal user at the same time
-- create table if not exists user_roles (
--     user_id int not null,
--     role_id int not null,
--     grant_date timestamp,
--     primary key (user_id, role_id),
--     foreign key (user_id) references users (id),
--     foreign key (role_id) references roles (id)
-- );
