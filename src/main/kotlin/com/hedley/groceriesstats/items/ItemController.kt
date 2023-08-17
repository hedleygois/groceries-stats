package com.hedley.groceriesstats.items

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.math.BigInteger

@RestController
@RequestMapping("/items")
@Tag(name = "Items API")
class ItemController(val service: ItemService) {

    @GetMapping("/byName/{name}")
    fun findByName(@PathVariable name: String) = service.findByName(name)

    @GetMapping("/{id}")
    fun findById(@PathVariable id: BigInteger) = service.findById(id)

    @PostMapping
    fun create(@RequestBody dto: SaveItemDTO) = service.save(dto)
}