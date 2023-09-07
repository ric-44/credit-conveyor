package ru.ashabelskii.deal.audit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.ashabelskii.deal.audit.config.AuditConfig;
import ru.ashabelskii.deal.audit.model.AuditMessage;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuditLogger {
    private final KafkaTemplate<String, AuditMessage> auditKafkaTemplate;
    private final AuditConfig auditConfig;

    public void sendEventAsync(AuditMessage auditMessage) {
        CompletableFuture.runAsync(
                () -> auditKafkaTemplate.send(auditConfig.getTopic(), auditMessage.id().toString(), auditMessage));
    }
}
