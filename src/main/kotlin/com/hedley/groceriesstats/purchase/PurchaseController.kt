package com.hedley.groceriesstats.purchase

import com.hedley.groceriesstats.supermarkets.SaveSupermarketDTO
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.math.BigInteger

@RestController
@RequestMapping("/purchases")
@Tag(name = "Purchases API")
@CrossOrigin
class PurchaseController(val service: PurchaseService) {

    @GetMapping("/{id}")
    fun findById(@PathVariable id: BigInteger) = service.findById(id)

    @GetMapping
    fun list() = service.list()

    @GetMapping("/byDate")
    fun findByDate(@RequestParam startDate: String, @RequestParam endDate: String) =
        service.findByDate(startDate, endDate)

    @PostMapping
    fun create(@RequestBody dto: SavePurchaseDTO) = service.save(dto)

}