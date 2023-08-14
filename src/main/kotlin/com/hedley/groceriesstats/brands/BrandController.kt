package com.hedley.groceriesstats.brands

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.math.BigInteger

@RestController
@RequestMapping("/brands")
class BrandController(val service: BrandService) {

    @GetMapping("/byName/{name}")
    @ResponseStatus(HttpStatus.OK)
    fun findByName(@PathVariable name: String) =
        service.findByName(name)

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun findById(@PathVariable id: BigInteger) =
        service.findById(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun save(@RequestBody dto: SaveBrandDTO) =
        service.save(dto)

}