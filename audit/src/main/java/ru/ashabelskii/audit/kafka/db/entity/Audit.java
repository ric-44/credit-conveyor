package ru.ashabelskii.audit.kafka.db.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;
import ru.ashabelskii.audit.kafka.db.model.AuditEventClass;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@RedisHash("Audit")
public class Audit {
    private UUID id;
    private AuditEventClass auditEventClass;
    private String auditEventType;
    private String service;
    private LocalDateTime dateTime;
    private String message;
}
