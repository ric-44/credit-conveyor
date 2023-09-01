package ru.ashabelskii.deal.integration.conveyor;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.ashabelskii.deal.dto.LoanApplicationRequestDto;
import ru.ashabelskii.deal.integration.conveyor.dto.CreditDto;
import ru.ashabelskii.deal.integration.conveyor.dto.LoanOfferDto;
import ru.ashabelskii.deal.integration.conveyor.dto.ScoringDataDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConveyorClient {
    private static final String URL_CONVEYOR_CALCULATION = "/conveyor/calculation";
    private static final String URL_CONVEYOR_OFFERS = "/conveyor/offers";

    private final WebClient conveyorWebClient;

    public List<LoanOfferDto> getOffers(LoanApplicationRequestDto request) {
        return conveyorWebClient.post()
                .uri(URL_CONVEYOR_OFFERS)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToFlux(LoanOfferDto.class)
                .collectList()
                .block();
    }

    public CreditDto calculateCredit(ScoringDataDto scoringData) {
        return conveyorWebClient.post()
                .uri(URL_CONVEYOR_CALCULATION)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(scoringData)
                .retrieve()
                .bodyToMono(CreditDto.class)
                .block();
    }
}
