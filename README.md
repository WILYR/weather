# weather
openweather current data

1. Установить JDK 8
2. [Скачать weather.jar](https://github.com/WILYR/weather/releases/tag/0.1.1)
3. Запуск через консоль в директории с архивом  
```shell
java -jar weather.jar
```
При запуске появляется файл weather.csv, содержащий текущие данные о погоде в районе "Малая Охта в Санкт-Петербурге". Если файл уже существует, новые
данные будут записаны в его конец.  

Температура +  
Давление +  
Влажность +  
Скорость ветра +  
Положение солнца ?  
Видимость +  
Осадки (есть/нет) + (Заменено на состояние(Ясно/Легкий дождь/Мокрый снег.....))  

Формат записи данных: 
```csv
Текущее время: Tue May 03 14:45:53 MSK 2022|Район: Малая Охта|Температура: 8.07|Влажность(%): 49|Давление(гПа): 1000|Скорость ветра(м/c): 11|Облачность(%): 75|Видимость(м): 10000|Погода: облачно с прояснениями
```
При желании можно поменять широту/долготу и apikey в application.properties в самом архиве.

Если все отработало корректно, то создаем рядом с jar новый файл - start.bat со [следующим](https://github.com/WILYR/weather/blob/master/src/main/resources/start.bat) содержимым. Пробуем его запустить, если все ок, то можно добавлять его в "Планировщик заданий windows" и выставлять выполнение в любое время.
