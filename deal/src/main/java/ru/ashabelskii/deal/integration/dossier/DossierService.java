package ru.ashabelskii.deal.integration.dossier;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.ashabelskii.deal.dto.EmailMessage;
import ru.ashabelskii.deal.dto.Theme;
import ru.ashabelskii.deal.integration.dossier.config.DossierConfig;

@Slf4j
@Service
@RequiredArgsConstructor
public class DossierService {

    private final DossierConfig dossierConfig;
    private final KafkaTemplate<String, EmailMessage> dossierKafkaTemplate;

    public void sendMessage(EmailMessage emailMessage) {
        String topic = selectTopic(emailMessage.theme());
        String applicationId = emailMessage.applicationId().toString();

        dossierKafkaTemplate.send(topic, applicationId, emailMessage)
                .addCallback(
                        success -> log.info("Message sent successfully to Kafka, applicationId = {}, theme = {} ",
                                applicationId, emailMessage.theme().name()),
                        failure -> log.error(
                                "Error sending message to Kafka, applicationId = {}, theme = {}, error = {}",
                                applicationId, emailMessage.theme().name(), failure.getMessage(), failure)
                );
    }

    private String selectTopic(Theme theme) {
        var topic = dossierConfig.getTopic();
        return switch (theme) {
            case FINISH_REGISTRATION -> topic.getFinishRegistration();
            case CREATE_DOCUMENT -> topic.getCreateDocument();
            case SEND_DOCUMENT -> topic.getSendDocument();
            case SEND_SES -> topic.getSendSes();
            case CREDIT_ISSUED -> topic.getCreditIssued();
            case APPLICATION_DENIED -> topic.getApplicationDenied();
        };
    }
}
