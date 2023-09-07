package ru.ashabelskii.audit.kafka.db.repository;

import org.springframework.data.repository.CrudRepository;
import ru.ashabelskii.audit.kafka.db.entity.Audit;

import java.util.UUID;

public interface AuditRepository extends CrudRepository<Audit, UUID> {
}
