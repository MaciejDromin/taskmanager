package com.soitio.taskmanager

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Specification

@Testcontainers
@SpringBootTest(classes = [TaskmanagerApplication])
abstract class BasePostgresITSpec extends Specification {

    static PostgreSQLContainer psql = new PostgreSQLContainer("postgres:16.1")
            .withDatabaseName("taskmanager")
            .withUsername("taskmanager")
            .withPassword("taskmanager")

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", psql::getJdbcUrl)
        dynamicPropertyRegistry.add("spring.datasource.username", psql::getUsername)
        dynamicPropertyRegistry.add("spring.datasource.password", psql::getPassword)
    }

    def setupSpec() {
        psql.start()
    }

    def cleanupSpec() {
        psql.stop()
    }

}
