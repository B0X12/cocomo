create table user (id integer not null, email varchar(255), join_date timestamp, name varchar(255), passwd varchar(255), primary key (id));
insert into user values (1001, 'cocomo@gmail.com', sysdate(), 'cocomo', 'passwd1');
insert into user values (1002, 'kokomo@gmail.com', sysdate(), 'kokomo', 'passwd2');
insert into user values (1003, 'mokoko@gmail.com', sysdate(), 'mokoko', 'passwd3');