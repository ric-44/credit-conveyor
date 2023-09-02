package ru.ashabelskii.dossier.kafka;

import ru.ashabelskii.dossier.kafka.model.Message;
import ru.ashabelskii.dossier.model.EmailMessage;
import ru.ashabelskii.dossier.sender.EMailSender;
import ru.ashabelskii.dossier.service.MessageTextService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final EMailSender mailSender;
    private final MessageTextService messageTextService;

    @KafkaListener(topics = "conveyor-finish-registration", containerFactory = "dossierContainerFactory")
    public void consumeFinishRegistrationMessage(@Payload Message message) {
        sendMessageToMail(message);
    }

    @KafkaListener(topics = "conveyor-create-documents", containerFactory = "dossierContainerFactory")
    public void consumeCreateDocumentsMessage(@Payload Message message) {
        sendMessageToMail(message);
    }

    @KafkaListener(topics = "conveyor-send-documents", containerFactory = "dossierContainerFactory")
    public void consumeSendDocumentsMessage(@Payload Message message) {
        sendMessageToMail(message);
    }

    @KafkaListener(topics = "conveyor-send-ses", containerFactory = "dossierContainerFactory")
    public void consumeSendSesMessage(@Payload Message message) {
        sendMessageToMail(message);
    }

    @KafkaListener(topics = "conveyor-credit-issued", containerFactory = "dossierContainerFactory")
    public void consumeCreditIssuedMessage(@Payload Message message) {
        sendMessageToMail(message);
    }

    @KafkaListener(topics = "conveyor-application-denied", containerFactory = "dossierContainerFactory")
    public void consumeApplicationDeniedMessage(@Payload Message message) {
        sendMessageToMail(message);
    }

    private void sendMessageToMail(Message message) {
        EmailMessage emailMessage = messageTextService.createEmailMessage(message);
        mailSender.sendMessage(emailMessage.getAddress(), emailMessage.getSubject(), emailMessage.getText());
    }
}
