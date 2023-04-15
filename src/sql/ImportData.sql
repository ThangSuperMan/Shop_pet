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
  id serial,
  name varchar(100),
  unique(name),
  primary key(id)
);

insert into brands (id, name) values ('1', 'ABC'); 
insert into brands (id, name) values ('2', 'AFC'); 
insert into brands (id, name) values ('3', 'Peppsi'); 

create table if not exists inventory (
    id serial,
    quantity smallint not null,
    primary key (id)
);

insert into inventory (id, quantity) values ('1', 5);
insert into inventory (id, quantity) values ('2', 17);
insert into inventory (id, quantity) values ('3', 17);

create type type_money_enum as enum ('USD', 'VND');

create table if not exists products (
    id serial,
    brand_id serial,
    inventory_id serial not null,
    related_images_product_id serial,
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
      foreign key(inventory_id)
        references inventory(id)
        on delete set null
);

insert into products (id, brand_id, inventory_id, title, price, image_url, money_type) values 
(1, 1, 2, 'Pet Dog Bed Sofa Mats Pet Products', 53.23, 'https://image.com', 'USD'),
(2, 3, 2, 'Toy for cat', 13.24, 'https://image.com', 'VND');

create table if not exists product_detail (
  id serial, 
  produce_id serial, 
  description varchar(500),
  primary key (id),
  constraint fk_product_detail_product
    foreign key(product_id)
      references products(id)
      on delete set null
)

insert into product_detail (id, product_id, description) values 
(1, 1, 'Description of product number 1'),
(2, 2, 'Description of product number 2');

create table if not exists product_types (
  id serial, 
  product_detail_id serial,
  name varchar(50),
  primary key (id),
  unique (name),
  constraint fk_product_detail_product
    foreign key(product_detail_id)
      references product_detail(id)
      on delete set null
)

insert into product_types (id, product_detail_id, name) values 
(1, 1, 'Coffee'),
(2, 1, 'Gray'),
(3, 1, 'Green'),

create table if not exists product_sizes (
  id serial, 
  product_detail_id serial,
  name varchar(50),
  primary key (id),
  unique (name),
  constraint fk_product_detail_product
    foreign key(product_detail_id)
      references product_detail(id)
      on delete set null
)

insert into product_sizes (id, product_detail_id, name) values 
(1, 1, 'L 60x45cm'),
(2, 1, 'M 50x40cm'),

create table if not exists related_images_product (
  id serial,
  product_id serial,
  image_url varchar(100),
  constraint fk_related_images_product
    foreign key(product_id)
      references products(id)
      on delete set null
);
