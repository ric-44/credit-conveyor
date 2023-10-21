package ru.ashabelskii.deal.mapper;

import org.springframework.stereotype.Component;
import ru.ashabelskii.deal.db.entity.Application;
import ru.ashabelskii.deal.db.entity.Client;
import ru.ashabelskii.deal.db.enums.ApplicationStatus;
import ru.ashabelskii.deal.dto.ApplicationDto;
import ru.ashabelskii.deal.dto.ApplicationStatusDto;
import ru.ashabelskii.deal.dto.ClientDto;

import java.util.List;
import java.util.Objects;

@Component
public class AdminMapper {

    public ApplicationStatus mapApplicationStatus(ApplicationStatusDto applicationStatusDto) {
        if (Objects.isNull(applicationStatusDto)) {
            return null;
        }

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

    public List<ApplicationDto> mapApplicationsDto(List<Application> applications) {
        return applications.stream()
                .map(this::mapApplicationDto)
                .toList();
    }

    private ApplicationDto mapApplicationDto(Application application) {
        ClientDto clientDto = mapClientDto(application.getClient());
        return new ApplicationDto(application.getId(), clientDto, application.getCreatedAt());
    }

    private ClientDto mapClientDto(Client client) {
        if (Objects.isNull(client)) {
            return null;
        }

        return new ClientDto(
                client.getFirstName(),
                client.getLastName(),
                client.getMiddleName(),
                client.getBirthdate()
        );
    }
}
