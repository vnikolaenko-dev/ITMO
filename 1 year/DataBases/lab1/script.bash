#!/bin/bash

# OPEN
psql -h pg -d studs

# CREATE DATA BASE
create table address(id serial PRIMARY KEY, name_of_street text NOT NULL, number_of_house int NOT NULL);
create table place(id serial PRIMARY KEY, address_id int NOT NULL REFERENCES address(id), name text NOT NULL, description text);
create table type(id sereal PRIMARY KEY, name text NOT NULL, description text);
create table murder_item(id serial PRIMARY KEY, address_id int NOT NULL REFERENCES type (id), name text NOT NULL, description text);
create table profession(id sereal PRIMARY KEY, name text NOT NULL, description text);
create table person(id serial PRIMARY KEY, name text NOT NULL, surname text NOT NULL, is_male boolean NOT NULL, is_married boolean NOT NULL, profession_id int REFERENCES profession(id), date_of_birth date NOT NULL, date_of_death date, address_id int REFERENCES address (id));
create table criminal_case(id serial PRIMARY KEY, name text NOT NULL, description text NOT NULL, is_close boolean NOT NULL, date_of_crime date NOT NULL, time_of_crime time, place_id int REFERENCES place(id), victim_id int REFERENCES person (id), murder_id int REFERENCES person (id), murder_item_id int REFERENCES murder_item (id));
create table suspect(criminal_case_id int NOT NULL REFERENCES criminal_case(id), person_id int NOT NULL REFERENCES person(id), CONSTRAINT suspect_key PRIMARY KEY (person_id, criminal_case_id));

# INSERT DATA TO PROFESSION
insert into profession(name, description) values('Программист-тестировщик', 'Программисты-тестировщики занимаются тестированием программ, приложений и других продуктов.');
insert into profession(name, description) values('Программист-инженер', 'Программисты-инженеры занимаются разработкой, усовершенствованием и тестированием программ, приложений и других продуктов.');
insert into profession(name, description) values('Шеф-повар', 'Осуществляет руководство производственно-хозяйственной деятельностью подразделения.');
insert into profession(name, description) values('Школьный преподаватель', 'Обучение детей.');
insert into profession(name, description) values('Инкассатор', 'Доставка наличных денег и ценностей в организации из банка и обратно.');
insert into profession(name, description) values('Механик', 'Диагностика и ремонт автомобилей.');

# INSERT DATA TO TYPE
insert into type(name, description) values('Холодное оружие', '');
insert into type(name, description) values('Огнестрельное оружие', '');
insert into type(name, description) values('Самодельное оружие', '');

# INSERT DATA TO MURDER_ITEM
insert into murder_item(name, type_id) values('Пистолет Макаров', 2);
insert into murder_item(name, type_id) values('Автомат АК-47', 2);
insert into murder_item(name, type_id) values('Охотничий нож', 1);
insert into murder_item(name, type_id) values('Катана', 1);

# INSERT DATA TO ADDRESS
insert into address(name_of_street, number_of_house) values('Невский проспект', 45);
insert into address(name_of_street, number_of_house) values('Пушкинская', 80);
insert into address(name_of_street, number_of_house) values('Калинина', 6);
insert into address(name_of_street, number_of_house) values('Свердлова', 51);

# INSERT DATA TO PLACE
insert into place(address_id, name, description) values(1, 'Торговый центр "ЗАРЯ"', 'Запасной выход №4');
insert into place(address_id, name, description) values(4, 'Квартира №13', 'Подъезд №13');

# INSERT DATA TO PERSON
insert into person(name, surname, is_male, is_married, profession_id, date_of_birth, date_of_death, address_id) values('Михаил', 'Манычев', TRUE, TRUE, 5, '1953-03-27', '1997-06-07', 2);
insert into person(name, surname, is_male, is_married, profession_id, date_of_birth, address_id) values('Виталий', 'Антипов', TRUE, FALSE, 2, '1986-08-14', 2);
insert into person(name, surname, is_male, is_married, profession_id, date_of_birth, date_of_death, address_id) values('Анастасия', 'Баранова', FALSE, FALSE, 1, '1995-02-08', '2021-06-05', 3);
insert into person(name, surname, is_male, is_married, profession_id, date_of_birth, address_id) values('Иван', 'Сарычев', TRUE, TRUE, 6, '1963-01-26', 2);
insert into person(name, surname, is_male, is_married, profession_id, date_of_birth, address_id) values('Владимир', 'Родневой', TRUE, TRUE, 6, '1953-08-02', 3);

# INSERT DATA TO CRIMINAL_CASE
insert into criminal_case(name, description, is_close, date_of_crime, time_of_crime, place_id, victim_id, murder_id, murder_item_id) values('Дело №89', 'Убийство инкасстора во время транспортировки денежных средств.', TRUE, '1997-06-07', '04:05 PM', 1, 1, 5, 2);
insert into criminal_case(name, description, is_close, date_of_crime, time_of_crime, place_id, victim_id, murder_id, murder_item_id) values('Дело №89', 'Убийство девушки-программиста из SowtWhere.', TRUE, '2021-04-05', '07:05 PM', 2, 3, 2, 3);

# INSERT DATA TO SUSPECT
insert into suspect(person_id, criminal_case_id) values(5, 1);