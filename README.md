## Домашняя работа № 2 в рамках открытой школы java от Т1

задание представлено в файле **Homework_task_2.md**

**ВАЖНЫЕ ЗАМЕЧАНИЕ**

+ В учебных целях при каждом запуске приложения таблицы в базе данныхе пересоздаются.

<details>
<summary>Какая ахитектура проекта?</summary>

+ Модуль `metrics-producer-service` при отправке запроса
  на эндпоинт  
  `POST http://loclahost:8081/metrics` c телом ниже:

```json
{
    "delayInSeconds" : 1,
    "measurementTypes" : [
      "POWER",
      "VOLTAGE",
      "TEMPERATURE"
    ]
  }
```

Начинает направлять каждую 1 с случайню из трёх `POWER, VOLTAGE,TEMPERATURE`метрику в `kafka` модуль
в топик `metrics-topic`

+ Модуль `metrics-consumer-service` читает из `kafka` направленные метрики и сохраняет их вбазу данных
+ Модуль `metrics-consumer-service` предоставляет возможность узнать максимальные и средние значения метрик
  Необходимые эндпоинты представлены в документации в [swagger](http://localhost:8082/swagger-ui/index.html)

**Требования:** Для получения доступа к документации необходимо запустить проект с помощью  `docker compose`
</details>


<details>
<summary>Как запустить проект?</summary>

**Требования:** Наличие установленного `docker compose`

+ Скачать проект из репозитория
+ Перейти в папку с репозиторием в консоле например `cd t1_hw_2`
+ Находясь с проектом набрать команду `docker compose up`
    - При первом запуске потребуется ожидание `~90-120 сек`  загрузки зависимостей для запуска `maven`
+ Docker скачает необходимые зависимости и запустит контейнеры с проектом
+ **Важно!** В учебных целях при каждом запуске приложения таблицы в базе данныхе пересоздаются.

</details>

<details>
<summary>Как запустить тесты из папки postman-tests ?</summary>

**Требования:** Наличие установленного `Postman` или `newman`

+ Импортировать коллекцию тестов в `Postman` в виде файлов
    - `t1_hw2_collection_to_produce_metrics.postman_collection.json`
    - `t1_hw2_collection_to_get_metrics.postman_collection.json`
+ Запустить проект
+ Запустить коллекцию `t1_hw2_collection_to_produce_metrics.postman_collection.json` в `Postman`  
  для того, чтобы `metrics-producer-service` начала ежесекундно генерировать метрики
+ Запустить коллекцию `t1_hw2_collection_to_get_metrics.postman_collection.json` в `Postman`  
  для того, чтобы получить аггрегированные метрики или просто перечень метрик

</details>