package ru.ashabelskii.deal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.ashabelskii.deal.api.AdminApi;
import ru.ashabelskii.deal.audit.Audit;
import ru.ashabelskii.deal.audit.model.AuditEventType;
import ru.ashabelskii.deal.dto.ApplicationDto;
import ru.ashabelskii.deal.dto.ApplicationStatusDto;
import ru.ashabelskii.deal.service.AdminService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AdminController implements AdminApi {

    private final AdminService adminService;

    @Override
    @Audit(eventType = AuditEventType.ADMIN_APPLICATIONS)
    public ResponseEntity<List<ApplicationDto>> getApplications(ApplicationStatusDto status,
                                                                LocalDateTime createdFrom,
                                                                String firstName,
                                                                String lastName,
                                                                String middleName) {
        List<ApplicationDto> applications = adminService.getApplications(status, createdFrom, firstName,
                lastName, middleName);
        return ResponseEntity.ok(applications);
    }

    @Override
    @Audit(eventType = AuditEventType.ADMIN_UPDATE_APPLICATION_STATUS)
    public ResponseEntity<Void> updateApplicationStatus(UUID applicationId, ApplicationStatusDto status,
                                                        HttpHeaders headers) {
        adminService.updateApplicationStatus(applicationId, status);
        return ResponseEntity.ok().build();
    }
}
