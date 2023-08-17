package com.hedley.groceriesstats.franchises

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.math.BigInteger
import io.swagger.v3.oas.annotations.parameters.RequestBody as OASRequestBody

@RestController
@RequestMapping("/franchises")
@Tag(name = "Franchises API")
class FranchiseController(val service: FranchiseService) {

    @GetMapping("/byName/{name}")
    @Operation(
        summary = "Finds a Franchise by name containing a certain string ignoring the case",
        description = "Returns 200 if successful",
        parameters = [
            Parameter(name = "name", required = true, example = "Albert")
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
    fun findByName(@PathVariable name: String) =
        service.findByName(name)

    @GetMapping("/{id}")
    @Operation(
        summary = "Finds a Franchise by id",
        description = "Returns 200 if successful",
        parameters = [
            Parameter(name = "id", required = true, example = "1111")
        ]
    )
    @ApiResponses(
        value = [
            ApiResponse(
                description = "Successful operation",
                responseCode = "200"
            ),
            ApiResponse(
                description = "Used an invalid id",
                responseCode = "403"
            )
        ]
    )
    fun findById(@PathVariable id: BigInteger) =
        service.findById(id)

    @PostMapping
    @Operation(
        summary = "Create a Franchise",
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
    fun save(
        @RequestBody @OASRequestBody(
            description = "New Franchise",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(type = "string", example = "Lidl"),
            )]
        ) dto: SaveFranchiseDTO
    ) =
        service.save(dto)

}