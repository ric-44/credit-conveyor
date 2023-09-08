package ru.ashabelskii.deal.integration.dossier.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.ashabelskii.deal.dto.EmailMessage;

@Configuration
@RequiredArgsConstructor
public class DossierKafkaConfig {

    @Bean
    public KafkaTemplate<String, EmailMessage> dossierKafkaTemplate(ProducerFactory<String, EmailMessage> factory) {
        return new KafkaTemplate<>(factory);
    }

    @Bean
    public ProducerFactory<String, EmailMessage> dossierProducerFactory(DossierConfig config) {
        return new DefaultKafkaProducerFactory<>(
                config.toKafkaProperties().buildProducerProperties(),
                new StringSerializer(),
                new JsonSerializer<>()
        );
    }
}
