package ru.ashabelskii.dossier.sender;

import ru.ashabelskii.dossier.config.MailConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EMailSender {

    private final JavaMailSender javaMailSender;
    private final MailConfig mailConfig;

    public void sendMessage(String addressTo, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailConfig.getAccount());
        message.setTo(addressTo);
        message.setSubject(subject);
        message.setText(text);

        javaMailSender.send(message);
    }
}
