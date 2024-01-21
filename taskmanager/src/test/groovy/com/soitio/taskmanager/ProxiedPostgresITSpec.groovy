package com.soitio.taskmanager

import eu.rekawek.toxiproxy.Proxy
import eu.rekawek.toxiproxy.ToxiproxyClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.Network
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.containers.ToxiproxyContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Specification

@Testcontainers
@SpringBootTest(classes = [TaskmanagerApplication])
abstract class ProxiedPostgresITSpec extends Specification {

    static Network network = Network.newNetwork()

    static PostgreSQLContainer<?> psql = new PostgreSQLContainer<>("postgres:16.1")
            .withDatabaseName("taskmanager")
            .withUsername("taskmanager")
            .withPassword("taskmanager")
            .withExposedPorts(5432)
            .withNetwork(network)
            .withNetworkAliases("psql")

    static ToxiproxyContainer toxiproxy = new ToxiproxyContainer("ghcr.io/shopify/toxiproxy:2.7.0")
            .withNetwork(network)

    static ToxiproxyClient toxiproxyClient
    static Proxy proxy


    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        toxiproxyClient = new ToxiproxyClient(toxiproxy.getHost(), toxiproxy.getControlPort())
        proxy = toxiproxyClient.createProxy("psql", "0.0.0.0:8666", "psql:5432")
        var jdbcUrl = "jdbc:postgresql://%s:%d/%s".formatted(toxiproxy.getHost(), toxiproxy.getMappedPort(8666), psql.getDatabaseName())
        dynamicPropertyRegistry.add("spring.datasource.url", () -> jdbcUrl)
        dynamicPropertyRegistry.add("spring.datasource.username", psql::getUsername)
        dynamicPropertyRegistry.add("spring.datasource.password", psql::getPassword)
    }

    def setupSpec() {
        psql.start()
        toxiproxy.start()
    }

    def cleanupSpec() {
        psql.stop()
        toxiproxy.stop()
    }

}
