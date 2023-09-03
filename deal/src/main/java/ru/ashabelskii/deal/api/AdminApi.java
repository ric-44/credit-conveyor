package ru.ashabelskii.deal.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ashabelskii.deal.dto.ApplicationStatusDto;

import java.util.UUID;

@Tag(name = "AdminApi", description = "Административные возможности")
@RequestMapping("/deal/admin")
public interface AdminApi {

    @PutMapping("/application/{applicationId}/status")
    @Operation(summary = "Обновление статуса заявки")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content)
    @ApiResponse(responseCode = "500", description = "Ошибка", content = @Content)
    ResponseEntity<Void> updateApplicationStatus(@PathVariable("applicationId") UUID applicationId,
                                                 @RequestParam ApplicationStatusDto status,
                                                 @RequestHeader HttpHeaders headers);
}
