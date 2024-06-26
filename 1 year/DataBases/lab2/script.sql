--Запрос №1
SELECT Н_ЛЮДИ.ИД, Н_СЕССИЯ.УЧГОД FROM Н_ЛЮДИ 
	INNER JOIN Н_СЕССИЯ 
	ON Н_ЛЮДИ.ИД > 100012 AND Н_СЕССИЯ.ЧЛВК_ИД > 100622 AND Н_СЕССИЯ.ЧЛВК_ИД = 106059;

--Запрос №2
SELECT Н_ЛЮДИ.ИМЯ, Н_ВЕДОМОСТИ.ДАТА, Н_СЕССИЯ.ЧЛВК_ИД 
	FROM Н_СЕССИЯ 
		RIGHT JOIN Н_ВЕДОМОСТИ ON Н_ВЕДОМОСТИ.ИД = Н_СЕССИЯ.ЧЛВК_ИД
		RIGHT JOIN Н_ЛЮДИ ON Н_ЛЮДИ.ИД = Н_СЕССИЯ.ЧЛВК_ИД
	WHERE Н_ВЕДОМОСТИ.ДАТА > '2010-06-18' AND Н_ЛЮДИ.ОТЧЕСТВО < 'Сергеевич';

--Запрос №3
SELECT EXISTS(SELECT Н_ЛЮДИ.ИМЯ 
		FROM Н_УЧЕНИКИ
			INNER JOIN Н_ЛЮДИ ON Н_ЛЮДИ.ИД = Н_УЧЕНИКИ.ЧЛВК_ИД
		WHERE age(Н_ЛЮДИ.ДАТА_РОЖДЕНИЯ) > 25);

--Запрос №4
SELECT Н_ПЛАНЫ.ИД
        FROM Н_ПЛАНЫ
                JOIN Н_ОТДЕЛЫ ON Н_ОТДЕЛЫ.ИД = Н_ПЛАНЫ.ОТД_ИД AND Н_ОТДЕЛЫ.КОРОТКОЕ_ИМЯ = 'КТиУ'
                JOIN Н_ГРУППЫ_ПЛАНОВ ON Н_ГРУППЫ_ПЛАНОВ.ПЛАН_ИД = Н_ПЛАНЫ.ИД
        GROUP BY Н_ПЛАНЫ.ИД 
        	HAVING count(Н_ГРУППЫ_ПЛАНОВ) = 2;

--Запрос №5
SELECT Н_ГРУППЫ_ПЛАНОВ.ГРУППА, avg(age(Н_ЛЮДИ.ДАТА_РОЖДЕНИЯ))
		FROM Н_ГРУППЫ_ПЛАНОВ
			JOIN Н_УЧЕНИКИ ON Н_УЧЕНИКИ.ГРУППА = Н_ГРУППЫ_ПЛАНОВ.ГРУППА
			JOIN Н_ЛЮДИ ON Н_ЛЮДИ.ИД = Н_УЧЕНИКИ.ЧЛВК_ИД
		GROUP BY Н_ГРУППЫ_ПЛАНОВ.ГРУППА, Н_ЛЮДИ.ДАТА_РОЖДЕНИЯ 
			HAVING avg(age(Н_ЛЮДИ.ДАТА_РОЖДЕНИЯ)) > (
				SELECT avg(age(Н_ЛЮДИ.ДАТА_РОЖДЕНИЯ))
					FROM Н_ЛЮДИ
    					JOIN Н_УЧЕНИКИ ON Н_УЧЕНИКИ.ЧЛВК_ИД = Н_ЛЮДИ.ИД
  					WHERE Н_УЧЕНИКИ.ГРУППА = '1100');

--Запрос №6
SELECT Н_УЧЕНИКИ.ГРУППА, Н_ЛЮДИ.ИД, Н_ЛЮДИ.ИМЯ, Н_ЛЮДИ.ФАМИЛИЯ, Н_ЛЮДИ.ОТЧЕСТВО
	FROM Н_УЧЕНИКИ
		JOIN Н_ЛЮДИ ON Н_ЛЮДИ.ИД = Н_УЧЕНИКИ.ЧЛВК_ИД
		JOIN Н_ПЛАНЫ ON Н_ПЛАНЫ.ИД = Н_УЧЕНИКИ.ПЛАН_ИД
		JOIN Н_НАПРАВЛЕНИЯ_СПЕЦИАЛ ON Н_НАПРАВЛЕНИЯ_СПЕЦИАЛ.ИД = Н_ПЛАНЫ.НАПС_ИД
		JOIN Н_НАПР_СПЕЦ ON Н_НАПР_СПЕЦ.ИД = Н_НАПРАВЛЕНИЯ_СПЕЦИАЛ.НАПС_ИД
	WHERE Н_ПЛАНЫ.НАПС_ИД IN (
		SELECT Н_НАПРАВЛЕНИЯ_СПЕЦИАЛ.ИД FROM Н_НАПРАВЛЕНИЯ_СПЕЦИАЛ
			JOIN Н_НАПР_СПЕЦ ON Н_НАПР_СПЕЦ.ИД = Н_НАПРАВЛЕНИЯ_СПЕЦИАЛ.НАПС_ИД AND Н_НАПР_СПЕЦ.НАИМЕНОВАНИЕ ='Программная инженерия')
		AND Н_УЧЕНИКИ.ПРИЗНАК = 'отчисл'
		AND Н_УЧЕНИКИ.КОНЕЦ_ПО_ПРИКАЗУ < '2012-09-01';

--Запрос №7
SELECT count(Н_ЛЮДИ.ИД)
	FROM Н_ЛЮДИ
		JOIN Н_УЧЕНИКИ ON Н_УЧЕНИКИ.ЧЛВК_ИД = Н_ЛЮДИ.ИД
	WHERE Н_УЧЕНИКИ.ГРУППА = '3100' 
		AND Н_ЛЮДИ.ИД NOT IN(
		SELECT Н_ЛЮДИ.ИД
			FROM Н_ЛЮДИ
				JOIN Н_ВЕДОМОСТИ ON Н_ВЕДОМОСТИ.ЧЛВК_ИД = Н_ЛЮДИ.ИД
				JOIN Н_ОЦЕНКИ ON Н_ВЕДОМОСТИ.ОЦЕНКА = Н_ОЦЕНКИ.КОД
			GROUP BY Н_ЛЮДИ.ИД, Н_ОЦЕНКИ.ПРИМЕЧАНИЕ 
			HAVING Н_ОЦЕНКИ.ПРИМЕЧАНИЕ = 'удовлетворительно');
