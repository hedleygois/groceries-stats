package com.hedley.groceriesstats.itemcategory

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.math.BigInteger

@RestController
@RequestMapping("/itemsCategory")
@Tag(name = "Item Category API")
@CrossOrigin
class ItemCategoryController(private val service: ItemCategoryService) {

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
    fun save(@RequestBody dto: SaveItemCategoryDTO) =
        service.save(dto)

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun list() = service.list()

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete a Item Category by id",
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
        ]
    )
    fun delete(@PathVariable id: BigInteger) = service.delete(id)
}