package org.nightingaale.paymentservice.model.enums;

import java.time.LocalDate;
import java.time.LocalDateTime;

public enum PaymentPeriod {
    TODAY {
        @Override
        public LocalDateTime getStart() {
            return LocalDate.now().atStartOfDay();
        }

        @Override
        public LocalDateTime getEnd() {
            return LocalDateTime.now();
        }
    },
    YESTERDAY {
        @Override
        public LocalDateTime getStart() {
            return LocalDate.now().minusDays(1).atStartOfDay();
        }

        @Override
        public LocalDateTime getEnd() {
            return LocalDate.now().atStartOfDay();
        }
    },
    LAST_7_DAYS {
        @Override
        public LocalDateTime getStart() {
            return LocalDate.now().minusDays(7).atStartOfDay();
        }

        @Override
        public LocalDateTime getEnd() {
            return LocalDateTime.now();
        }
    },
    MONTH {
        @Override
        public LocalDateTime getStart() {
            return LocalDate.now().withDayOfMonth(1).atStartOfDay();
        }

        @Override
        public LocalDateTime getEnd() {
            return LocalDateTime.now();
        }
    };

    public abstract LocalDateTime getStart();
    public abstract LocalDateTime getEnd();
}
