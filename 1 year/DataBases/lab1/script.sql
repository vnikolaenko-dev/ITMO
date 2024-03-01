-- CLEAR
DROP TABLE IF EXISTS participant CASCADE;
DROP TABLE IF EXISTS evidence CASCADE;
DROP TABLE IF EXISTS criminal_case CASCADE;
DROP TABLE IF EXISTS person CASCADE;
DROP TABLE IF EXISTS place CASCADE;
DROP TABLE IF EXISTS address CASCADE;
DROP TABLE IF EXISTS item CASCADE;
DROP TABLE IF EXISTS type CASCADE;
DROP TABLE IF EXISTS profession CASCADE;

DROP TYPE IF EXISTS gender CASCADE;
DROP TYPE IF EXISTS participant_status CASCADE;
DROP TYPE IF EXISTS evidence_status CASCADE;
-- CREATE TYPES
CREATE TYPE gender as ENUM ('М', 'Ж');
CREATE TYPE participant_status as ENUM ('Свидетель', 'Подозреваемый', 'Убийца', 'Подельник', 'Виновник', 'Жертва', 'Убитый');
CREATE TYPE evidence_status as ENUM ('Улика', 'Орудие убийства', 'Орудие');
-- CREATE DATA BASE
create table address(
	id serial PRIMARY KEY, 
	name_of_street varchar(70) NOT NULL,
	number_of_house int);
create table place(
	id serial PRIMARY KEY,
	name varchar(70) NOT NULL,
	address_id int NOT NULL REFERENCES address(id),
	description text);
create table type(
	id serial PRIMARY KEY,
	name varchar(70) NOT NULL,
	description text);
create table item(
	id serial PRIMARY KEY,
	name varchar(70) NOT NULL,
	type_id int NOT NULL REFERENCES type (id),
	description text);
create table profession(
	id serial PRIMARY KEY,
	name varchar(70) NOT NULL,
	description text);
create table person(
	id serial PRIMARY KEY, 
	name varchar(20) NOT NULL, 
	surname varchar(30) NOT NULL, 
	gender gender NOT NULL, 
	is_married boolean NOT NULL, 
	profession_id int REFERENCES profession(id), 
	date_of_birth date NOT NULL, 
	date_of_death date, 
	address_id int REFERENCES address (id));
create table criminal_case(
	id serial PRIMARY KEY, 
	name text NOT NULL, 
	description text NOT NULL, 
	is_close boolean NOT NULL, 
	date_of_crime date NOT NULL, 
	time_of_crime time, 
	place_id int REFERENCES place(id));
create table participant(
	criminal_case_id int NOT NULL REFERENCES criminal_case(id), 
	person_id int NOT NULL REFERENCES person(id), 
	status participant_status NOT NULL, 
	CONSTRAINT participant_key PRIMARY KEY (criminal_case_id, person_id));
create table evidence(
	criminal_case_id int NOT NULL REFERENCES criminal_case(id),
	item_id int NOT NULL REFERENCES item(id), 
	status evidence_status NOT NULL, 
	CONSTRAINT evidence_key PRIMARY KEY (criminal_case_id, item_id));
-- INSERT DATA TO PROFESSION
insert into profession(name, description) values
	('Программист-тестировщик', 'Программисты-тестировщики занимаются тестированием программ, приложений и других продуктов.'),
	('Программист-инженер', 'Программисты-инженеры занимаются разработкой, усовершенствованием и тестированием программ, приложений и других продуктов.'),
	('Шеф-повар', 'Осуществляет руководство производственно-хозяйственной деятельностью подразделения.'),
	('Школьный преподаватель', 'Обучение детей.'),
	('Инкассатор', 'Доставка наличных денег и ценностей в организации из банка и обратно.'),
	('Механик', 'Диагностика и ремонт автомобилей.');
-- INSERT DATA TO TYPE
insert into type(name, description) values
	('Холодное оружие', ''),
	('Огнестрельное оружие', ''),
	('Самодельное оружие', '');
-- INSERT DATA TO MURDER_ITEM
insert into item(name, type_id) values
	('Пистолет Макаров', 2),
	('Автомат АК-47', 2),
	('Охотничий нож', 1),
	('Катана', 1);
-- INSERT DATA TO ADDRESS
insert into address(name_of_street, number_of_house) values
	('Невский проспект', 45),
	('Пушкинская', 80),
	('Калинина', 6),
	('Свердлова', 51);
--INSERT DATA TO PLACE
insert into place(address_id, name, description) values
	(1, 'Торговый центр "ЗАРЯ"', 'Запасной выход №4'),
	(4, 'Квартира №13', 'Подъезд №13');
-- INSERT DATA TO PERSON
insert into person(name, surname, gender, is_married, profession_id, date_of_birth, date_of_death, address_id) values
	('Михаил', 'Манычев', 'М', TRUE, 5, '1953-03-27', '1997-06-07', 2),
	('Анастасия', 'Баранова', 'Ж', FALSE, 1, '1995-02-08', '2021-06-05', 3);
insert into person(name, surname, gender, is_married, profession_id, date_of_birth, address_id) values
	('Виталий', 'Антипов', 'М', FALSE, 2, '1986-08-14', 2),
	('Иван', 'Сарычев', 'М', TRUE, 6, '1963-01-26', 2),
	('Владимир', 'Родневой', 'М', TRUE, 6, '1953-08-02', 3);
-- INSERT DATA TO CRIMINAL_CASE
insert into criminal_case(name, description, is_close, date_of_crime, time_of_crime, place_id) values
	('Дело №89', 'Нападение на инкассторов во время транспортировки денежных средств.', TRUE, '1997-06-07', '04:05 PM', 1),
	('Дело №90', 'Убийство девушки-программиста из SowtWhere.', TRUE, '2021-04-05', '07:05 PM', 1);
-- INSERT DATA TO participant
insert into participant(criminal_case_id, person_id, status) values
	(1, 1, 'Убитый'), 
	(1, 2, 'Убитый'),
	(1, 3, 'Убийца'),
	(1, 4, 'Подозреваемый'),
	(2, 5, 'Убийца');
-- INSERT DATA TO participant
insert into evidence(criminal_case_id, item_id, status) values
	(1, 1, 'Орудие убийства'),
	(1, 3, 'Улика'),
	(2, 2, 'Орудие убийства');


SELECT address.name_of_street, COUNT(*)
	FROM participant
    JOIN criminal_case ON participant.criminal_case_id = criminal_case.id
    JOIN place ON place.id = criminal_case.place_id
    JOIN address ON address.id = place.address_id
	WHERE status = 'Убитый'
	GROUP BY address.name_of_street
	HAVING COUNT(*) > 1;