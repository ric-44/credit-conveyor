package ru.ashabelskii.deal.audit.model;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record AuditMessage(
        UUID id,
        AuditEventClass auditEventClass,
        AuditEventType auditEventType,
        Service service,
        LocalDateTime dateTime,
        String message
) {
}
