SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE `person`;
TRUNCATE TABLE `city`;
TRUNCATE TABLE `country`;
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO country (id, title) VALUES (1, "Россия");
INSERT INTO city (id, country_id, title) VALUES (1, 1, "Москва");

INSERT INTO `person` (
    id, about, birth_date, e_mail, first_name, phone,
    is_approved, is_blocked, last_name, last_online_time,
    messages_permission, password, photo, reg_date,
    city_id, country_id)
    VALUES (1, "about", "2020-10-22", "user@user.ru", "testUser",
    "111-222-3333", 1, 0, "LastName", NOW(), "ALL", "$2a$10$CogjAlX0O78H9AbuJDsBMuExLksckVOlx/Y9b/8Gv0FDS0Tjd2zna",
    "/static/img/default_avatar.png", NOW(), 1, 1);