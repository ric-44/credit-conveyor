package ru.ashabelskii.dossier.kafka;

import lombok.extern.slf4j.Slf4j;
import ru.ashabelskii.dossier.dto.ApplicationStatusDto;
import ru.ashabelskii.dossier.integration.deal.DealService;
import ru.ashabelskii.dossier.kafka.model.Message;
import ru.ashabelskii.dossier.model.EmailMessage;
import ru.ashabelskii.dossier.sender.EMailSender;
import ru.ashabelskii.dossier.service.MessageTextService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final EMailSender mailSender;
    private final DealService dealService;
    private final MessageTextService messageTextService;

    @KafkaListener(topics = "conveyor-finish-registration", containerFactory = "dossierContainerFactory")
    public void consumeFinishRegistrationMessage(@Payload Message message) {
        sendMessageToMailWithHandling(message);
    }

    @KafkaListener(topics = "conveyor-create-documents", containerFactory = "dossierContainerFactory")
    public void consumeCreateDocumentsMessage(@Payload Message message) {
        sendMessageToMailWithHandling(message);
    }

    @KafkaListener(topics = "conveyor-send-documents", containerFactory = "dossierContainerFactory")
    public void consumeSendDocumentsMessage(@Payload Message message) {
        boolean isMessageSend = sendMessageToMailWithHandling(message);
        try {
            if (isMessageSend) {
                dealService.updateApplicationStatus(message.getApplicationId(), ApplicationStatusDto.DOCUMENT_CREATED);
            }
        } catch (Exception e) {
            log.error("Error updating status, applicationId = {}, theme = {}, error: {}", message.getApplicationId(),
                    message.getTheme().name(), e.getMessage(), e);
        }
    }

    @KafkaListener(topics = "conveyor-send-ses", containerFactory = "dossierContainerFactory")
    public void consumeSendSesMessage(@Payload Message message) {
        sendMessageToMailWithHandling(message);
    }

    @KafkaListener(topics = "conveyor-credit-issued", containerFactory = "dossierContainerFactory")
    public void consumeCreditIssuedMessage(@Payload Message message) {
        sendMessageToMailWithHandling(message);
    }

    @KafkaListener(topics = "conveyor-application-denied", containerFactory = "dossierContainerFactory")
    public void consumeApplicationDeniedMessage(@Payload Message message) {
        sendMessageToMailWithHandling(message);
    }

    private boolean sendMessageToMailWithHandling(Message message) {
        try {
            sendMessageToMail(message);
            log.info("Message sent successfully, applicationId = {}, theme = {}, address = {}",
                    message.getApplicationId(), message.getTheme().name(), message.getAddress());
            return true;
        } catch (Exception e) {
            log.error("Error sending message, applicationId = {}, theme = {}, address = {}, error: {}",
                    message.getApplicationId(), message.getTheme().name(), message.getAddress(), e.getMessage(), e);
            return false;
        }
    }

    private void sendMessageToMail(Message message) {
        EmailMessage emailMessage = messageTextService.createEmailMessage(message);
        mailSender.sendMessage(emailMessage.getAddress(), emailMessage.getSubject(), emailMessage.getText());
    }
}
