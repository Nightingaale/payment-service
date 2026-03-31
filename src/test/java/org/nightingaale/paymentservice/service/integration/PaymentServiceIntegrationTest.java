package org.nightingaale.paymentservice.service.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test") // remove @EnableScheduling & @Scheduler for JUnit tests(watch SchedulerConfig)
class PaymentServiceIntegrationTest extends BaseConfigurationTest {

    @Autowired
    private ApplicationContext context;

    @Test
    void contextLoads() {
        assertThat(context).isNotNull();

        kafka.start();
        System.out.println("Kafka bootstrap servers: " + kafka.getBootstrapServers());

        assertThat(postgres.isRunning()).isTrue();
        assertThat(kafka.isRunning()).isTrue();
        assertThat(vault.isRunning()).isTrue();
    }
}