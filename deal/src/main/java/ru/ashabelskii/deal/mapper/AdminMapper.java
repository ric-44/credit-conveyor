package ru.ashabelskii.deal.mapper;

import org.springframework.stereotype.Component;
import ru.ashabelskii.deal.db.enums.ApplicationStatus;
import ru.ashabelskii.deal.dto.ApplicationStatusDto;

@Component
public class AdminMapper {

    public ApplicationStatus mapApplicationStatus(ApplicationStatusDto applicationStatusDto) {
        return switch (applicationStatusDto) {
            case APPROVED -> ApplicationStatus.APPROVED;
            case CC_DENIED -> ApplicationStatus.CC_DENIED;
            case PREAPPROVAL -> ApplicationStatus.PREAPPROVAL;
            case CC_APPROVED -> ApplicationStatus.CC_APPROVED;
            case CLIENT_DENIED -> ApplicationStatus.CLIENT_DENIED;
            case CREDIT_ISSUED -> ApplicationStatus.CREDIT_ISSUED;
            case DOCUMENT_SIGNED -> ApplicationStatus.DOCUMENT_SIGNED;
            case DOCUMENT_CREATED -> ApplicationStatus.DOCUMENT_CREATED;
            case PREPARE_DOCUMENTS -> ApplicationStatus.PREPARE_DOCUMENTS;
        };
    }
}
