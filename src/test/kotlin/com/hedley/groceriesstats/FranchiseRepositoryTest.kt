package com.hedley.groceriesstats

import com.hedley.groceriesstats.franchises.Franchise
import com.hedley.groceriesstats.franchises.FranchiseRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class FranchiseRepositoryTest : BaseIntegrationTest() {

    @Autowired
    private lateinit var franchiseRepository: FranchiseRepository

    @AfterEach
    fun clear() {
        franchiseRepository.deleteAll()
    }

    @Test
    fun `it loads a franchise by id`() {
        val saved = franchiseRepository.save(Franchise(id = null, name = "Test")).block()
        val found = franchiseRepository.findById(saved!!.id!!).block()
        Assertions.assertEquals(saved, found)
    }

    @Test
    fun `it finds by name ignoring the case`() {
        val saved = franchiseRepository.save(Franchise(id = null, name = "Testing")).block()
        val lowercase = franchiseRepository.findByNameContainingIgnoreCase("test").collectList().block()
        val uppercase = franchiseRepository.findByNameContainingIgnoreCase("Test").collectList().block()
        Assertions.assertEquals(saved, lowercase!![0])
        Assertions.assertEquals(saved, uppercase!![0])
    }
}