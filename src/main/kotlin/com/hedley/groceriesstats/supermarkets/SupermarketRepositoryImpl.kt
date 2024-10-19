package com.hedley.groceriesstats.supermarkets

import com.hedley.groceriesstats.franchises.FranchiseDTO
import io.r2dbc.spi.Readable
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.math.BigInteger

@Component
// Creating this because Spring Data R2JDBC STILL doesn't support relations... in 2023....
// Think about the contracts of Repositories in the future. Should they use DTOs or Entities? This comes from the above-mentioned problem
class SupermarketRepositoryImpl(
    private val databaseClient: DatabaseClient,
    private val defaultSupermarketRepository: DefaultSupermarketRepository, // I shouldn't need this but I'm lazy
) : SupermarketRepository {
    override fun findByNameWithFranchise(name: String): Flux<SupermarketDTO> =
        databaseClient.sql(
            """
            SELECT s.id as s_id, s.name as s_name, f.id as f_id, f.name as f_name 
            FROM supermarkets s
            INNER JOIN franchises f ON f.id = s.franchise_id 
            WHERE s.name LIKE :name                
        """.trimIndent()
        ).bind("name", name).map(::mapSupermarketSqlRowToSupermarketDTO).all()

    override fun findById(id: BigInteger): Mono<SupermarketDTO> =
        databaseClient.sql(
            """
            SELECT s.id as s_id, s.name as s_name, f.id as f_id, f.name as f_name 
            FROM supermarkets s
            INNER JOIN franchises f ON f.id = s.franchise_id 
            WHERE s.id = :id                
        """.trimIndent()
        ).bind("id", id).map(::mapSupermarketSqlRowToSupermarketDTO).all().toMono()

    override fun list(): Flux<SupermarketDTO> =
        databaseClient.sql(
            """
            SELECT s.id as s_id, s.name as s_name, f.id as f_id, f.name as f_name
            FROM supermarkets s
            INNER JOIN franchises f ON f.id = s.franchise_id
        """.trimIndent()
        ).map(::mapSupermarketSqlRowToSupermarketDTO).all()


    override fun save(dto: SaveSupermarketDTO): Mono<SavedSupermarketDTO> =
        defaultSupermarketRepository.save(Supermarket(id = null, name = dto.name, franchiseId = dto.franchiseId))
            .map { supermarket ->
                SavedSupermarketDTO(
                    id = supermarket.id ?: BigInteger.ZERO,
                    name = supermarket.name,
                    franchiseId = supermarket.franchiseId
                )
            }

    private fun mapSupermarketSqlRowToSupermarketDTO(row: Readable): SupermarketDTO =
        SupermarketDTO(
            id = row.get("s_id", BigInteger::class.java) ?: BigInteger.ZERO,
            name = row.get("s_name", String::class.java) ?: "",
            franchise = FranchiseDTO(
                id = row.get("f_id", BigInteger::class.java) ?: BigInteger.ZERO,
                name = row.get("f_name", String::class.java) ?: ""
            )
        )
}
