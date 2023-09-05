package ru.ashabelskii.deal.db.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ashabelskii.deal.db.entity.Application;
import ru.ashabelskii.deal.db.entity.StatusHistory;
import ru.ashabelskii.deal.db.enums.ApplicationStatus;
import ru.ashabelskii.deal.db.enums.ChangeType;
import ru.ashabelskii.deal.db.repository.ApplicationRepository;
import ru.ashabelskii.deal.metric.MetricMonitoring;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ApplicationHelper {

    private final ApplicationRepository applicationRepository;

    public Application getById(UUID id) {
        return applicationRepository.getReferenceById(id);
    }

    public Application save(Application application) {
        return applicationRepository.save(application);
    }

    @MetricMonitoring
    public Application saveAndUpdateStatus(Application application, ApplicationStatus status, ChangeType changeType) {
        updateStatus(application, status, changeType);
        return applicationRepository.save(application);
    }

    public void updateStatus(Application application, ApplicationStatus status, ChangeType changeType) {
        StatusHistory history = createStatusHistory(status, changeType);
        application.setStatus(status);
        application.addHistory(history);
    }

    public boolean checkStatus(Application application, ApplicationStatus applicationStatus) {
        return application.getStatus().equals(applicationStatus);
    }

    private static StatusHistory createStatusHistory(ApplicationStatus status, ChangeType changeType) {
        StatusHistory history = new StatusHistory();
        history.setStatus(status);
        history.setChangeType(changeType);
        return history;
    }
}
