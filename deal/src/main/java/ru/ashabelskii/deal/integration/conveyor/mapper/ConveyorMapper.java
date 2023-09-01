package ru.ashabelskii.deal.integration.conveyor.mapper;

import org.springframework.stereotype.Component;
import ru.ashabelskii.deal.db.entity.Application;
import ru.ashabelskii.deal.db.entity.Client;
import ru.ashabelskii.deal.db.entity.Credit;
import ru.ashabelskii.deal.db.entity.Employment;
import ru.ashabelskii.deal.db.enums.EmploymentStatus;
import ru.ashabelskii.deal.db.enums.Gender;
import ru.ashabelskii.deal.db.enums.Marital;
import ru.ashabelskii.deal.db.enums.Position;
import ru.ashabelskii.deal.dto.*;
import ru.ashabelskii.deal.integration.conveyor.dto.CreditDto;
import ru.ashabelskii.deal.integration.conveyor.dto.LoanOfferDto;
import ru.ashabelskii.deal.integration.conveyor.dto.ScoringDataDto;
import ru.ashabelskii.deal.model.LoanOffer;

import java.util.List;

@Component
public class ConveyorMapper {

    public List<LoanOffer> mapLoanOffers(List<LoanOfferDto> loanOfferDtoList) {
        return loanOfferDtoList.stream()
                .map(this::mapLoanOffer)
                .toList();
    }

    public ScoringDataDto mapToScoringDataDto(Application application) {
        Client client = application.getClient();
        LoanOffer appliedOffer = application.getAppliedOffer();

        return ScoringDataDto.builder()
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .middleName(client.getMiddleName())
                .passportNumber(client.getPassport().getNumber())
                .passportSeries(client.getPassport().getSeries())
                .passportIssueDate(client.getPassport().getIssueDate())
                .passportIssueBranch(client.getPassport().getIssueBranch())
                .birthdate(client.getBirthdate())
                .amount(appliedOffer.getRequestedAmount())
                .term(appliedOffer.getTerm())
                .dependentAmount(client.getDependentAmount())
                .isSalaryClient(appliedOffer.getIsSalaryClient())
                .isInsuranceEnabled(appliedOffer.getIsInsuranceEnabled())
                .gender(mapGenderDto(client.getGender()))
                .employment(mapEmploymentDto(client.getEmployment()))
                .maritalStatus(mapMaritalStatusDto(client.getMaritalStatus()))
                .account(client.getAccount())
                .build();
    }

    public Credit mapCredit(CreditDto creditDTO) {
        Credit credit = new Credit();
        credit.setAmount(creditDTO.amount());
        credit.setTerm(creditDTO.term());
        credit.setRate(creditDTO.rate());
        credit.setIsSalaryClient(creditDTO.isSalaryClient());
        credit.setIsInsuranceEnabled(creditDTO.isInsuranceEnabled());
        credit.setMonthlyPayment(creditDTO.monthlyPayment());
        credit.setPaymentSchedule(creditDTO.paymentSchedule());
        credit.setPsk(creditDTO.psk());
        return credit;
    }

    private LoanOffer mapLoanOffer(LoanOfferDto loanOfferDto) {
        LoanOffer loanOffer = new LoanOffer();
        loanOffer.setRequestedAmount(loanOfferDto.requestedAmount());
        loanOffer.setTerm(loanOfferDto.term());
        loanOffer.setMonthlyPayment(loanOfferDto.monthlyPayment());
        loanOffer.setRate(loanOfferDto.rate());
        loanOffer.setTotalAmount(loanOfferDto.totalAmount());
        loanOffer.setIsSalaryClient(loanOfferDto.isSalaryClient());
        loanOffer.setIsInsuranceEnabled(loanOfferDto.isInsuranceEnabled());
        return loanOffer;
    }

    private MaritalDto mapMaritalStatusDto(Marital maritalStatus) {
        return switch (maritalStatus) {
            case SINGLE -> MaritalDto.SINGLE;
            case DIVORCED -> MaritalDto.DIVORCED;
            case WIDOW_WIDOWER -> MaritalDto.WIDOW_WIDOWER;
            case MARRIED -> MaritalDto.MARRIED;
            case REMARRIED -> MaritalDto.REMARRIED;
        };
    }

    private EmploymentDto mapEmploymentDto(Employment employment) {
        return EmploymentDto.builder()
                .employmentStatus(mapEmploymentStatusDto(employment.getStatus()))
                .employerINN(employment.getEmployerINN())
                .salary(employment.getSalary())
                .position(mapPositionDto(employment.getPosition()))
                .workExperienceTotal(employment.getWorkExperienceTotal())
                .workExperienceCurrent(employment.getWorkExperienceCurrent())
                .build();
    }

    private GenderDto mapGenderDto(Gender gender) {
        return switch (gender) {
            case MALE -> GenderDto.MALE;
            case FEMALE -> GenderDto.FEMALE;
            case NON_BINARY -> GenderDto.NON_BINARY;
        };
    }

    private EmploymentStatusDto mapEmploymentStatusDto(EmploymentStatus employmentStatus) {
        return switch (employmentStatus) {
            case EMPLOYED -> EmploymentStatusDto.EMPLOYED;
            case UNEMPLOYED -> EmploymentStatusDto.UNEMPLOYED;
            case SELF_EMPLOYED -> EmploymentStatusDto.SELF_EMPLOYED;
            case BUSINESS_OWNER -> EmploymentStatusDto.BUSINESS_OWNER;
        };
    }

    private PositionDto mapPositionDto(Position position) {
        return switch (position) {
            case WORKER -> PositionDto.WORKER;
            case MID_MANAGER -> PositionDto.MID_MANAGER;
            case TOP_MANAGER -> PositionDto.TOP_MANAGER;
            case OWNER -> PositionDto.OWNER;
        };
    }
}
