# CurrencyConverter
Проект "Калькулятор валют"

Рабочий вариант проекта: https://exchrateconverter.herokuapp.com

Описание
------
Приложение с определённой периодичностью (сейчас один раз в час) загружает в базу данных актуальные курсы валют с сайта ЦБ 
(http://www.cbr.ru/scripts/XML_daily.asp)

Перед началом работы появляется форма для ввода имени пользователя и пароля. Если пользователь не зарегистрирован в системе, 
то необходимо пройти процедуру регистрации.

Зарегистрированному пользователю доступны страницы:

- Конвертер - здесь пользователь может выбрать исходную валюту, ее значение и целевую валюту в которую он хочет сконвертировать
  исходную валюту. Результат конвертации будет записан в базу данных.
            
- Курсы валют - отображаются курсы валют на дату, которые загружены в базе.

- История - можно посмотреть историю конвертации валют, используя фильтры "Исходная валюта", "Целевая валюта", "Дата". 

Сборка проекта
------
Для сборки проекта в корневом каталоге репозитория в командной строке запустить команду:

mvn clean install

После сборки в каталоге `\target\` появится файл `converter-0.0.1-SNAPSHOT.jar`

Запуск приложения
------
Файл `converter-0.0.1-SNAPSHOT.jar`, сформированный на этапе сборки, разместить в отдельной папке (например CONVERTER)
и запустить приложение в командной строке следующим образом:

java -jar converter-0.0.1-SNAPSHOT.jar

Настройки
------
Приложение использует файл конфигурации `resources/application.properties`. 
В файле указаны настройки подключения к базе данных.
В файле ../resources/db/migration/V1__DDL.sql расположены DDL скрипты для миграции базы данных c использованием FlyWay

В файле ../resources/db/migration/V2__DML.sql расположены скрипты для инициализации настроек в базе

Используемые технологии
-------
* Java 8
* Spring Boot 2.2.4
* Spring Security
* Postgresql 12
* flywaydb
* mustache
* html
* css

