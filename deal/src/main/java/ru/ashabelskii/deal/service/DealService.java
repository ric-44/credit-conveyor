package ru.ashabelskii.deal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ashabelskii.deal.db.entity.Application;
import ru.ashabelskii.deal.db.entity.Client;
import ru.ashabelskii.deal.db.entity.Credit;
import ru.ashabelskii.deal.db.enums.CreditStatus;
import ru.ashabelskii.deal.db.helper.ApplicationHelper;
import ru.ashabelskii.deal.dto.*;
import ru.ashabelskii.deal.exception.AppException;
import ru.ashabelskii.deal.integration.conveyor.ConveyorService;
import ru.ashabelskii.deal.integration.dossier.DossierService;
import ru.ashabelskii.deal.mapper.DealMapper;
import ru.ashabelskii.deal.model.LoanOffer;

import java.util.List;
import java.util.UUID;

import static ru.ashabelskii.deal.db.enums.ApplicationStatus.*;
import static ru.ashabelskii.deal.db.enums.ChangeType.AUTOMATIC;
import static ru.ashabelskii.deal.db.enums.ChangeType.MANUAL;

@Service
@RequiredArgsConstructor
public class DealService {

    private final ApplicationHelper applicationHelper;
    private final DealMapper dealMapper;
    private final EmailService emailService;

    private final ConveyorService conveyorService;
    private final DossierService dossierService;

    @Transactional
    public List<LoanOfferDto> createApplication(LoanApplicationRequestDto loanApplicationRequestDto) {
        Client client = dealMapper.mapBaseClientInfo(loanApplicationRequestDto);
        Application application = initApplication(client);
        applicationHelper.saveAndUpdateStatus(application, PREAPPROVAL, AUTOMATIC);

        List<LoanOffer> offers = conveyorService.getOffers(loanApplicationRequestDto);
        offers.forEach(offer -> offer.setApplicationId(application.getId()));
        return dealMapper.mapLoanOffersDto(offers);
    }

    @Transactional
    public void applyOffer(LoanOfferDto loanOfferDto) {
        Application application = applicationHelper.getById(loanOfferDto.applicationId());
        if (!applicationHelper.checkStatus(application, PREAPPROVAL)) {
            throw new AppException("Application with id = " + application.getId() + " has the status "
                    + application.getStatus());
        }
        LoanOffer loanOffer = dealMapper.mapLoanOffer(loanOfferDto);
        application.setAppliedOffer(loanOffer);
        applicationHelper.saveAndUpdateStatus(application, APPROVED, MANUAL);

        EmailMessage emailMessage = emailService.createEmailMessage(application, Theme.FINISH_REGISTRATION);
        dossierService.sendMessage(emailMessage);
    }

    @Transactional
    public void calculateCredit(UUID applicationId, FinishRegistrationRequestDto finishRegistrationRequestDto) {
        Application application = applicationHelper.getById(applicationId);
        if (!applicationHelper.checkStatus(application, APPROVED)) {
            throw new AppException("Application with id = " + application.getId() + " has the status "
                    + application.getStatus());
        }
        dealMapper.mapFinishClientInfo(application.getClient(), finishRegistrationRequestDto);

        Credit credit = conveyorService.calculateCredit(application);
        credit.setStatus(CreditStatus.CALCULATED);
        application.setCredit(credit);

        applicationHelper.saveAndUpdateStatus(application, CC_APPROVED, AUTOMATIC);

        EmailMessage emailMessage = emailService.createEmailMessage(application, Theme.CREATE_DOCUMENT);
        dossierService.sendMessage(emailMessage);
    }

    private static Application initApplication(Client client) {
        Application application = new Application();
        application.setClient(client);
        return application;
    }
}
