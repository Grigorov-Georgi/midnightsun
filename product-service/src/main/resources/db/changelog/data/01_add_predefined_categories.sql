insert into category(id, name, created_by, created_date, last_modified_by, last_modified_date)
values (nextval('category_sequence_generator'), 'Electronics', 'system', now(), 'system', now()),
       (nextval('category_sequence_generator'), 'Home and Kitchen', 'system', now(), 'system', now()),
       (nextval('category_sequence_generator'), 'Books', 'system', now(), 'system', now()),
       (nextval('category_sequence_generator'), 'Health and Personal Care', 'system', now(), 'system', now()),
       (nextval('category_sequence_generator'), 'Sports and Outdoors', 'system', now(), 'system', now())