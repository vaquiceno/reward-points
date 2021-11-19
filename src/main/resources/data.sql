insert into user(name) values ('pep1');
insert into user(name) values ('pep2');

insert into transaction(value, created, user_id) values (500, NOW() - 300 , 1);
insert into transaction(value, created, user_id) values (100, NOW() - 200, 1);
insert into transaction(value, created, user_id) values (300, NOW() - 100, 2);
insert into transaction(value, created, user_id) values (400, NOW() - 50, 2);