package ru.headh.kosti.homeservice.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional
import ru.headh.kosti.homeservice.dto.request.HomeRequest
import ru.headh.kosti.homeservice.entity.HomeEntity
import ru.headh.kosti.homeservice.error.ApiError
import ru.headh.kosti.homeservice.expectApiException
import ru.headh.kosti.homeservice.repositoty.HomeRepository
import ru.headh.kosti.homeservice.service.container.PostgresTestContainer


@Import(HomeService::class)
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(
    classes = [],
    initializers = [PostgresTestContainer.Initializer::class]
)
@ExtendWith(SpringExtension::class)
class HomeServiceTest {

    private val mapper = jacksonObjectMapper()

    @Autowired
    private lateinit var homeRepository: HomeRepository

    @Autowired
    private lateinit var homeService: HomeService

    @Nested
    @Transactional
    inner class CreateHome {

        @AfterEach
        fun delete() {
            homeRepository.deleteAllInBatch()
            homeRepository.flush()
        }

        @Test
        fun success() {
            val request =
                HomeRequest(
                    name = "Test 1",
                    address = null
                )
            val ownerId = 1
            val expectedHome = HomeEntity(
                id = 1,
                name = request.name,
                address = request.address,
                ownerId = ownerId,
                rooms = emptyList()
            )

            val real = homeService.createHome(request, ownerId)

            assertEquals(mapper.writeValueAsString(expectedHome), mapper.writeValueAsString(real))
        }

        @Test
        fun `throw exception not correct ownerId`() {
            val request =
                HomeRequest(
                    name = "Test 2",
                    address = null
                )
            val ownerId = -1
            expectApiException(ApiError.WRONG_REQUEST_DATA) { homeService.createHome(request, ownerId) }
        }
    }

    @Nested
    @Transactional
    inner class GetHome {
        private var testHome1 = HomeEntity(
            id = 1,
            name = "Test home 1",
            address = "Test address 1",
            ownerId = 1,
            rooms = emptyList()
        )
        private var testHome2 = HomeEntity(
            id = 2,
            name = "Test home 2",
            address = "Test address 2",
            ownerId = 1,
            rooms = emptyList()
        )

        @BeforeEach
        fun saveAnyData() {
            testHome1 = homeRepository.save(testHome1)
            testHome2 = homeRepository.save(testHome2)
            homeRepository.flush()
        }

        @AfterEach
        fun delete() {
            homeRepository.deleteAllInBatch()
            homeRepository.flush()
        }

        @Test
        fun success() {
            val id = testHome1.id
            val ownerId = testHome1.ownerId
            val expected = testHome1
            val real = homeService.getHome(id, ownerId)

            assertEquals(mapper.writeValueAsString(real), mapper.writeValueAsString(expected))
        }

        @Test
        fun `throw exception home not found`() {
            val id = -1
            val ownerId = testHome1.ownerId

            expectApiException(ApiError.HOME_NOT_FOUND) { homeService.getHome(id, ownerId) }
        }

        @Test
        fun `throw exception action is cancelled`() {
            val id = testHome1.id
            val ownerId = testHome1.ownerId + 1
            expectApiException(ApiError.ACTION_IS_CANCELLED) { homeService.getHome(id, ownerId) }
        }

        @Test
        fun `throw exception wrong ownerId`() {
            val id = testHome1.id
            val ownerId = -1
            expectApiException(ApiError.WRONG_REQUEST_DATA) { homeService.getHome(id, ownerId) }
        }
    }
}