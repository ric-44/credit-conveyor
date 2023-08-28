package ru.ashabelskii.conveyor.mapper;

import org.springframework.stereotype.Component;
import ru.ashabelskii.conveyor.dto.*;
import ru.ashabelskii.conveyor.model.*;

@Component
public class ScoringMapper {

    public ScoringInfo mapScoringInfo(ScoringDataDto scoringDataDto) {
        return new ScoringInfo(
                scoringDataDto.amount(),
                scoringDataDto.term(),
                scoringDataDto.firstName(),
                scoringDataDto.lastName(),
                scoringDataDto.middleName(),
                scoringDataDto.passportNumber(),
                scoringDataDto.passportSeries(),
                scoringDataDto.passportIssueDate(),
                scoringDataDto.passportIssueBranch(),
                scoringDataDto.birthdate(),
                scoringDataDto.dependentAmount(),
                scoringDataDto.isSalaryClient(),
                scoringDataDto.isInsuranceEnabled(),
                mapGender(scoringDataDto.gender()),
                mapEmployment(scoringDataDto.employment()),
                mapMarital(scoringDataDto.maritalStatus()),
                scoringDataDto.account()
        );
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
        return new Employment(
                mapEmploymentStatus(employmentDto.employmentStatus()),
                employmentDto.salary(),
                mapPosition(employmentDto.position()),
                employmentDto.workExperienceTotal(),
                employmentDto.workExperienceCurrent()
        );
    }

    private Marital mapMarital(MaritalDto maritalDto) {
        return switch (maritalDto) {
            case SINGLE -> Marital.SINGLE;
            case DIVORCED -> Marital.DIVORCED;
            case WIDOW_WIDOWER -> Marital.WIDOW_WIDOWER;
            case MARRIED -> Marital.MARRIED;
            case REMARRIED -> Marital.REMARRIED;
        };
    }
}
