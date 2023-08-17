package com.hedley.groceriesstats.itempurchase

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.math.BigInteger

@RestController
@RequestMapping("/itemsPurchase")
@Tag(name = "Item Purchase API")
class ItemPurchaseController(private val service: ItemPurchaseService) {

    @GetMapping("/purchases")
    @Operation(
        summary = "Finds an Item tied to Purchases by Purchase ID",
        description = "Returns 200 if successful",
        parameters = [
            Parameter(
                name = "ids",
                required = true,
                example = "1111,2222,3333",
                array = ArraySchema(schema = Schema(type = "number"))
            )
        ]
    )
    @ApiResponses(
        value = [
            ApiResponse(
                description = "Successful operation",
                responseCode = "200"
            ),
            ApiResponse(
                description = "Used an invalid name",
                responseCode = "403"
            )
        ]
    )
    fun findByPurchases(@RequestParam(required = true) ids: List<BigInteger>) =
        service.findByPurchases(ids)


    @GetMapping("/{id}")
    @Operation(
        summary = "Finds an Item tied to Purchases by Item ID",
        description = "Returns 200 if successful",
        parameters = [
            Parameter(
                name = "id",
                required = true,
                example = "1111",
            )
        ]
    )
    @ApiResponses(
        value = [
            ApiResponse(
                description = "Successful operation",
                responseCode = "200"
            ),
            ApiResponse(
                description = "Used an invalid name",
                responseCode = "403"
            )
        ]
    )
    fun findByItemId(@PathVariable id: BigInteger) =
        service.findByItemId(id)

//    @GetMapping("/purchases/{id}")
//    @Operation(
//        summary = "Finds an Item tied to Purchases by Purchase ID",
//        description = "Returns 200 if successful",
//        parameters = [
//            Parameter(
//                name = "id",
//                required = true,
//                example = "1111"
//            )
//        ]
//    )
//    @ApiResponses(
//        value = [
//            ApiResponse(
//                description = "Successful operation",
//                responseCode = "200"
//            ),
//            ApiResponse(
//                description = "Used an invalid name",
//                responseCode = "403"
//            )
//        ]
//    )
//    fun findByPurchaseId(@PathVariable id: BigInteger) =
//        service.findByPurchaseId(id)

    @PostMapping
    @Operation(
        summary = "Create an Item tied to a Purchase",
        description = "Returns 200 if successful",
        requestBody = io.swagger.v3.oas.annotations.parameters.RequestBody()
    )

    @ApiResponses(
        value = [
            ApiResponse(
                description = "Successful operation",
                responseCode = "200"
            ),
            ApiResponse(
                description = "Used an invalid name",
                responseCode = "403"
            )
        ]
    )
    fun create(
        @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "New ItemPurchase",
            content = [Content(
                mediaType = "application/json",
//                schema = Schema(type = "string", example = "Lidl"),
            )]
        ) dto: SaveItemPurchaseDTO
    ) =
        service.create(dto)

}