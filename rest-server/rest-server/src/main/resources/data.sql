insert into user values (2001, 'cocomo@gmail.com', sysdate(), 'passwd1', '010-1111-1111', 'cocomoID', 'coco');
insert into user values (2002, 'kokomo@gmail.com', sysdate(), 'passwd2', '010-2222-2222', 'kokomoID', 'koko');
insert into user values (2003, 'mokoko@gmail.com', sysdate(), 'passwd3', '010-3333-3333', 'mokokoID', 'moko');
insert into auth_user values (2001, 444, 444, 0, null, null, 'cocomoID');
insert into auth_user values (2002, 444, 444, 0, null, null, 'kokomoID');
insert into auth_user values (2003, 444, 444, 0, null, null, 'mokokoID');