package ru.ashabelskii.deal.mapper;

import org.springframework.stereotype.Component;
import ru.ashabelskii.deal.db.entity.Client;
import ru.ashabelskii.deal.db.entity.Employment;
import ru.ashabelskii.deal.db.entity.Passport;
import ru.ashabelskii.deal.db.enums.EmploymentStatus;
import ru.ashabelskii.deal.db.enums.Gender;
import ru.ashabelskii.deal.db.enums.Marital;
import ru.ashabelskii.deal.db.enums.Position;
import ru.ashabelskii.deal.dto.*;
import ru.ashabelskii.deal.model.LoanOffer;

import java.util.List;

@Component
public class DealMapper {

    public Client mapBaseClientInfo(LoanApplicationRequestDto loanApplicationRequestDto) {
        Passport passport = mapPassport(loanApplicationRequestDto);

        Client client = new Client();
        client.setFirstName(loanApplicationRequestDto.firstName());
        client.setLastName(loanApplicationRequestDto.lastName());
        client.setMiddleName(loanApplicationRequestDto.middleName());
        client.setBirthdate(loanApplicationRequestDto.birthdate());
        client.setEmail(loanApplicationRequestDto.email());
        client.setPassport(passport);
        return client;
    }

    public Client mapFinishClientInfo(Client client, FinishRegistrationRequestDto finishRegistrationRequestDto) {
        Passport passport = client.getPassport();
        passport.setIssueDate(finishRegistrationRequestDto.passportIssueDate());
        passport.setIssueBranch(finishRegistrationRequestDto.passportIssueBranch());

        client.setDependentAmount(finishRegistrationRequestDto.dependentAmount());
        client.setGender(mapGender(finishRegistrationRequestDto.gender()));
        client.setEmployment(mapEmployment(finishRegistrationRequestDto.employment()));
        client.setMaritalStatus(mapMaritalStatus(finishRegistrationRequestDto.maritalStatus()));
        client.setAccount(finishRegistrationRequestDto.account());
        return client;
    }

    public LoanOffer mapLoanOffer(LoanOfferDto loanOfferDto) {
        LoanOffer loanOffer = new LoanOffer();
        loanOffer.setApplicationId(loanOfferDto.applicationId());
        loanOffer.setRequestedAmount(loanOfferDto.requestedAmount());
        loanOffer.setTerm(loanOfferDto.term());
        loanOffer.setMonthlyPayment(loanOfferDto.monthlyPayment());
        loanOffer.setRate(loanOfferDto.rate());
        loanOffer.setTotalAmount(loanOfferDto.totalAmount());
        loanOffer.setIsSalaryClient(loanOfferDto.isSalaryClient());
        loanOffer.setIsInsuranceEnabled(loanOfferDto.isInsuranceEnabled());
        return loanOffer;
    }

    public List<LoanOfferDto> mapLoanOffersDto(List<LoanOffer> loanOffer) {
        return loanOffer.stream()
                .map(this::mapLoanOfferDto)
                .toList();
    }

    private LoanOfferDto mapLoanOfferDto(LoanOffer loanOffer) {
        return new LoanOfferDto(loanOffer.getApplicationId(), loanOffer.getRequestedAmount(), loanOffer.getTerm(),
                loanOffer.getMonthlyPayment(), loanOffer.getRate(), loanOffer.getTotalAmount(),
                loanOffer.getIsSalaryClient(), loanOffer.getIsInsuranceEnabled());
    }

    private static Passport mapPassport(LoanApplicationRequestDto loanApplicationRequestDto) {
        Passport passport = new Passport();
        passport.setSeries(loanApplicationRequestDto.passportSeries());
        passport.setNumber(loanApplicationRequestDto.passportNumber());
        return passport;
    }

    private Gender mapGender(GenderDto genderDto) {
        return switch (genderDto) {
            case MALE -> Gender.MALE;
            case FEMALE -> Gender.FEMALE;
            case NON_BINARY -> Gender.NON_BINARY;
        };
    }

    private Position mapPosition(PositionDto positionDto) {
        return switch (positionDto) {
            case WORKER -> Position.WORKER;
            case MID_MANAGER -> Position.MID_MANAGER;
            case TOP_MANAGER -> Position.TOP_MANAGER;
            case OWNER -> Position.OWNER;
        };
    }

    private EmploymentStatus mapEmploymentStatus(EmploymentStatusDto employmentStatusDto) {
        return switch (employmentStatusDto) {
            case EMPLOYED -> EmploymentStatus.EMPLOYED;
            case UNEMPLOYED -> EmploymentStatus.UNEMPLOYED;
            case SELF_EMPLOYED -> EmploymentStatus.SELF_EMPLOYED;
            case BUSINESS_OWNER -> EmploymentStatus.BUSINESS_OWNER;
        };
    }

    private Employment mapEmployment(EmploymentDto employmentDto) {
        Employment employment = new Employment();
        employment.setStatus(mapEmploymentStatus(employmentDto.employmentStatus()));
        employment.setEmployerINN(employmentDto.employerINN());
        employment.setSalary(employmentDto.salary());
        employment.setPosition(mapPosition(employmentDto.position()));
        employment.setWorkExperienceTotal(employmentDto.workExperienceTotal());
        employment.setWorkExperienceCurrent(employmentDto.workExperienceCurrent());
        return employment;
    }

    private Marital mapMaritalStatus(MaritalDto maritalDto) {
        return switch (maritalDto) {
            case SINGLE -> Marital.SINGLE;
            case DIVORCED -> Marital.DIVORCED;
            case WIDOW_WIDOWER -> Marital.WIDOW_WIDOWER;
            case MARRIED -> Marital.MARRIED;
            case REMARRIED -> Marital.REMARRIED;
        };
    }
}
