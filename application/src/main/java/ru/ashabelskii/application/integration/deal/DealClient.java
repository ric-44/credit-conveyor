package ru.ashabelskii.application.integration.deal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.ashabelskii.application.dto.LoanApplicationRequestDto;
import ru.ashabelskii.application.dto.LoanOfferDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DealClient {

    private static final String URL_DEAL_APPLICATION = "/deal/application";
    private static final String URL_DEAL_OFFER = "/deal/offer";

    private final WebClient dealWebClient;

    public List<LoanOfferDto> createApplication(LoanApplicationRequestDto loanApplicationRequestDto) {
        return dealWebClient.post()
                .uri(URL_DEAL_APPLICATION)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(loanApplicationRequestDto)
                .retrieve()
                .bodyToFlux(LoanOfferDto.class)
                .collectList()
                .block();
    }

    public void applyOffer(LoanOfferDto loanOfferDto) {
        dealWebClient.put()
                .uri(URL_DEAL_OFFER)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(loanOfferDto)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
