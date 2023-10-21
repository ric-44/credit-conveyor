package ru.ashabelskii.deal.db.specification;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ru.ashabelskii.deal.db.entity.Application;
import ru.ashabelskii.deal.db.entity.Client;
import ru.ashabelskii.deal.db.enums.ApplicationStatus;

import javax.persistence.criteria.Join;
import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApplicationSpecifications {

    private static final String CLIENT_TABLE = "client";

    public static Specification<Application> hasStatus(ApplicationStatus status) {
        if (Objects.isNull(status)) {
            return null;
        }
        return (application, cq, cb) -> cb.equal(application.get("status"), status);
    }

    public static Specification<Application> hasCreatedFrom(LocalDateTime createdFrom) {
        if (Objects.isNull(createdFrom)) {
            return null;
        }
        return (application, cq, cb) -> cb.greaterThanOrEqualTo(application.get("createdAt"), createdFrom);
    }

    public static Specification<Application> hasClientFirstName(String firstName) {
        if (Objects.isNull(firstName)) {
            return null;
        }

        return (application, cq, cb) -> {
            Join<Client, Application> applicationsClient = application.join(CLIENT_TABLE);
            return cb.equal(applicationsClient.get("firstName"), firstName);
        };
    }

    public static Specification<Application> hasClientLastName(String lastName) {
        if (Objects.isNull(lastName)) {
            return null;
        }

        return (application, cq, cb) -> {
            Join<Client, Application> applicationsClient = application.join(CLIENT_TABLE);
            return cb.equal(applicationsClient.get("lastName"), lastName);
        };
    }

    public static Specification<Application> hasClientMiddleName(String middleName) {
        if (Objects.isNull(middleName)) {
            return null;
        }

        return (application, cq, cb) -> {
            Join<Client, Application> applicationsClient = application.join(CLIENT_TABLE);
            return cb.equal(applicationsClient.get("middleName"), middleName);
        };
    }
}
