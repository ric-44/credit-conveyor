package ru.ashabelskii.deal.audit.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuditEventType {
    ADMIN_APPLICATIONS("Просмотр заявлений"),
    ADMIN_UPDATE_APPLICATION_STATUS("Обновление статуса заявления"),

    DEAL_CREATE_APPLICATION("Расчёт возможных условий кредита"),
    DEAL_APPLY_OFFER("Выбор предложения"),
    DEAL_CALCULATE_CREDIT("Завершение регистрации + Полный расчет параметров кредита"),

    DOCUMENT_SEND_DOCUMENTS("Запрос на отправку документов"),
    DOCUMENT_SIGN_DOCUMENTS("Запрос на подписание документов"),
    DOCUMENT_VERIFY_CODE("Подписание документов");

    private final String description;
}
