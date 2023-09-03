package ru.ashabelskii.deal.service;

import org.springframework.stereotype.Service;
import ru.ashabelskii.deal.db.entity.Application;
import ru.ashabelskii.deal.dto.EmailMessage;
import ru.ashabelskii.deal.dto.Theme;

@Service
public class EmailService {

    public EmailMessage createEmailMessage(Application application, Theme theme) {
        return createEmailMessage(application, theme, null);
    }

    public EmailMessage createEmailMessage(Application application, Theme theme, String text) {
        return EmailMessage.builder()
                .applicationId(application.getId())
                .theme(theme)
                .address(application.getClient().getEmail())
                .text(text)
                .build();
    }
}
