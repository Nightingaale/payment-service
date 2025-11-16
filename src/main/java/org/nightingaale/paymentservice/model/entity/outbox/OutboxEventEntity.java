package org.nightingaale.paymentservice.model.entity.outbox;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnTransformer;
import org.nightingaale.paymentservice.model.enums.outbox.OutboxType;
import org.nightingaale.paymentservice.util.outbox.OutboxTypeConverter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "outbox")
@Getter
@Setter
@NoArgsConstructor
public class OutboxEventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String aggregateType;
    private String aggregateId;

    @Convert(converter = OutboxTypeConverter.class)
    private OutboxType type;

    @Version
    private Long version;

    @Column(columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    private String payload;

    private Instant createdAt;
    private boolean processed;
}
