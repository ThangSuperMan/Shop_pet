-- set timezone = 'Asia/Saigon';
-- We have to set timezone before select the column field data type timezone 
-- Genereate uuid (random id)
create extension if not exists "uuid-ossp";

/*
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

*/

create type role_enum as enum ('ADMIN', 'USER');

create table if not exists users (
    id uuid default uuid_generate_v4(),
    username varchar(45) not null,
    password char(60) not null,
    email varchar(45) not null,
    role role_enum default 'USER' not null,
    created_at timestamptz not null default now(),
    updated_at timestamptz not null default now(),
    unique(username),
    primary key (id)
);

insert into users (username, password, email, role) values ('thangphan', '1', 'thang@gmail.com', 'ADMIN');
insert into users (username, password, email, role) values ('ngocphan', '1', 'ngoc@gmail.com', 'USER');

create table if not exists brands (
  id uuid default uuid_generate_v4(),
  name varchar(100),
  unique(name),
  primary key(id)
);

insert into brands (id, name) values ('91bed489-e391-4e8f-b96e-5ca646741ab8' ,'ABC'); 
insert into brands (id, name) values ('132d84bb-8c2b-4412-bc83-310067249ba3' ,'AFC'); 
insert into brands (id, name) values ('0f8f5435-d5f8-4b04-b39e-e692d1faf9e3', 'Peppsi'); 

create table if not exists inventory (
    id uuid default uuid_generate_v4(),
    quantity smallint not null,
    primary key (id)
);

insert into inventory (id, quantity) values ('20bed489-e391-4e8f-b98e-2ca646741cb8', 5);
insert into inventory (id, quantity) values ('67faae64-9807-4202-b0c5-c44cc4a1bd8e', 17);
insert into inventory (id, quantity) values ('75a001a2-185f-453e-aed3-4831815ce6c8', 17);

create type type_money_enum as enum ('USD', 'VN');

create table if not exists products (
    id uuid default uuid_generate_v4(),
    brand_id uuid,
    inventory_id uuid not null,
    title varchar(150),
    price real not null,
    image_url varchar(100),
    money_type type_money_enum default 'USD' not null,
    created_at timestamptz not null default now(),
    updated_at timestamptz not null default now(),
    unique(title),
    primary key (id),
    constraint fk_product_brand
      foreign key(brand_id)
        references brands(id)
        on delete set null,
    constraint fk_product_inventory
      foreign key(brand_id)
        references brands(id)
        on delete set null
);

insert into products (brand_id, inventory_id, title, price, image_url, money_type) 
values ('91bed489-e391-4e8f-b96e-5ca646741ab8', '20bed489-e391-4e8f-b98e-2ca646741cb8', 'Pet Dog Bed Sofa Mats Pet Products', 53.23, 'https://image.com', 'USD');
