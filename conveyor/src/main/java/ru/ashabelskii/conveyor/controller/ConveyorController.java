package ru.ashabelskii.conveyor.controller;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.ashabelskii.conveyor.api.ConveyorApi;
import ru.ashabelskii.conveyor.dto.CreditDto;
import ru.ashabelskii.conveyor.dto.LoanApplicationRequestDto;
import ru.ashabelskii.conveyor.dto.LoanOfferDto;
import ru.ashabelskii.conveyor.dto.ScoringDataDto;

import java.util.List;

@RestController
public class ConveyorController implements ConveyorApi {

    @Override
    public ResponseEntity<List<LoanOfferDto>> generateOffers(LoanApplicationRequestDto loanApplicationRequestDto,
                                                             HttpHeaders headers) {
        throw new NotImplementedException();
    }

    @Override
    public ResponseEntity<CreditDto> calculateCredit(ScoringDataDto scoringDataDto, HttpHeaders headers) {
        throw new NotImplementedException();
    }
}
