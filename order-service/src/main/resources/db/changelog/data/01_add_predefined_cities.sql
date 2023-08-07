insert into city(id, name, created_by, created_date, last_modified_by, last_modified_date)
values (nextval('city_sequence_generator'), 'Sofia', 'system', now(), 'system', now()),
       (nextval('city_sequence_generator'), 'Varna', 'system', now(), 'system', now()),
       (nextval('city_sequence_generator'), 'Burgas', 'system', now(), 'system', now()),
       (nextval('city_sequence_generator'), 'Plovdiv', 'system', now(), 'system', now()),
       (nextval('city_sequence_generator'), 'Stara Zagora', 'system', now(), 'system', now())