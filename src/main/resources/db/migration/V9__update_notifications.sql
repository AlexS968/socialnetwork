alter table notification_type MODIFY COLUMN code enum('POST','POST_COMMENT','COMMENT_COMMENT','FRIEND_REQUEST','MESSAGE', 'FRIEND_BIRTHDAY');
alter table notification add column read_status varchar(255) not null;

drop table if exists notification_settings;

create table notification_settings (
    id integer not null auto_increment,
    person_id integer not null,
    is_enabled TINYINT not null,
    notification_type_code enum('COMMENT_COMMENT','POST', 'POST_COMMENT', 'FRIEND_REQUEST', 'MESSAGE', 'FRIEND_BIRTHDAY') not null,
    primary key (id)

);

alter table notification_settings add constraint fk_person_settings_id  foreign key (person_id) references person(id);
