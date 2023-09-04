package ru.ashabelskii.dossier.integration.deal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ashabelskii.dossier.dto.ApplicationStatusDto;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DealService {

    private final DealClient dealClient;

    public void updateApplicationStatus(UUID applicationId, ApplicationStatusDto applicationStatus) {
        dealClient.updateApplicationStatus(applicationId, applicationStatus.name());
    }
}
