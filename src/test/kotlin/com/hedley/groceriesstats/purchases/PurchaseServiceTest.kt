package com.hedley.groceriesstats.purchases

import com.hedley.groceriesstats.BaseIntegrationTest
import com.hedley.groceriesstats.brands.Brand
import com.hedley.groceriesstats.brands.BrandRepository
import com.hedley.groceriesstats.franchises.Franchise
import com.hedley.groceriesstats.franchises.FranchiseRepository
import com.hedley.groceriesstats.itemcategory.ItemCategory
import com.hedley.groceriesstats.itemcategory.ItemCategoryRepository
import com.hedley.groceriesstats.itempurchase.ItemPurchase
import com.hedley.groceriesstats.itempurchase.ItemPurchaseRepository
import com.hedley.groceriesstats.items.ItemRepository
import com.hedley.groceriesstats.items.SaveItemDTO
import com.hedley.groceriesstats.paymenttype.PaymentType
import com.hedley.groceriesstats.paymenttype.PaymentTypeRepository
import com.hedley.groceriesstats.purchase.ItemPurchaseCreation
import com.hedley.groceriesstats.purchase.PurchaseService
import com.hedley.groceriesstats.purchase.SavePurchaseDTO
import com.hedley.groceriesstats.supermarkets.SaveSupermarketDTO
import com.hedley.groceriesstats.supermarkets.SupermarketRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.junit.jupiter.Testcontainers
import java.math.BigInteger
import java.time.Duration
import java.time.Instant

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class PurchaseServiceTest : BaseIntegrationTest() {

    @Autowired
    private lateinit var purchaseService: PurchaseService

    @Autowired
    private lateinit var paymentTypeRepository: PaymentTypeRepository

    @Autowired
    private lateinit var supermarketRepository: SupermarketRepository

    @Autowired
    private lateinit var itemRepository: ItemRepository

    @Autowired
    private lateinit var franchiseRepository: FranchiseRepository

    @Autowired
    private lateinit var itemPurchaseRepository: ItemPurchaseRepository

    @Autowired
    private lateinit var itemCategoryRepository: ItemCategoryRepository

    @Autowired
    private lateinit var brandRepository: BrandRepository

    @AfterEach
    fun clear() {
        purchaseService.repository.deleteAll()
    }

    @Test
    fun `it saves the items associated to the purchase`() {
        val now = Instant.now()
        val firstItem = itemRepository.findById(BigInteger.ONE).block()
        val secondItem = itemRepository.findById(BigInteger.TWO).block()
        purchaseService.save(
            SavePurchaseDTO(
                paymentTypeId = BigInteger.ONE,
                supermarketId = BigInteger.ONE,
                totalValue = 10.5F,
                date = now.toString(),
                items = listOf(
                    ItemPurchaseCreation(
                        itemId = firstItem!!.id,
                        itemName = firstItem.name,
                        value = 5.5F,
                        grams = 0.5F,
                        quantity = 1
                    ),
                    ItemPurchaseCreation(
                        itemId = secondItem!!.id,
                        itemName = secondItem.name,
                        value = 5F,
                        grams = 10F,
                        quantity = 1
                    )
                )
            )
        )
        Assertions.assertEquals(
            itemPurchaseRepository.findByPurchase(BigInteger.ONE).collectList().block(), listOf(
                ItemPurchase(
                    id = BigInteger.ONE,
                    itemId = firstItem.id,
                    purchaseId = BigInteger.ONE,
                    value = 5.5F,
                    grams = 0.5F,
                    quantity = 1
                ),
                ItemPurchase(
                    id = BigInteger.TWO,
                    itemId = secondItem.id,
                    purchaseId = BigInteger.ONE,
                    value = 5F,
                    grams = 10F,
                    quantity = 1
                )
            )
        )
    }

    @BeforeEach
    fun setup(): Unit {
        paymentTypeRepository.save(PaymentType(type = "Test"))
        franchiseRepository.save(Franchise(name = "Test"))
        supermarketRepository.save(
            SaveSupermarketDTO(
                name = "Super Test",
                franchiseId = BigInteger.ONE
            )
        )
        itemCategoryRepository.save(ItemCategory(name = "Category Test"))
        brandRepository.save(Brand(name = "Brand Test"))
        brandRepository.save(Brand(name = "Brand Test 2"))
        itemRepository.save(
            SaveItemDTO(
                name = "Test Item",
                categoryId = BigInteger.ONE,
                brandId = BigInteger.ONE
            )
        )
        itemRepository.save(
            SaveItemDTO(
                name = "Test Item 2",
                categoryId = BigInteger.ONE,
                brandId = BigInteger.TWO
            )
        )
    }
}