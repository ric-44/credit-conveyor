# Проект "Кредитный конвейер"

## Описание

Набор микросервисов для расчёта и выдачи кредита.

## [Запуск проекта](INFO.md)

## Технологии
- Java 17 (Spring boot)
- Spring Data (PostgreSQL, Redis, Liquibase)
- Swagger
- Kafka
- Spring Cloud Gateway
- Docker
- Java Mail
- Prometheus + Grafana
- Logbook
- Lombok
- Junit 5 (Mockito)

## Логика работы
1. Пользователь отправляет заявку на кредит
2. МС Заявка осуществляет прескоринг заявки и если прескоринг проходит, то заявка сохраняется в МС Сделка и отправляется
   в МС Конвейер.
3. МС Конвейер возвращает через МС Заявку пользователю 4 предложения по кредиту с разными условиями (например без
   страховки, со страховкой, с зарплатным клиентом, со страховкой и зарплатным клиентом) или отказ.
4. Пользователь выбирает одно из предложений, отправляется запрос в МС Заявка, а оттуда в МС Сделка, где заявка на
   кредит и сам кредит сохраняются в базу.
5. МС Досье отправляет клиенту письмо с текстом "Ваша заявка предварительно одобрена, завершите оформление".
6. Клиент отправляет запрос в МС Сделка со всеми своими полными данными о работодателе и прописке. Происходит скоринг
   данных в МС Конвейер, МС Конвейер рассчитывает все данные по кредиту (ПСК, график платежей и тд), МС Сделка сохраняет
   обновленную заявку и кредит.
7. После валидации МС Досье отправляет письмо на почту клиенту с одобрением или отказом. Если кредит одобрен,
   то в письме присутствует ссылка на запрос "Сформировать документы".
8. Клиент отправляет запрос на формирование документов в МС Досье, МС Досье отправляет клиенту на почту документы для
   подписания и ссылку на запрос на согласие с условиями.
9. Клиент может отказаться от условий или согласиться. Если согласился - МС Досье на почту отправляет код и ссылку на
   подписание документов, куда клиент должен отправить полученный код в МС Сделка.
10. Если полученный код совпадает с отправленным, МС Сделка выдает кредит.

## Архитектура

![architecture.png](images%2Farchitecture.png)

## Business-flow

![business-flow.png](images%2Fbusiness-flow.png)

### Легенда:

Цвета:

- Оранжевый: МС Заявка
- Голубой: МС Сделка + БД
- Фиолетовый: МС Кредитный Конвейер
- Зеленый: МС Кредитное Досье
- Красный: конец флоу

Типы действий:

- Иконка "человек" сверху слева: пользовательское действие
- Иконка "зубчатое колесо" сверху слева: действие системы
- Иконка "молния": ошибка
- Иконка "прямоугольник с горизонтальными полосками": выбор пользователя
- Иконка "конверт": асинхронная отправка email-сообщения на почту

## Sequence-диаграмма

![sequence.png](images%2Fsequence.png)

