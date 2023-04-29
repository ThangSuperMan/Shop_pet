-- set timezone = 'Asia/Saigon';
-- We have to set timezone before select the column field data type timezone 
-- Genereate uuid (random id)
-- create database if not  exists shop_pet;

-- select to_char(created_at, 'YYYY/MM/dd HH24:MI:SS') from users;
-- Resources: https://www.postgresql.org/docs/current/functions-formatting.html

-- Get number of table in database
-- Cmd: select count(*) from information_schema.tables where table_schema = 'public';

/*

Description: 11 total number of tables in the database right away.

*/

-- use shop_pet;

create extension if not exists "uuid-ossp";

-- ENUM
create type role_enum as enum ('ADMIN', 'USER');
create type unit_product_enum as enum ('lb', 'bag', 'kg');
create type type_money_enum as enum ('USD', 'VND');

create table if not exists users (
  id uuid default uuid_generate_v4(),
  username varchar(45) not null,
  password char(60) not null,
  email varchar(45),
  phone varchar(20) default null,
  avatar_url varchar(200),
  gg_id varchar(50) default null,
  fb_id varchar(50) default null,
  role role_enum default 'USER' not null,
  created_at timestamptz not null default now(),
  updated_at timestamptz not null default now(),
  primary key(id)
);

insert into users (id, username, avatar_url, email, password, role) values 
('5c98778f-692f-4c94-a564-cb45662bfe41', 'thangphan', 'https://images-na.ssl-images-amazon.com/images/S/influencer-profile-image-prod/logo/influencer-275f68b5_1662012947804_original._CR0,3,576,576_._FMjpg_.jpeg', 'thangphan@gmail.com', '$2a$10$UVAD4O3IGOS0q1Ak1mmgp.6SdpUPQDzpukLkWAJ/akg9HprTVtEVO','ADMIN');

create table if not exists brands (
  id serial,
  name varchar(100),
  unique(name),
  primary key(id)
);

insert into brands (id, name) values ('1', 'Blue Buffalo'); 
insert into brands (id, name) values ('2', 'Cesar'); 
insert into brands (id, name) values ('3', 'Friskies'); 
insert into brands (id, name) values ('4', 'Beneful'); 
insert into brands (id, name) values ('5', 'IRIS USA, Inc.'); 

create table if not exists inventory (
  id serial,
  quantity smallint not null,
  primary key(id)
);

insert into inventory (id, quantity) values ('1', 5),
('2', 17),
('3', 17);

create table if not exists products (
  id serial,
  brand_id serial not null,
  inventory_id serial not null,
  title varchar(150),
  price numeric(10, 2) not null,
  image_url varchar(100) not null,
  money_type type_money_enum default 'USD' not null,
  created_at timestamptz not null default now(),
  updated_at timestamptz not null default now(),
  unique(title),
  primary key(id), constraint fk_product_brand foreign key(brand_id) references brands(id) on delete set null,
  constraint fk_product_inventory foreign key(inventory_id) references inventory(id) on delete set null
);

create table if not exists prices (
  id serial,
  product_id integer, 
  price NUMERIC(10, 2)
);

insert into products (id, brand_id, inventory_id, title, price, image_url, money_type) values 
(1, 1, 2, 'Blue Buffalo Life Protection Formula Natural Adult Dry Dog Food, Chicken and Brown Rice 5-lb Trial Size Bag', 24.5 , 'https://m.media-amazon.com/images/I/817jbhS0QpL._AC_SX679_.jpg', 'USD'),
(2, 2, 1, 'CESAR Wet Dog Food Classic Loaf in Sauce Poultry Variety Pack,. Easy Peel Trays with Real Chicken, Turkey or Duck, 3.5 Ounce', 13.24, 'https://m.media-amazon.com/images/I/71LtXuEA1sL._AC_UL320_.jpg', 'USD'),
(3, 1, 2, 'PetLab Co. Probiotics for Dogs, Support Gut Health & Seasonal Allergies - Pork Flavor Soft Chew - 30 Soft Chews - Packaging May Vary', 31.286, 'https://m.media-amazon.com/images/I/91WYcbT7uQL._AC_UL640_FMwebp_QL65_.jpg', 'USD'),
(4, 2, 1, 'TEMPTATIONS Classic Crunchy and Soft Cat Treats Tasty Chicken Flavor, 30 oz. Tub (Packaging May Vary)', 19.3, 'https://m.media-amazon.com/images/I/81xLTrwlNbL._AC_UL640_FMwebp_QL65_.jpg', 'USD');

create table if not exists product_images (
  id serial,
  product_id integer not null,
  url varchar(100),
  alt_text text not null default 'Default alt',
  primary key (id),
  constraint fk_product_images foreign key(product_id) references products(id) on delete set null
);

insert into product_images (id, product_id, url) values 
-- Product 1
(1, 1, 'https://m.media-amazon.com/images/I/51e+bL+tjuS._AC_US40_.jpg'),
(2, 1, 'https://m.media-amazon.com/images/I/51y1aJEZixS._AC_US40_.jpg'),
(3, 1, 'https://m.media-amazon.com/images/I/71CLm0BKHmL._AC_SX466_.jpg'),

-- Product 2
(4, 2, 'https://m.media-amazon.com/images/I/81sBISbHS3L._AC_SX569_.jpg'),
(5, 2, 'https://m.media-amazon.com/images/I/81idM50IhKL._AC_SX569_.jpg'),
(6, 2, 'https://m.media-amazon.com/images/I/81avyiuwhYL._AC_SX569_.jpg');

create table if not exists product_sizes (
  id serial,
  product_id integer not null,
  size text not null,
  unit unit_product_enum default 'lb' not null,
  unit_weight numeric(10,2) not null,
  constraint fk_product_sizes foreign key(product_id) references products(id) on delete set null
);

create table if not exists product_detail (
  id serial, 
  product_id integer not null, 
  description varchar(2500),
  primary key (id),
  constraint fk_product_detail_product foreign key(product_id) references products(id) on delete set null
);

insert into product_detail (id, product_id, description) values 
(1, 1, 'Twenty-Four (24) 3 oz. Cans - Purina Fancy Feast Gravy Wet Cat Food Variety Pack, Gravy Lovers Poultry & Beef Feast Collection'),
(2, 2, 'Chicken. turkey and beef flavors cats love. 100 percent complete and balanced nutrition'),
(3, 3, 'Tender, delicate bites for a tempting texture. Essential vitamins and minerals to support her overall health');

create table if not exists reviews (
  id serial,
  product_id serial,
  user_id uuid not null,
  title text not null,
  body text not null,
  rating smallint not null check (rating between 1 and 5),
  created_at timestamptz not null default now(),
  updated_at timestamptz not null default now(),
  primary key(id),
  constraint fk_reviews_product foreign key(product_id) references products(id) on delete cascade,
  constraint fk_reviews_user foreign key(user_id) references users(id) on delete cascade
);

insert into reviews (id, product_id, user_id, title, body, rating) values 
(1, 1, '5c98778f-692f-4c94-a564-cb45662bfe41', 'Great product!', 'I love this product. It works really well and looks great too.', 5),
(2, 2, '5c98778f-692f-4c94-a564-cb45662bfe41', 'Not what I expected', 'This product did not meet my expectations. It was difficult to use and did not work as advertised.', 2),
(3, 3, '5c98778f-692f-4c94-a564-cb45662bfe41', 'Good value for the price', 'This product is a great value for the price. It works well and is very affordable.', 4);

-- CATEGORIES
create table if not exists pet_food_flavors (
  id serial,
  name varchar(30) not null,
  unique(name),
  primary key(id)
);

insert into pet_food_flavors (id, name) values 
(1, 'Apple'),
(2, 'Beef'),
(3, 'Bison'),
(4, 'Blueberry'),
(5, 'Catnip'),
(6, 'Cheese'),
(7, 'Chicken'),
(8, 'Duc'),
(9, 'Fish'),
(10, 'Lamb'),
(11, 'Liver'),
(12, 'Milk'),
(13, 'Pork'),
(14, 'Pumpkin'),
(15, 'Rabbit'),
(16, 'Rice'),
(17, 'Salmon'),
(18, 'Seafood'),
(19, 'Shrimp'),
(20, 'Sweet Potato'),
(21, 'Tuna'),
(22, 'Turkey'),
(23, 'Vegetable'),
(24, 'Venison');

create table if not exists product_flavors (
  product_id integer,
  pet_food_flavor_id integer,
  primary key (product_id, pet_food_flavor_id),
  constraint fk_product_flavor foreign key(product_id) references products(id) on delete set null,
  constraint fk_product_flavors_pet_food_flavor foreign key(pet_food_flavor_id) references pet_food_flavors(id) on delete set null
);

insert into product_flavors (product_id, pet_food_flavor_id) values 
-- First food product
(1, 1),
(1, 10),
(1, 22),

-- Second food product
(2, 12),
(2, 7);
