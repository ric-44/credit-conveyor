package ru.ashabelskii.deal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ashabelskii.deal.db.entity.Application;
import ru.ashabelskii.deal.db.enums.ApplicationStatus;
import ru.ashabelskii.deal.db.enums.ChangeType;
import ru.ashabelskii.deal.db.helper.ApplicationHelper;
import ru.ashabelskii.deal.dto.ApplicationDto;
import ru.ashabelskii.deal.dto.ApplicationStatusDto;
import ru.ashabelskii.deal.mapper.AdminMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminMapper adminMapper;

    private final ApplicationHelper applicationHelper;

    @Transactional(readOnly = true)
    public List<ApplicationDto> getApplications(ApplicationStatusDto statusDto,
                                                LocalDateTime createdFrom,
                                                String firstName,
                                                String lastName,
                                                String middleName) {
        ApplicationStatus status = adminMapper.mapApplicationStatus(statusDto);
        List<Application> applications = applicationHelper.getAllWithParameters(status, createdFrom, firstName,
                lastName, middleName);
        return adminMapper.mapApplicationsDto(applications);
    }

    @Transactional
    public void updateApplicationStatus(UUID applicationId, ApplicationStatusDto statusDto) {
        Application application = applicationHelper.getById(applicationId);
        ApplicationStatus applicationStatus = adminMapper.mapApplicationStatus(statusDto);
        applicationHelper.saveAndUpdateStatus(application, applicationStatus, ChangeType.MANUAL);
    }
}
