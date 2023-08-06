insert into rating(id, product_id, score, created_by, created_date, last_modified_by, last_modified_date)
values (nextval('rating_sequence_generator'), 1000, 0, 'system', now(), 'system', now()),
       (nextval('rating_sequence_generator'), 1000, 1, 'system', now(), 'system', now()),
       (nextval('rating_sequence_generator'), 1000, 2, 'system', now(), 'system', now()),
       (nextval('rating_sequence_generator'), 1000, 3, 'system', now(), 'system', now()),
       (nextval('rating_sequence_generator'), 1000, 4, 'system', now(), 'system', now()),
       (nextval('rating_sequence_generator'), 1000, 5, 'system', now(), 'system', now())