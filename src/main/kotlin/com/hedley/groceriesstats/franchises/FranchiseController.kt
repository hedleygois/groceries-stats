package com.hedley.groceriesstats.franchises

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.math.BigInteger

@RestController
@RequestMapping("/franchises")
class FranchiseController(val service: FranchiseService) {

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
    fun save(@RequestBody dto: SaveFranchiseDTO) =
        service.save(dto)

}