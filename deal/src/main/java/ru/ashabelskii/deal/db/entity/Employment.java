package ru.ashabelskii.deal.db.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import ru.ashabelskii.deal.db.enums.EmploymentStatus;
import ru.ashabelskii.deal.db.enums.Position;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "employment")
public class Employment {
    @Id
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EmploymentStatus status;
    @Column(name = "employer_inn", nullable = false)
    private String employerINN;
    @Column(nullable = false)
    private BigDecimal salary;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Position position;
    @Column(nullable = false)
    private Integer workExperienceTotal;
    @Column(nullable = false)
    private Integer workExperienceCurrent;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Employment that = (Employment) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
