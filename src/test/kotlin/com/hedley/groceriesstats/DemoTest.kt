package com.hedley.groceriesstats

import com.hedley.groceriesstats.franchises.Franchise
import com.hedley.groceriesstats.franchises.FranchiseRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
//@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class DemoTestTests {

    companion object {
        @Container
        val postgreSQLContainer: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:bullseye")
            .withDatabaseName("integration-tests-db")

        @DynamicPropertySource
        @JvmStatic
        fun registerDynamicProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl)
            registry.add("spring.datasource.username", postgreSQLContainer::getUsername)
            registry.add("spring.datasource.password", postgreSQLContainer::getPassword)

            registry.add("spring.r2dbc.url", Companion::r2dbcUrl)
            registry.add("spring.r2dbc.username", postgreSQLContainer::getUsername)
            registry.add("spring.r2dbc.password", postgreSQLContainer::getPassword)

            registry.add("spring.flyway.url", postgreSQLContainer::getJdbcUrl)
            registry.add("spring.flyway.user", postgreSQLContainer::getUsername)
            registry.add("spring.flyway.password", postgreSQLContainer::getPassword)

        }

        fun r2dbcUrl(): String {
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

    @Autowired
    private lateinit var franchiseRepository: FranchiseRepository

    @Test
    fun `it loads a franchise by id`() {
        val saved = franchiseRepository.save(Franchise(id = null, name = "Test")).block()
        val found = franchiseRepository.findById(saved!!.id!!).block()
        Assertions.assertEquals(saved, found)
    }
}