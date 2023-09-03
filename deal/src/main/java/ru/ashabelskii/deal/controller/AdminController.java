package ru.ashabelskii.deal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.ashabelskii.deal.api.AdminApi;
import ru.ashabelskii.deal.dto.ApplicationStatusDto;
import ru.ashabelskii.deal.service.AdminService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AdminController implements AdminApi {

    private final AdminService adminService;

    @Override
    public ResponseEntity<Void> updateApplicationStatus(UUID applicationId, ApplicationStatusDto status,
                                                        HttpHeaders headers) {
        adminService.updateApplicationStatus(applicationId, status);
        return ResponseEntity.ok().build();
    }
}
