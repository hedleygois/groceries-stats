package com.hedley.groceriesstats

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean


@SpringBootApplication
class GroceriesStatsApplication

fun main(args: Array<String>) {
	runApplication<GroceriesStatsApplication>(*args)
}

@Bean
fun publicApi(): OpenAPI {
	return OpenAPI()
		.info(
			Info()
				.title("Your API Name")
				.version("1.0")
				.description("Your API Description")
		)
}