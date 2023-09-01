package ru.ashabelskii.deal.integration.conveyor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ashabelskii.deal.db.entity.Application;
import ru.ashabelskii.deal.db.entity.Credit;
import ru.ashabelskii.deal.dto.LoanApplicationRequestDto;
import ru.ashabelskii.deal.integration.conveyor.dto.CreditDto;
import ru.ashabelskii.deal.integration.conveyor.dto.LoanOfferDto;
import ru.ashabelskii.deal.integration.conveyor.dto.ScoringDataDto;
import ru.ashabelskii.deal.integration.conveyor.mapper.ConveyorMapper;
import ru.ashabelskii.deal.model.LoanOffer;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConveyorService {

    private final ConveyorClient conveyorClient;
    private final ConveyorMapper conveyorMapper;

    public List<LoanOffer> getOffers(LoanApplicationRequestDto loanApplicationRequestDto) {
        List<LoanOfferDto> offersResponse = conveyorClient.getOffers(loanApplicationRequestDto);
        return conveyorMapper.mapLoanOffers(offersResponse);
    }

    public Credit calculateCredit(Application application) {
        ScoringDataDto scoringDataDto = conveyorMapper.mapToScoringDataDto(application);
        CreditDto creditDto = conveyorClient.calculateCredit(scoringDataDto);
        return conveyorMapper.mapCredit(creditDto);
    }
}
