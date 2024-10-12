package com.hedley.groceriesstats.purchase

import com.hedley.groceriesstats.supermarkets.SaveSupermarketDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.math.BigInteger

@RestController
@RequestMapping("/purchases")
@Tag(name = "Purchases API")
@CrossOrigin
class PurchaseController(val service: PurchaseService) {

    private val log = LoggerFactory.getLogger(PurchaseController::class.java)

    @GetMapping("/{id}")
    fun findById(@PathVariable id: BigInteger) = service.findById(id)

    @GetMapping
    fun list() = service.list()

    @GetMapping("/byDate")
    fun findByDate(@RequestParam startDate: String, @RequestParam endDate: String) =
        service.findByDate(startDate, endDate)

    @PostMapping
    @Operation(
        summary = "Create a Purchase",
        description = "Returns 200 if successful",
        requestBody = io.swagger.v3.oas.annotations.parameters.RequestBody()
    )

    @ApiResponses(
        value = [
            ApiResponse(
                description = "Successful operation",
                responseCode = "200",
            ),
        ]
    )
    fun create(@RequestBody dto: SavePurchaseDTO): Mono<ResponseEntity<SavedPurchaseDTO>> =
        service.save(dto).map { ResponseEntity.ok(it) }

}