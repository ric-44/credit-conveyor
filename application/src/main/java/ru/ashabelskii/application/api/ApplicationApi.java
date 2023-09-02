package ru.ashabelskii.application.api;

import org.springframework.http.ResponseEntity;
import ru.ashabelskii.application.dto.LoanApplicationRequestDto;
import ru.ashabelskii.application.dto.LoanOfferDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.ashabelskii.application.dto.PreScoringErrorResponse;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "ApplicationApi", description = "Операции с проведением сделок")
@RequestMapping("/application")
public interface ApplicationApi {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Прескоринг и запрос на расчёт возможных условий кредита")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(schema = @Schema(implementation = LoanOfferDto.class)))
    @ApiResponse(responseCode = "400", description = "Некорректный запрос", content = @Content)
    @ApiResponse(responseCode = "409", description = "Ошибка скоринга",
            content = @Content(schema = @Schema(implementation = PreScoringErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Ошибка", content = @Content)
    ResponseEntity<List<LoanOfferDto>> createApplication(@Parameter(description = "Тело запроса", required = true)
                                                         @RequestBody @Valid LoanApplicationRequestDto loanApplicationRequestDto,
                                                         @RequestHeader HttpHeaders headers);

    @PutMapping(path = "/offer", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Выбор одного из предложений")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content)
    @ApiResponse(responseCode = "400", description = "Некорректный запрос", content = @Content)
    @ApiResponse(responseCode = "500", description = "Ошибка", content = @Content)
    ResponseEntity<Void> applyOffer(@Parameter(description = "Тело запроса", required = true)
                                    @RequestBody @Valid LoanOfferDto loanOfferDto,
                                    @RequestHeader HttpHeaders headers);
}
