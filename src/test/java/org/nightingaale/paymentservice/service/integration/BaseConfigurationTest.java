package org.nightingaale.paymentservice.service.integration;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.vault.VaultContainer;

@Testcontainers
@ActiveProfiles("test")
public abstract class BaseConfigurationTest {

    private static final String VAULT_TOKEN = "root";

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:17.2")
                    .withDatabaseName("postgres")
                    .withUsername("postgres")
                    .withPassword("example");

    @Container
    // TestContainers accept "apache/kafka:?" Docker image
    // In my docker configuration was used "confluentinc/cp-kafka:7.5.0" image & confluentinc/cp-zookeeper:7.5.0(Kafka + Zookeper, no pure KRaft)
    static KafkaContainer kafka =
            new KafkaContainer(DockerImageName.parse("apache/kafka:3.9.2"));

    @Container
    // Build Vault Hashicorp container for bootstrap.yaml & creds
    // Do not use :latest version for any Docker Images like there(TestContainers won't accept it + best practice)
    static VaultContainer<?> vault =
            new VaultContainer<>("hashicorp/vault:1.21.0")
                    .withVaultToken(VAULT_TOKEN)
                    .withInitCommand(
                            "secrets enable -path=secret kv-v2 && " +
                                    "vault kv put secret/prod/payment-service " +
                                    "spring.datasource.username=postgres " +
                                    "spring.datasource.password=example " +
                                    "some.test.property=test-value"
                    );

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {

        // Postgres
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);

        // Kafka
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);

        // Vault
        registry.add("spring.cloud.vault.uri", vault::getHttpHostAddress);
        registry.add("spring.cloud.vault.token", () -> VAULT_TOKEN);
        registry.add("spring.cloud.vault.kv.backend", () -> "secret");
        registry.add("spring.cloud.vault.kv.enabled", () -> true);

        registry.add("spring.config.import", () -> "vault://secret/prod/payment-service");

        assert postgres.isRunning();
        assert kafka.isRunning();
        assert vault.isRunning();
    }
}