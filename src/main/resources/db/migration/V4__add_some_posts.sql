insert into Person (id, first_name, last_name, e_mail, phone, reg_date, birth_date, photo, about, city_id, country_id, messages_permission, last_online_time, is_blocked, is_approved, password) values (4, 'Karrah', 'Lukins', 'klukins0@live.com', '678-829-4880', '2020-07-29 09:29:41', '2014-08-13', 'img.jpg', 'vel nulla eget eros elementum pellentesque quisque porta volutpat', 63, 90, 'ALL', '2014-11-25 19:01:57', true, true, 'FE1oqiTMvV');
insert into Person (id, first_name, last_name, e_mail, phone, reg_date, birth_date, photo, about, city_id, country_id, messages_permission, last_online_time, is_blocked, is_approved, password) values (15, 'Christen', 'Workes', 'cworkes1@posterous.com', '563-436-7500', '2019-09-30 23:02:45', '2015-07-09', 'img.jpg', 'vel augue vestibulum ante ipsum primis in', 68, 36, 'ALL', '2018-07-13 18:26:47', true, false, 'EGEpn1C');

insert into Post (id, time, author_id, title, post_text, is_blocked) values (1, '2019-11-24 08:36:01', 15, 'Neptune''s Daughter', 'in lacus curabitur at ipsum ac tellus semper interdum mauris ullamcorper purus sit amet', false);
insert into Post (id, time, author_id, title, post_text, is_blocked) values (2, '2020-05-03 16:40:07', 4, 'Blade Runner', 'commodo placerat praesent blandit nam nulla integer pede justo lacinia', true);

insert into City (id, city, country_id) VALUES (63, 'Moscow', 90);
insert into City (id, city, country_id) VALUES (68, 'Tokio', 36);


insert into Country (id, country) VALUES (36, 'Japan');
insert into Country (id, country) VALUES (90, 'Russia');