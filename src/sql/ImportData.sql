-- set timezone = 'Asia/Saigon';
-- We have to set timezone before select the column field data type timezone 
-- Genereate uuid (random id)
-- create database if not  exists shop_pet;

-- select to_char(created_at, 'YYYY/MM/dd HH24:MI:SS') from users;
-- Resources: https://www.postgresql.org/docs/current/functions-formatting.html

-- Get number of table in database
-- Cmd: select count(*) from information_schema.tables where table_schema = 'public';
-- Select the datetime column with format and timezone
-- SELECT to_char(updated_at AT TIME ZONE 'Asia/Ho_Chi_Minh', 'YYYY-MM-DD HH24:MI:SS TZ') from products where id = 1;

-- Get all the order based on the user name
-- Cmd: select * 
-- from (
--   select username, payment_status, orders.id as order_id from users 
--   left join orders on orders.user_id = users.id
--   and payment_status = 'unpaid'
--   where username = 'ngocphan') as joined_table 
--   left join order_items 
--   on order_items.order_id = joined_table.order_id;
/*

Description: 14 total number of tables in the database right away.

*/

-- use shop_pet;

create extension if not exists "uuid-ossp";

-- ENUM
create type role_enum as enum ('ADMIN', 'USER');
create type unit_product_enum as enum ('lb', 'bag', 'kg');
create type type_money_enum as enum ('USD', 'VND');
create type payment_status_enum as enum ('unpaid', 'paid', 'pending', 'refunded');

-- FUNCTIONS
create or replace function update_for_updated_at_column()
RETURNS trigger as $$
begin
  new.updated_at = now();
  return new;
end;
$$ language plpgsql;


create table if not exists users (
  id uuid default uuid_generate_v4(),
  username varchar(45) not null,
  unique (username),
  password char(60) not null,
  email varchar(45),
  address varchar(200),
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
('5c98778f-692f-4c94-a564-cb45662bfe41', 'thangphan', 'https://images-na.ssl-images-amazon.com/images/S/influencer-profile-image-prod/logo/influencer-275f68b5_1662012947804_original._CR0,3,576,576_._FMjpg_.jpeg', 'thangphan@gmail.com', '$2a$10$UVAD4O3IGOS0q1Ak1mmgp.6SdpUPQDzpukLkWAJ/akg9HprTVtEVO','ADMIN'),
('250d51ea-3021-4ad6-a5c7-3ad47756d413', 'ngocphan', 'https://images-na.ssl-images-amazon.com/images/S/influencer-profile-image-prod/logo/influencer-275f68b5_1662012947804_original._CR0,3,576,576_._FMjpg_.jpeg', 'ngocphan@gmail.com', '$2a$10$udD2nZ30k95m2JN8te3oQ.4Wugu5UdDDMGOf1fx6PY1qwLajT2LO6','USER');

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

create table if not exists category_pet_supplies (
  id serial,
  name varchar(30),
  unique(name),
  primary key (id)
);

insert into category_pet_supplies (id, name) values 
(1, 'Dogs'),
(2, 'Cats'),
(3, 'Fish & Aquatic Pets'),
(4, 'Birds'),
(5, 'Horses'),
(6, 'Small Animals');

create table if not exists products (
  id serial,
  brand_id serial not null,
  inventory_id serial not null,
  category_pet_supplie_id serial not null,
  title varchar(150),
  price numeric(10, 2) not null,
  image_url varchar(100) not null,
  money_type type_money_enum default 'USD' not null,
  created_at timestamptz not null default now(),
  updated_at timestamptz not null default now(),
  unique(title),
  primary key(id),
  constraint fk_product_brand foreign key(brand_id) references brands(id) on delete set null,
  constraint fk_product_inventory foreign key(inventory_id) references inventory(id) on delete set null,
  constraint fk_product_category_pet_supplie foreign key(category_pet_supplie_id) references category_pet_supplies(id) on delete set null
);

-- TRIGGER FOR PRODUCT TABLE 
create trigger products_updated_at
before update on products
for each row
execute function update_for_updated_at_column();

create table if not exists prices (
  id serial,
  product_id integer, 
  price NUMERIC(10, 2)
);

insert into products (id, brand_id, inventory_id, category_pet_supplie_id, title, price, image_url, money_type) values 
(1, 1, 2, 1, 'Blue Buffalo Life Protection Formula Natural Adult Dry Dog Food, Chicken and Brown Rice', 24.5 , 'https://m.media-amazon.com/images/I/817jbhS0QpL._AC_SX679_.jpg', 'USD'),
(2, 2, 1, 1, 'CESAR Wet Dog Food Classic Loaf in Sauce Poultry Variety Pack,. Easy Peel Trays with Real Chicken, Turkey or Duck, 3.5 Ounce', 13.24, 'https://m.media-amazon.com/images/I/71LtXuEA1sL._AC_UL320_.jpg', 'USD'),
(3, 1, 2, 2, 'GREENIES Original TEENIE Natural Dog Dental Care Chews Oral Health Dog Treats', 31.286, 'https://m.media-amazon.com/images/I/91WYcbT7uQL._AC_UL640_FMwebp_QL65_.jpg', 'USD'),
(4, 2, 1, 2, 'TEMPTATIONS Classic Crunchy and Soft Cat Treats Tasty Chicken Flavor, 30 oz. Tub (Packaging May Vary)', 19.3, 'https://m.media-amazon.com/images/I/81xLTrwlNbL._AC_UL640_FMwebp_QL65_.jpg', 'USD'),
(5, 4, 3, 1, 'Blue Buffalo Wilderness Rocky Mountain Recipe High Protein Grain Free, Natural Adult Dry Dog Food', 56.99, 'https://m.media-amazon.com/images/I/81lBQFJIMmL._AC_UL640_QL65_.jpg', 'USD'),
(6, 2, 2, 4, 'Royal Canin Small Adult Dry Dog Food', 41.99, 'https://m.media-amazon.com/images/I/81ulRQkt+7L._AC_UL640_FMwebp_QL65_.jpg', 'USD'),
(7, 4, 1, 4, 'Purina Pro Plan Small Breed Dry Dog Food, FOCUS Chicken & Rice Formula', 47.99, 'https://m.media-amazon.com/images/I/81YYm5WFAdL._AC_UL640_FMwebp_QL65_.jpg', 'USD'),
(8, 1, 2, 5, 'Iams Proactive Health Adult Large Breed Dry Dog Food Chicken', 41.99, 'https://m.media-amazon.com/images/I/810vMEI+w3L._AC_UL640_FMwebp_QL65_.jpg', 'USD'),
(9, 2, 3, 6, 'Pedigree Adult Dry Dog Food, Chicken & Steak', 23.99, 'https://m.media-amazon.com/images/I/513Zc3U96TL._AC_UL640_FMwebp_QL65_.jpg', 'USD'),
(10, 2, 3, 6, 'Nutro Wholesome Essentials Adult Dry Dog Food Farm Raised Chicken, Brown Rice & Sweet Potato Recipe', 21.99, 'https://m.media-amazon.com/images/I/71Azp-504uL._AC_UL640_FMwebp_QL65_.jpg', 'USD'),
(11, 2, 3, 6, 'Purina ONE Natural Dry Dog Food, SmartBlend Chicken & Rice Formula', 21.99, 'https://m.media-amazon.com/images/I/8199hsVcgHL._AC_UL640_QL65_.jpg', 'USD'),
(12, 3, 2, 2, 'Cesar Small Breed Dry Dog Food Filet Mignon Flavor with Spring Vegetables Garnish', 17.99, 'https://m.media-amazon.com/images/I/91B4JZnSA6L._AC_UL640_FMwebp_QL65_.jpg', 'USD'),
(13, 3, 2, 2, 'Van Ness 10 Pound Food Container with Fresh Tite Seal (FC10) white', 17.99, 'https://m.media-amazon.com/images/I/91V1l27TmCS._AC_UL640_QL65_.jpg', 'USD'),
(14, 3, 2, 2, 'Purina Moist & Meaty Dog Food, Steak Flavor', 17.99, 'https://m.media-amazon.com/images/I/817CK5y6pyL._AC_UL640_QL65_.jpg', 'USD'),
(15, 3, 2, 2, 'Manna Pro Crushed Oyster Shell Calcium Supplement for Laying Hens, Chicken Feed for Egg Laying Chickens', 17.99, 'https://m.media-amazon.com/images/I/81XjNiiisDL._AC_UL640_QL65_.jpg', 'USD'),
(16, 3, 2, 2, 'Ultra Micro Crystals Cat Litter 5 pounds', 17.99, 'https://m.media-amazon.com/images/I/61X5Rem3qLL._AC_UL640_QL65_.jpg', 'USD'),
(17, 3, 2, 2, 'Virbac CET VEGGIEDENT FR3SH Tartar Control Chews for Dogs', 17.99, 'https://m.media-amazon.com/images/I/91B4JZnSA6L._AC_UL640_FMwebp_QL65_.jpg', 'USD'),
(18, 3, 2, 2, 'Purina Friskies Gravy Wet Cat Food Variety Pack, Extra Gravy Chunky', 17.99, 'https://m.media-amazon.com/images/I/81qBrPoSKpL._AC_UL640_QL65_.jpg', 'USD'),
(19, 3, 2, 2, 'Blue Buffalo BLUE Bits Natural Soft Moist Training Dog Treats, Beef Recipe 19 oz Bag', 17.99, 'https://m.media-amazon.com/images/I/81zpVGn9E+L._AC_UL640_QL65_.jpg', 'USD'),
(20, 3, 2, 2, 'FELINE GREENIES Adult Dental Cat Treats, Savory Salmon Flavor, 9.75 oz. Tub', 299, 'https://m.media-amazon.com/images/I/819y0lLPQsL._AC_UL640_QL65_.jpg', 'USD'),
(21, 3, 2, 2, 'Purina Fancy Feast Gravy Lovers Chicken Feast in Gravy Gourmet Cat Food in Wet Cat Food Gravy - (24) 3 oz. Cans', 5.99, 'https://m.media-amazon.com/images/I/71csCOmueiL._AC_UL640_QL65_.jpg', 'USD'),
(22, 3, 2, 2, 'Blue Dog Bakery Natural Dog Treats, Softies, Peanut Butter Flavor, 16.2oz Bag, 1 Bag', 17.99, 'https://m.media-amazon.com/images/I/612RC6H3yuL._AC_UL640_QL65_.jpg', 'USD'),
(23, 3, 2, 2, 'Resolve Ultra Pet Stain & Odor Remover Spray, 32oz', 17.99, 'https://m.media-amazon.com/images/I/819TnlK40bL._AC_UL640_QL65_.jpg', 'USD'),
(24, 3, 2, 2, 'CESAR Small Breed Dry Dog Food Filet Mignon Flavor with Spring Vegetables Garnish Dog Kibble, 12 lb. Bag', 17.99, 'https://m.media-amazon.com/images/I/91MIYPd26AS._AC_UL640_QL65_.jpg', 'USD'),
(25, 1, 2, 1, 'Blue Buffalo ife Protection Formula Natural Adult Dry Dog Food, Chicken and Brown Rice 5lb Trial Size Bag', 24.5 , 'https://m.media-amazon.com/images/I/81DNZAmP0BL._AC_SX679_.jpg', 'USD'),
(26, 2, 1, 1, 'CESAR Wet Do Food Classic Loaf in Sauce Poultry Variety Pack,. Easy Peel Trays with Real Chicken, Turkey or Duck, 3.5 Ounce', 13.24, 'https://m.media-amazon.com/images/I/71sghJOu2ML._AC_UL640_FMwebp_QL65_.jpg', 'USD'),
(27, 1, 2, 2, 'GREENES Original TEENIE Natural Dog Dental Care Chews Oral Health Dog Treats', 31.286, 'https://m.media-amazon.com/images/I/61rV7AbNcQL._AC_UL640_FMwebp_QL65_.jpg', 'USD'),
(28, 2, 1, 2, 'TEMPTTIONS Classic Crunchy and Soft Cat Treats Tasty Chicken Flavor, 30 oz. Tub (Packaging May Vary)', 19.3, 'https://m.media-amazon.com/images/I/81xLTrwlNbL._AC_UL640_FMwebp_QL65_.jpg', 'USD'),
(29, 4, 3, 1, 'Blue Bffalo Wilderness Rocky Mountain Recipe High Protein Grain Free, Natural Adult Dry Dog Food', 56.99, 'https://m.media-amazon.com/images/I/71qVdHKkVjL._AC_UL640_QL65_.jpg', 'USD'),
(30, 2, 2, 4, 'Royal anin Small Adult Dry Dog Food', 41.99, 'https://m.media-amazon.com/images/I/61C31hpsNzL._AC_UL640_QL65_.jpg', 'USD'),
(31, 4, 1, 4, 'Purina Pro Plan SmallBeed Dry Dog Food, FOCUS Chicken & Rice Formula', 47.99, 'https://m.media-amazon.com/images/I/81YYm5WFAdL._AC_UL640_FMwebp_QL65_.jpg', 'USD'),
(32, 1, 2, 5, 'Iams Proactive Heat Adult Large Breed Dry Dog Food Chicken', 41.99, 'https://m.media-amazon.com/images/I/810vMEI+w3L._AC_UL640_FMwebp_QL65_.jpg', 'USD'),
(33, 2, 3, 6, 'Pedigree Adult Dryog Food, Chicken & Steak', 23.99, 'https://m.media-amazon.com/images/I/513Zc3U96TL._AC_UL640_FMwebp_QL65_.jpg', 'USD'),
(34, 2, 3, 6, 'Nutro Wholesome Eentials Adult Dry Dog Food Farm Raised Chicken, Brown Rice & Sweet Potato Recipe', 21.99, 'https://m.media-amazon.com/images/I/71Azp-504uL._AC_UL640_FMwebp_QL65_.jpg', 'USD'),
(35, 2, 3, 6, 'Purina ONE NatuaDry Dog Food, SmartBlend Chicken & Rice Formula', 21.99, 'https://m.media-amazon.com/images/I/8199hsVcgHL._AC_UL640_QL65_.jpg', 'USD'),
(36, 3, 2, 2, 'Cesar Small eed Dry Dog Food Filet Mignon Flavor with Spring Vegetables Garnish', 17.99, 'https://m.media-amazon.com/images/I/91B4JZnSA6L._AC_UL640_FMwebp_QL65_.jpg', 'USD'),
(37, 3, 2, 2, 'Van Ness 1Pound Food Container with Fresh Tite Seal (FC10) white', 17.99, 'https://m.media-amazon.com/images/I/91V1l27TmCS._AC_UL640_QL65_.jpg', 'USD'),
(38, 3, 2, 2, 'Purina Moist & Meaty Dog Food, Steak Flavr', 17.99, 'https://m.media-amazon.com/images/I/817CK5y6pyL._AC_UL640_QL65_.jpg', 'USD'),
(39, 3, 2, 2, 'Manna Pro Crushed Oyster Shell Calcium plement for Laying Hens - Chicken Feed for Egg-Laying Chickens - 5 lbs', 17.99, 'https://m.media-amazon.com/images/I/81XjNiiisDL._AC_UL640_QL65_.jpg', 'USD'),
(40, 3, 2, 2, 'Ultra Micro Crystals Cat Litter 5 pous', 17.99, 'https://m.media-amazon.com/images/I/61X5Rem3qLL._AC_UL640_QL65_.jpg', 'USD'),
(41, 3, 2, 2, 'Virbac CET VEGGIEDENT FR3SH Tartar Corol Chews for Dogs', 17.99, 'https://m.media-amazon.com/images/I/91B4JZnSA6L._AC_UL640_FMwebp_QL65_.jpg', 'USD'),
(42, 3, 2, 2, 'Purina Friskies Gravy Wet Cat Food Varty Pack, xtra Gravy Chunky - (24) 5.5 oz. Cans', 17.99, 'https://m.media-amazon.com/images/I/81qBrPoSKpL._AC_UL640_QL65_.jpg', 'USD'),
(43, 3, 2, 2, 'Blue Buffalo BLUE Bs Natural Soft Moist Training Dog Treats, Beef Recipe 19-oz Bag', 17.99, 'https://m.media-amazon.com/images/I/81zpVGn9E+L._AC_UL640_QL65_.jpg', 'USD'),
(44, 3, 2, 2, 'FELINE GREENIES Adu Dental Cat Treats, Savory Salmon Flavor, 9.75 oz. Tub', 299, 'https://m.media-amazon.com/images/I/819y0lLPQsL._AC_UL640_QL65_.jpg', 'USD'),
(45, 3, 2, 2, 'Purinancy Feast Gravy overs Chicken Feast in Gravy Gourmet Cat Food in Wet Cat Food Gravy - (24) 3 oz. Cans', 5.99, 'https://m.media-amazon.com/images/I/71csCOmueiL._AC_UL640_QL65_.jpg', 'USD'),
(46, 3, 2, 2, 'Blue Dog Bakery Natural Dog eats, Softies, Peanut Butter Flavor, .2oz Bag, 1 Bag', 17.99, 'https://m.media-amazon.com/images/I/612RC6H3yuL._AC_UL640_QL65_.jpg', 'USD'),
(47, 3, 2, 2, 'Rolve Ultra Pet Stain & Odor Remover Spray, 32oz', 17.99, 'https://m.media-amazon.com/images/I/819TnlK40bL._AC_UL640_QL65_.jpg', 'USD'),
(48, 3, 2, 2, 'CESAR Small Breed Dry Dog od Filet Mignon Flavor with Spring Vegetables Garnish g Kibble, 12 lb. Bag', 17.99, 'https://m.media-amazon.com/images/I/91MIYPd26AS._AC_UL640_QL65_.jpg', 'USD');

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
  constraint fk_product_detail_product foreign key(product_id) references products(id) on delete cascade
);

/* Notes */
/*
  - Auto insert a ' character if the description store a ' character
  - Add E for escape the \n (break line)
*/
insert into product_detail (id, product_id, description) values 
(1, 1, 'Thirty (30) 3 oz. Cans - Purina Fancy Feast Grain Free Pate Wet Cat Food Variety Pack, Poultry & Beef Collection $$$Made with turkey. chicken or beef. Three different recipes for the variety your cat loves $$$Provides 100 percent complete and balanced nutrition for adult cats. Pleasing pate texture $$$Essential vitamins and minerals in every serving. Backed by Purina, a trusted leader in pet food $$$Delicious tastes she''s sure to adore. Multi-can variety pack makes it easy to stock your pantry'),
(2, 2, 'Tender, delicate bites for a tempting texture. Essential vitamins and minerals to support her overall health'),
(3, 3, 'Small Dog Dental Treats: One GREENIES dental treat a day is all it takes for clean teeth, fresh breath and a happy dog; These irresistibly tasty treats feature a delightfully chewy texture that helps fight plaque and tartar $$$ Supports Oral Health: The chewy texture helps clean teeth, maintain healthy gums and freshen breath to make mouths happy day after day $$$ Find the Right Treat: We make GREENIES Dental Treats for every age from puppy to mature, and every dog size from small to large, plus we offer Grain Free, Weight Management, Blueberry Flavor and Fresh Flavor varieties $$$Made With Natural Ingredients Plus Vitamins, Minerals and Nutrients: GREENIES dental treats are easy to digest with highly soluble ingredients and provide balanced nutrition for adult dogs for healthy and delicious treating $$$Veterinarian Recommended for Dental Care: These dental treats are made from high quality ingredients combined into soft chews your dog will love');

-- updated_at timestamptz not null default now(),

create table if not exists orders (
  id serial,
  user_id uuid not null,
  is_free_shipping bool not null default false,
  payment_status payment_status_enum default 'unpaid' not null,
  total numeric(10,2) not null,
  created_at timestamptz not null default now(),
  updated_at timestamptz not null default now(),
  primary key(id),
  constraint fk_order_user foreign key(user_id) references users(id) on delete set null
);

-- TRIGGER FOR ORDER DETAIL TABLE 
create trigger order_updated_at
before update on orders
for each row
execute function update_for_updated_at_column();

create table if not exists order_items (
  order_id integer not null,
  product_id integer not null,
  quantity smallint not null,
  primary key(order_id, product_id),
  constraint fk_order_item_product foreign key(product_id) references products(id) on delete set null,
  constraint fk_order_item_order foreign key(order_id) references orders(id) on delete set null
);

create table if not exists payments (
  id serial,
  order_id integer,
  method VARCHAR(50),
  amount decimal(10, 2),
  constraint fk_payment_order foreign key(order_id) references orders(id) on delete set null
);

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
