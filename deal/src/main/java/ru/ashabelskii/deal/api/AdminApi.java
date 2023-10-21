package ru.ashabelskii.deal.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ashabelskii.deal.dto.ApplicationDto;
import ru.ashabelskii.deal.dto.ApplicationStatusDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Tag(name = "AdminApi", description = "Административные возможности")
@RequestMapping("/deal/admin")
public interface AdminApi {

    @Operation(summary = "Получение заявок")
    @GetMapping("/applications")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApplicationDto.class))))
    @ApiResponse(responseCode = "500", description = "Ошибка", content = @Content)
    ResponseEntity<List<ApplicationDto>> getApplications(
            @RequestParam(required = false) ApplicationStatusDto status,
            @Schema(example = "2023-10-10T13:30:00")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @RequestParam(required = false) LocalDateTime createdFrom,
            @Schema(example = "Сергей")
            @RequestParam(required = false) String firstName,
            @Schema(example = "Ершов")
            @RequestParam(required = false) String lastName,
            @Schema(example = "Иванович")
            @RequestParam(required = false) String middleName
    );

    @PutMapping("/application/{applicationId}/status")
    @Operation(summary = "Обновление статуса заявки")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content)
    @ApiResponse(responseCode = "500", description = "Ошибка", content = @Content)
    ResponseEntity<Void> updateApplicationStatus(@PathVariable("applicationId") UUID applicationId,
                                                 @RequestParam ApplicationStatusDto status,
                                                 @RequestHeader HttpHeaders headers);
}
