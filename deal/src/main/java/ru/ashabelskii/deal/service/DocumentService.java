package ru.ashabelskii.deal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ashabelskii.deal.db.entity.Application;
import ru.ashabelskii.deal.db.enums.ApplicationStatus;
import ru.ashabelskii.deal.db.enums.CreditStatus;
import ru.ashabelskii.deal.db.helper.ApplicationHelper;
import ru.ashabelskii.deal.dto.EmailMessage;
import ru.ashabelskii.deal.dto.Theme;
import ru.ashabelskii.deal.exception.AppException;
import ru.ashabelskii.deal.integration.dossier.DossierService;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static ru.ashabelskii.deal.db.enums.ApplicationStatus.CREDIT_ISSUED;
import static ru.ashabelskii.deal.db.enums.ChangeType.AUTOMATIC;
import static ru.ashabelskii.deal.db.enums.ChangeType.MANUAL;
import static ru.ashabelskii.deal.service.util.SesCodeGeneratorUtil.createSesCode;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final ApplicationHelper applicationHelper;
    private final EmailService emailService;
    private final DossierService dossierService;

    @Transactional(readOnly = true)
    public void sendDocuments(UUID applicationId) {
        Application application = applicationHelper.getById(applicationId);
        applicationHelper.saveAndUpdateStatus(application, ApplicationStatus.PREPARE_DOCUMENTS, MANUAL);
        EmailMessage emailMessage = emailService.createEmailMessage(application, Theme.SEND_DOCUMENT);
        dossierService.sendMessage(emailMessage);
    }

    @Transactional
    public void signDocuments(UUID applicationId) {
        Application application = applicationHelper.getById(applicationId);
        application.setSesCode(createSesCode());

        applicationHelper.save(application);
        EmailMessage emailMessage = emailService.createEmailMessage(application, Theme.SEND_SES,
                String.valueOf(application.getSesCode()));
        dossierService.sendMessage(emailMessage);
    }

    @Transactional
    public void verifyCode(UUID applicationId, Integer sesCode) {
        Application application = applicationHelper.getById(applicationId);

        if (!Objects.equals(application.getSesCode(), sesCode)) {
            throw new AppException("Incorrect code");
        }
        application.setSignDate(LocalDateTime.now());
        applicationHelper.updateStatus(application, ApplicationStatus.DOCUMENT_SIGNED, MANUAL);

        issueCredit(application);
    }

    public void issueCredit(Application application) {
        application.getCredit().setStatus(CreditStatus.ISSUED);
        applicationHelper.saveAndUpdateStatus(application, CREDIT_ISSUED, AUTOMATIC);

        EmailMessage emailMessage = emailService.createEmailMessage(application, Theme.CREDIT_ISSUED);
        dossierService.sendMessage(emailMessage);
    }
}