package ru.ashabelskii.audit.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.ashabelskii.audit.kafka.db.entity.Audit;
import ru.ashabelskii.audit.kafka.db.repository.AuditRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuditListener {

    private final AuditRepository auditRepository;

    @KafkaListener(topics = "conveyor-audit", containerFactory = "auditContainerFactory")
    public void consumeAuditMessage(@Payload Audit message) {
        log.debug("New message received: {}", message);
        auditRepository.save(message);
    }
}
