package com.hedley.groceriesstats

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GroceriesStatsApplication

fun main(args: Array<String>) {
	runApplication<GroceriesStatsApplication>(*args)
}