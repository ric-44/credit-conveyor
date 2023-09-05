# Мониторинг

## Микросервис Сделка (Deal)
- Просмотр метрик: http://localhost:8011/actuator/prometheus

## Prometheus
- Проверка, что Prometheus правильно прослушивает приложение: http://localhost:9090/targets

## Grafana:
- Просмотр Grafana: http://localhost:3000
### Настройка
- Добавить datasource: administration -> datasource -> host = http://prometheus:9090
- Добавить dashboards: 
    - готовые (credit-conveyor\additionally\grafana\dashboards)
    - свой вариант : add -> visualization -> metric -> application_status_total -> Run queries



