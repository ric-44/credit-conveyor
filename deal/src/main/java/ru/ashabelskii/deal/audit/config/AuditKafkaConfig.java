package ru.ashabelskii.deal.audit.config;

import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.ashabelskii.deal.audit.model.AuditMessage;

@Configuration
public class AuditKafkaConfig {

    @Bean
    public KafkaTemplate<String, AuditMessage> auditKafkaTemplate(
            ProducerFactory<String, AuditMessage> auditProducerFactory) {
        return new KafkaTemplate<>(auditProducerFactory);
    }

    @Bean
    public ProducerFactory<String, AuditMessage> auditProducerFactory(AuditConfig config) {
        return new DefaultKafkaProducerFactory<>(
                config.toKafkaProperties().buildProducerProperties(),
                new StringSerializer(),
                new JsonSerializer<>()
        );
    }

}
