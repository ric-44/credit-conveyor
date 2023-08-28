package ru.ashabelskii.conveyor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ashabelskii.conveyor.dto.CreditDto;
import ru.ashabelskii.conveyor.dto.LoanApplicationRequestDto;
import ru.ashabelskii.conveyor.dto.LoanOfferDto;
import ru.ashabelskii.conveyor.dto.ScoringDataDto;
import ru.ashabelskii.conveyor.exception.ScoringException;
import ru.ashabelskii.conveyor.mapper.CreditMapper;
import ru.ashabelskii.conveyor.mapper.OfferMapper;
import ru.ashabelskii.conveyor.mapper.ScoringMapper;
import ru.ashabelskii.conveyor.model.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConveyorService {

    private final OfferService offerService;
    private final ScoringService scoringService;
    private final CreditService creditService;

    private final OfferMapper offerMapper;
    private final ScoringMapper scoringMapper;
    private final CreditMapper creditMapper;

    public List<LoanOfferDto> generateOffers(LoanApplicationRequestDto loanApplicationRequestDto) {
        LoanRequest loanRequest = offerMapper.mapLoanRequest(loanApplicationRequestDto);
        List<LoanOffer> loanOffers = offerService.createOffers(loanRequest);
        return offerMapper.mapOfferResponse(loanOffers);
    }

    public CreditDto calculateCredit(ScoringDataDto scoringDataDto) {
        ScoringInfo scoringInfo = scoringMapper.mapScoringInfo(scoringDataDto);
        ScoringResult scoringResult = scoringService.start(scoringInfo);
        if (!scoringResult.isAvailability()) {
            throw new ScoringException(scoringResult.scoringRefuseCauses());
        }
        CreditInfo creditInfo = creditService.createCredit(scoringInfo, scoringResult.rate());
        return creditMapper.mapCreditDto(creditInfo);
    }
}
