insert into product(id, name, description, category_id, price, quantity, weight, length, width, height, created_by, created_date, last_modified_by, last_modified_date)
values (nextval('product_sequence_generator'), 'product 1', 'gravity is actually wave', 1000, 11, 1000, 5, 10, 10, 10, 'system', now(), 'system', now()),
       (nextval('product_sequence_generator'), 'product 2', 'gravity is actually wave', 1001, 22, 2000, 5, 20, 20, 20, 'system', now(), 'system', now()),
       (nextval('product_sequence_generator'), 'product 3', 'gravity is actually wave', 1002, 33, 3000, 5, 30, 30, 30, 'system', now(), 'system', now()),
       (nextval('product_sequence_generator'), 'product 4', 'gravity is actually wave', 1003, 44, 4000, 5, 40, 40, 40, 'system', now(), 'system', now()),
       (nextval('product_sequence_generator'), 'product 5', 'gravity is actually wave', 1004, 55, 5000, 5, 50, 50, 50, 'system', now(), 'system', now())