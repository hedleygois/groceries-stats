package com.hedley.groceriesstats

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@OpenAPIDefinition
class GroceriesStatsApplication

fun main(args: Array<String>) {
	runApplication<GroceriesStatsApplication>(*args)
}