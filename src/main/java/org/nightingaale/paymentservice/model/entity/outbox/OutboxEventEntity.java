package org.nightingaale.paymentservice.model.entity.outbox;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "outbox")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OutboxEventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String aggregateType;
    private String aggregateId;
    private String type;

    @Lob
    private String payload;

    private Instant createdAt;
    private boolean processed;
}
