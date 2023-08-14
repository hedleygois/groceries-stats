package com.hedley.groceriesstats.items

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigInteger

@RestController
@RequestMapping("/items")
class ItemController(val service: ItemService) {

    @GetMapping("/byName/{name}")
    fun findByName(@PathVariable name: String) = service.findByName(name)

    @GetMapping("/{id}")
    fun findById(@PathVariable id: BigInteger) = service.findById(id)

    @PostMapping
    fun create(@RequestBody dto: SaveItemDTO) = service.save(dto)
}