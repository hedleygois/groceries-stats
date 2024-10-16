package com.hedley.groceriesstats

import org.junit.jupiter.api.BeforeAll
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container

open class BaseIntegrationTest {
    companion object {
        @Container
        val postgreSQLContainer: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:15")
            .withDatabaseName("integration-tests-db")

        @DynamicPropertySource
        @JvmStatic
        fun registerDynamicProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl)
            registry.add("spring.datasource.username", postgreSQLContainer::getUsername)
            registry.add("spring.datasource.password", postgreSQLContainer::getPassword)

            registry.add("spring.r2dbc.url", this::r2dbcUrl)
            registry.add("spring.r2dbc.username", postgreSQLContainer::getUsername)
            registry.add("spring.r2dbc.password", postgreSQLContainer::getPassword)

            registry.add("spring.flyway.url", postgreSQLContainer::getJdbcUrl)
            registry.add("spring.flyway.user", postgreSQLContainer::getUsername)
            registry.add("spring.flyway.password", postgreSQLContainer::getPassword)

        }

        private fun r2dbcUrl(): String {
            return "r2dbc:postgresql://${postgreSQLContainer.host}:${
                postgreSQLContainer.getMappedPort(
                    PostgreSQLContainer.POSTGRESQL_PORT
                )
            }/${postgreSQLContainer.databaseName}"
        }

        @JvmStatic
        @BeforeAll
        internal fun setUp(): Unit {
            postgreSQLContainer.start()
        }
    }
}