package ru.ashabelskii.dossier.integration.deal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DealClient {

    private static final String URL_DEAL_ADMIN_UPDATE_STATUS = "/deal/admin/application/{applicationId}/status";

    private final WebClient dealWebClient;

    public void updateApplicationStatus(UUID applicationId, String status) {
        dealWebClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_DEAL_ADMIN_UPDATE_STATUS)
                        .queryParam("status", status)
                        .build(applicationId))
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
