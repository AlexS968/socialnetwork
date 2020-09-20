drop table if exists country;
drop table if exists city;

create table country (
    id integer not null auto_increment,
    country integer not null,
    primary key (id)
);

create table city (
    id integer not null auto_increment,
    city integer not null,
    primary key (id)
);

alter table person drop column town;
alter table person add column country_id integer not null;
alter table person add column city_id integer not null;