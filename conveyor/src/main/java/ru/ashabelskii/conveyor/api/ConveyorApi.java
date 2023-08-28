package ru.ashabelskii.conveyor.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.ashabelskii.conveyor.dto.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "ConveyorApi", description = "Операции с кредитом")
@RequestMapping("/conveyor")
public interface ConveyorApi {

    @PostMapping(path = "/offers", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Расчёт возможных условий кредита")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(schema = @Schema(implementation = LoanOfferDto.class)))
    @ApiResponse(responseCode = "400", description = "Некорректный запрос", content = @Content)
    @ApiResponse(responseCode = "500", description = "Ошибка", content = @Content)
    ResponseEntity<List<LoanOfferDto>> generateOffers(@Parameter(description = "Тело запроса", required = true)
                                                      @RequestBody @Valid LoanApplicationRequestDto loanApplicationRequestDto,
                                                      @RequestHeader HttpHeaders headers);

    @PostMapping(path = "/calculation", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Полный расчет параметров кредита")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(schema = @Schema(implementation = CreditDto.class)))
    @ApiResponse(responseCode = "400", description = "Некорректный запрос", content = @Content)
    @ApiResponse(responseCode = "409", description = "Ошибка скоринга",
            content = @Content(schema = @Schema(implementation = ScoringErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Ошибка", content = @Content)
    ResponseEntity<CreditDto> calculateCredit(@Parameter(description = "Тело запроса", required = true)
                                              @RequestBody @Valid ScoringDataDto scoringDataDto,
                                              @RequestHeader HttpHeaders headers);
}
