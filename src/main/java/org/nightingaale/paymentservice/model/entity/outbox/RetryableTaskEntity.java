package org.nightingaale.paymentservice.model.entity.outbox;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;
import org.nightingaale.paymentservice.model.enums.outbox.RetryableTaskStatus;
import org.nightingaale.paymentservice.model.enums.outbox.RetryableTaskType;
import org.nightingaale.paymentservice.util.outbox.RetryableTaskStatusConverter;
import org.nightingaale.paymentservice.util.outbox.RetryableTaskTypeConverter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "retry_tasks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RetryableTaskEntity {

    @Id
    private UUID id;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    @Version
    private Long version;

    @Column(columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    private String payload;

    @Convert(converter = RetryableTaskTypeConverter.class)
    private RetryableTaskType type;

    @Convert(converter = RetryableTaskStatusConverter.class)
    private RetryableTaskStatus status;

    private Instant retryTime;
}

