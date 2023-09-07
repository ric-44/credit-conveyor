package ru.ashabelskii.deal.audit.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "audit")
public class AuditConfig {
    @NotEmpty
    private List<String> bootstrapServers;
    @NotEmpty
    private String topic;

    public KafkaProperties toKafkaProperties() {
        KafkaProperties kafkaProperties = new KafkaProperties();
        kafkaProperties.setBootstrapServers(bootstrapServers);
        return kafkaProperties;
    }
}
