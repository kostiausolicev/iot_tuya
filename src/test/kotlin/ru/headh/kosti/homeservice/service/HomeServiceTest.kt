package ru.headh.kosti.homeservice.service

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import ru.headh.kosti.homeservice.dto.request.HomeRequest
import ru.headh.kosti.homeservice.repositoty.HomeRepository
import ru.headh.kosti.homeservice.service.container.PostgresTestContainer

@Import(HomeService::class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(
    classes = [],
    initializers = [PostgresTestContainer.Initializer::class]
)
@ExtendWith(SpringExtension::class)
class HomeServiceTest {
    @MockkBean
    private lateinit var homeService: HomeService

    @Autowired
    private lateinit var homeRepository: HomeRepository

//    @Nested
//    inner class CreateHome {
//        @Test
//        fun success() {
//            val request = HomeRequest(
//                name = "Test home 1",
//                address = null
//            )
//        }
//    }

}