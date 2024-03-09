package ru.headh.kosti.userservice.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.testcontainers.junit.jupiter.Testcontainers
import ru.headh.kosti.userservice.configuration.ApplicationConfiguration
import ru.headh.kosti.userservice.configuration.AuthenticationConfiguration
import ru.headh.kosti.userservice.container.PostgresTestContainer
import ru.headh.kosti.userservice.container.PostgresTestContainer.Companion.postgresqlContainer
import ru.headh.kosti.userservice.dto.request.UserRegisterRequest
import ru.headh.kosti.userservice.entity.UserEntity
import ru.headh.kosti.userservice.repository.UserRepository


@SpringBootTest
@Import(UserService::class)
@ImportAutoConfiguration(classes = [AuthenticationConfiguration::class])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@ExtendWith(SpringExtension::class)
@ContextConfiguration(
    classes = [ApplicationConfiguration::class, AuthenticationConfiguration::class],
    initializers = [PostgresTestContainer.Initializer::class]
)
class UserServiceTest {
    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var bcryptEncoder: PasswordEncoder

    private val mapper = jacksonObjectMapper()

    @Nested
    inner class RegisterUserTest {
        @Test
        fun postgresRun() {
            assertThat(postgresqlContainer.isRunning).isTrue()
        }

        @Test
        fun success() {
            UserRegisterRequest(
                name = "name",
                username = "username",
                password = "password",
                confirmPassword = "password"
            ).also { userService.register(it) }
            val expected = UserEntity(
                id = 1,
                name = "name",
                username = "username",
                password = bcryptEncoder.encode("password")
            )
            val actual = userRepository.findByIdOrNull(1)
            assertAll(
                { assertEquals(expected.name, actual?.name) },
                { assertEquals(expected.username, actual?.username) },
                { assertThat(bcryptEncoder.matches("password", actual?.password)).isTrue() }
            )
        }
    }
}