package ru.ashabelskii.deal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ashabelskii.deal.db.entity.Application;
import ru.ashabelskii.deal.db.entity.Client;
import ru.ashabelskii.deal.db.entity.Credit;
import ru.ashabelskii.deal.db.enums.ApplicationStatus;
import ru.ashabelskii.deal.db.enums.CreditStatus;
import ru.ashabelskii.deal.db.helper.ApplicationHelper;
import ru.ashabelskii.deal.dto.FinishRegistrationRequestDto;
import ru.ashabelskii.deal.dto.LoanApplicationRequestDto;
import ru.ashabelskii.deal.dto.LoanOfferDto;
import ru.ashabelskii.deal.exception.AppException;
import ru.ashabelskii.deal.integration.conveyor.ConveyorService;
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

    private final ConveyorService conveyorService;

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
        checkStatus(application, PREAPPROVAL);
        LoanOffer loanOffer = dealMapper.mapLoanOffer(loanOfferDto);
        application.setAppliedOffer(loanOffer);
        applicationHelper.saveAndUpdateStatus(application, APPROVED, MANUAL);
    }

    @Transactional
    public void calculateCredit(UUID applicationId, FinishRegistrationRequestDto finishRegistrationRequestDto) {
        Application application = applicationHelper.getById(applicationId);
        checkStatus(application, APPROVED);
        dealMapper.mapFinishClientInfo(application.getClient(), finishRegistrationRequestDto);

        Credit credit = conveyorService.calculateCredit(application);
        credit.setStatus(CreditStatus.CALCULATED);
        application.setCredit(credit);

        applicationHelper.saveAndUpdateStatus(application, CC_APPROVED, AUTOMATIC);
    }

    private static Application initApplication(Client client) {
        Application application = new Application();
        application.setClient(client);
        return application;
    }

    private static void checkStatus(Application application, ApplicationStatus applicationStatus) {
        if (!application.getStatus().equals(applicationStatus)) {
            throw new AppException("Application with id = " + application.getId() + " has the status "
                    + application.getStatus());
        }
    }
}
