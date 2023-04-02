set timezone = 'Asia/Saigon';

create table if not exists books (
    id serial,
    title varchar(45) not null,
    author varchar(45) not null,
    price real not null,
    created_at timestamp not null default now(),
    unique(title),
    primary key (id)
);

insert into books (title, author, price, created_at)
values ('Love for the imperfect things', 'Thang Jenny', 2.400, now());

create table if not exists users (
    id serial,
    username varchar(45) not null,
    password varchar(75) not null,
    email varchar(45) not null,
    unique(username),
    primary key (id)
);

insert into users(username, password, email)
values ('thangphan', '1', 'thangjenny2002@gmail.com');

create table if not exists roles (
    id serial,
    name varchar(5) not null,
    primary key(id)
);

-- Many to many relational 
-- between users and roles tables
-- One user can be admin and normal user at the same time
create table if not exists user_roles (
    user_id int not null,
    role_id int not null,
    grant_date timestamp,
    primary key (user_id, role_id),
    foreign key (user_id) references users (id),
    foreign key (role_id) references roles (id)
);