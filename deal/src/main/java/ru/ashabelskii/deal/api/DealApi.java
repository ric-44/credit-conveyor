package ru.ashabelskii.deal.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ashabelskii.deal.dto.FinishRegistrationRequestDto;
import ru.ashabelskii.deal.dto.LoanApplicationRequestDto;
import ru.ashabelskii.deal.dto.LoanOfferDto;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Tag(name = "DealApi", description = "Операции с проведением сделок")
@RequestMapping("/deal")
public interface DealApi {

    @PostMapping(path = "/application", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Расчёт возможных условий кредита")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(schema = @Schema(implementation = LoanOfferDto.class)))
    @ApiResponse(responseCode = "400", description = "Некорректный запрос", content = @Content)
    @ApiResponse(responseCode = "500", description = "Ошибка", content = @Content)
    ResponseEntity<List<LoanOfferDto>> createApplication(@Parameter(description = "Тело запроса", required = true)
                                                         @RequestBody @Valid LoanApplicationRequestDto loanApplicationRequestDto,
                                                         @RequestHeader HttpHeaders headers);

    @PutMapping(path = "/offer", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Выбор предложения")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content)
    @ApiResponse(responseCode = "400", description = "Некорректный запрос", content = @Content)
    @ApiResponse(responseCode = "500", description = "Ошибка", content = @Content)
    ResponseEntity<Void> applyOffer(@Parameter(description = "Тело запроса", required = true)
                                    @RequestBody @Valid LoanOfferDto loanOfferDto,
                                    @RequestHeader HttpHeaders headers);

    @PutMapping(path = "/calculate/{applicationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Завершение регистрации + Полный расчет параметров кредита")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content)
    @ApiResponse(responseCode = "400", description = "Некорректный запрос", content = @Content)
    @ApiResponse(responseCode = "500", description = "Ошибка", content = @Content)
    ResponseEntity<Void> calculateCredit(@PathVariable("applicationId") UUID applicationId,
                                         @Parameter(description = "Тело запроса", required = true)
                                         @RequestBody @Valid FinishRegistrationRequestDto finishRegistrationRequestDto,
                                         @RequestHeader HttpHeaders headers);
}
