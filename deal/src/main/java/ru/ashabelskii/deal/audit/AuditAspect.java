package ru.ashabelskii.deal.audit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import ru.ashabelskii.deal.audit.model.AuditEventClass;
import ru.ashabelskii.deal.audit.model.AuditEventType;
import ru.ashabelskii.deal.audit.model.AuditMessage;
import ru.ashabelskii.deal.audit.model.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {

    private final AuditLogger auditLogger;

    @Around("@annotation(Audit)")
    public Object handleAuditEvent(ProceedingJoinPoint joinPoint) throws Throwable {
        AuditEventType auditEventType;
        try {
            MethodSignature signature = ((MethodSignature) joinPoint.getSignature());
            auditEventType = signature.getMethod().getAnnotation(Audit.class).eventType();
        } catch (Exception e) {
            log.error("Ошибка при попытке извлечь данные аудита", e);
            return joinPoint.proceed();
        }

        try {
            Object response = joinPoint.proceed();
            AuditMessage auditMessage = createAuditMessage(auditEventType, AuditEventClass.SUCCESS);
            auditLogger.sendEventAsync(auditMessage);
            return response;
        } catch (Exception e) {
            AuditMessage auditMessage = createAuditMessage(auditEventType, AuditEventClass.FAILURE, e.getMessage());
            auditLogger.sendEventAsync(auditMessage);
            throw e;
        }
    }

    private static AuditMessage createAuditMessage(AuditEventType auditEventType, AuditEventClass auditEventClass) {
        return createAuditMessage(auditEventType, auditEventClass, null);
    }

    private static AuditMessage createAuditMessage(AuditEventType auditEventType, AuditEventClass auditEventClass,
                                                   String message) {
        return AuditMessage.builder()
                .id(UUID.randomUUID())
                .auditEventClass(auditEventClass)
                .auditEventType(auditEventType)
                .service(Service.DEAL)
                .dateTime(LocalDateTime.now())
                .message(message)
                .build();
    }
}
