package com.hedley.groceriesstats.supermarkets

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.math.BigInteger

@RestController
@RequestMapping("/supermarkets")
@Tag(name = "Supermarkets API")
class SupermarketController(val service: SupermarketService) {

    @GetMapping("/byName/{name}")
    fun findByName(@PathVariable name: String) = service.findByName(name)

    @GetMapping("/{id}")
    fun findById(@PathVariable id: BigInteger) = service.findById(id)

    @PostMapping
    fun create(@RequestBody dto: SaveSupermarketDTO) = service.save(dto)
}