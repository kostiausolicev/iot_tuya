package ru.headh.kosti.homeservice.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import ru.headh.kosti.homeservice.dto.request.RoomRequest
import ru.headh.kosti.homeservice.entity.HomeEntity
import ru.headh.kosti.homeservice.entity.RoomEntity
import ru.headh.kosti.homeservice.error.ApiError
import ru.headh.kosti.homeservice.expectApiException
import ru.headh.kosti.homeservice.repositoty.HomeRepository
import ru.headh.kosti.homeservice.repositoty.RoomRepository
import ru.headh.kosti.homeservice.service.container.PostgresTestContainer

@Import(RoomService::class)
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(
    classes = [],
    initializers = [PostgresTestContainer.Initializer::class]
)
@ExtendWith(SpringExtension::class)
class RoomServiceTest {
    private val mapper = jacksonObjectMapper()

    @Autowired
    private lateinit var roomRepository: RoomRepository

    @Autowired
    private lateinit var roomService: RoomService

    @Autowired
    private lateinit var homeRepository: HomeRepository

    @Nested
    inner class Create {
        private var home = HomeEntity(
            id = 1,
            name = "Name",
            address = "Address",
            ownerId = 1,
            rooms = emptyList()
        )

        @BeforeEach
        fun saveData() {
            home = homeRepository.save(home)
            homeRepository.flush()
        }

        @AfterEach
        fun delete() {
            roomRepository.deleteAllInBatch()
            roomRepository.flush()
        }

        @Test
        fun success() {
            val request =
                RoomRequest(
                    name = "Test 1"
                )
            val ownerId = home.ownerId
            val expected = RoomEntity(
                id = home.id,
                name = request.name,
                home = home
            ).toDto()

            val real = roomService.create(
                homeId = home.id,
                roomRequest = request,
                ownerId = ownerId
            )

            Assertions.assertEquals(mapper.writeValueAsString(expected), mapper.writeValueAsString(real))
        }

        @Test
        fun `throw exception not correct ownerId`() {
            val request =
                RoomRequest(
                    name = "Test 1"
                )
            val ownerId = -1
            expectApiException(ApiError.ACTION_IS_CANCELLED) { roomService.create(
                homeId = home.id,
                roomRequest = request,
                ownerId = ownerId
            ) }
        }

        @Test
        fun `should throw exception HOM_NOT_FOUND`() {
            val request =
                RoomRequest(
                    name = "Test 1"
                )
            val ownerId = 1
            expectApiException(ApiError.HOME_NOT_FOUND) { roomService.create(
                homeId = home.id + 1,
                roomRequest = request,
                ownerId = ownerId
            ) }
        }
    }

    @Nested
    inner class Update {
        private var home = HomeEntity(
            id = 1,
            name = "Name",
            address = "Address",
            ownerId = 1,
            rooms = emptyList()
        )
        private var testRoom1 = RoomEntity(
            id = 1,
            name = "Name",
            home = home
        )

        @BeforeEach
        fun saveData() {
            home = homeRepository.save(home)
            homeRepository.flush()
            testRoom1 = roomRepository.save(
                RoomEntity(
                    id = testRoom1.id,
                    name = testRoom1.name,
                    home = home
                )
            )
        }

        @AfterEach
        fun delete() {
            roomRepository.deleteAllInBatch()
            roomRepository.flush()

            homeRepository.deleteAllInBatch()
            homeRepository.flush()
        }

        @Test
        fun success() {
            val request =
                RoomRequest(
                    name = "Test 1"
                )
            val roomId = testRoom1.id
            val ownerId = home.ownerId
            val expected = RoomEntity(
                id = roomId,
                name = request.name,
                home = home
            ).toDto()

            val real = roomService.update(
                roomId = roomId,
                roomRequest = request,
                ownerId = ownerId
            )

            Assertions.assertEquals(mapper.writeValueAsString(expected), mapper.writeValueAsString(real))
        }

        @Test
        fun `throw exception not correct ownerId`() {
            val request =
                RoomRequest(
                    name = "Test 1"
                )
            val roomId = testRoom1.id
            val ownerId = -1
            expectApiException(ApiError.ACTION_IS_CANCELLED) { roomService.update(
                roomId = roomId,
                roomRequest = request,
                ownerId = ownerId
            ) }
        }

        @Test
        fun `should throw exception ROOM_NOT_FOUND`() {
            val request =
                RoomRequest(
                    name = "Test 1"
                )
            val ownerId = 1
            expectApiException(ApiError.ROOM_NOT_FOUND) { roomService.update(
                roomId = testRoom1.id + 1,
                roomRequest = request,
                ownerId = ownerId
            ) }
        }
    }

    @Nested
    inner class Delete {
        private var home = HomeEntity(
            id = 1,
            name = "Name",
            address = "Address",
            ownerId = 1,
            rooms = emptyList()
        )
        private var testRoom1 = RoomEntity(
            id = 1,
            name = "Name",
            home = home
        )

        @BeforeEach
        fun saveData() {
            home = homeRepository.save(home)
            homeRepository.flush()
            testRoom1 = roomRepository.save(
                RoomEntity(
                    id = testRoom1.id,
                    name = testRoom1.name,
                    home = home
                )
            )
        }

        @AfterEach
        fun delete() {
            roomRepository.deleteAllInBatch()
            roomRepository.flush()

            homeRepository.deleteAllInBatch()
            homeRepository.flush()
        }

        @Test
        fun success() {
            val id = testRoom1.id
            val ownerId = testRoom1.home!!.ownerId
            roomService.delete(id, ownerId)
            Assertions.assertNull(roomRepository.findByIdOrNull(id))
        }

        @Test
        fun `should throw exception ROOM_NOT_FOUND`() {
            val id = testRoom1.id + 1
            val ownerId = testRoom1.home!!.ownerId
            expectApiException(ApiError.ROOM_NOT_FOUND) { roomService.delete(id, ownerId) }
        }

        @Test
        fun `should throw exception ACTION_IS_CANCELLED`() {
            val id = testRoom1.id
            val ownerId = testRoom1.home!!.ownerId + 1
            expectApiException(ApiError.ACTION_IS_CANCELLED) { roomService.delete(id, ownerId) }
        }
    }
}