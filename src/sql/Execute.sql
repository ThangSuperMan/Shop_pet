/* \d product_flavors; */
/* \d pet_food_flavors; */

/* Get flavors for pet's food product */
/* Input: product id */
/* select a.id, b.pet_food_flavor_id, title, pet_food_flavors.name */ 
/* from products a */ 
/* left join product_flavors b */ 
/* on a.id = b.product_id */
/* left join pet_food_flavors */
/* on pet_food_flavors.id = b.pet_food_flavor_id; */

select a.id, b.pet_food_flavor_id, title, pet_food_flavors.name 
from products a 
left join product_flavors b 
on a.id = b.product_id
left join pet_food_flavors
on pet_food_flavors.id = b.pet_food_flavor_id
where a.id = 1;

\d product_flavors;

select count(*) total_flavor 
from product_flavors
where product_id = 1;
