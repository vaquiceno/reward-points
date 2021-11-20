insert into user(name) values ('pep1');
insert into user(name) values ('pep2');

insert into transaction(value, created, user_id) values (80, NOW() - 300 , 1);
insert into transaction(value, created, user_id) values (40, NOW() - 100, 2);
insert into transaction(value, created, user_id) values (0, NOW() - 50, 2);