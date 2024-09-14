Гайд на первую лабу по вебу (Метод GET)

структура на гелиосе

| - httpd-root
    | - - index.html
    | - - mutex-dir 
          | - - httpd.conf
    | - - fcgi-bin
          | - - my_app.jar

пробрасываем порт
```
ssh -p 2222 -l sНОМЕР_ИСУ -L8080:localhost:24XXX helios.cs.ifmo.ru
XXX - ваш порт для web сервера
```
Подрубаем Апачи при помощи конфига
```
httpd -f /home/studs/s409244/httpd-root/mutex-dir/httpd.conf -k start
```
Запускаем наш jar
```
java -jar -DFCGI_PORT=24YYY httpd-root/fcgi-bin/my_app.jar
YYY - ваш для FCGI сервера
```
Заходим на http://localhost:8080/ и радуемся жизни
