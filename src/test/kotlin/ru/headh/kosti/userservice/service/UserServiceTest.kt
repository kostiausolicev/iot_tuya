package ru.headh.kosti.userservice.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
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
import ru.headh.kosti.userservice.dto.request.UserAuthRequest
import ru.headh.kosti.userservice.dto.request.UserRegisterRequest
import ru.headh.kosti.userservice.entity.UserEntity
import ru.headh.kosti.userservice.exception.UserException
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
    @Value("\${jwt.secret}")
    private lateinit var secret: String

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var tokenService: TokenService

    @Autowired
    lateinit var bcryptEncoder: PasswordEncoder

    @BeforeEach
    fun clearDB() {
        userRepository.deleteAll()
    }

    @Nested
    inner class RegisterUserTest {
        @Test
        fun postgresRun() {
            assertThat(postgresqlContainer.isRunning).isTrue()
        }

        @Test
        fun success() {
            val token = UserRegisterRequest(
                name = "name",
                username = "username",
                password = "password",
                confirmPassword = "password"
            ).let { userService.register(it) }
            val id = tokenService.getUserId(token.accessToken)
            val expected = UserEntity(
                id = id,
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

        @Test
        fun `user exist`() {
            UserEntity(
                name = "name",
                username = "username",
                password = bcryptEncoder.encode("password")
            ).also { userRepository.save(it) }

            assertThrows<UserException> {
                UserRegisterRequest(
                    name = "name",
                    username = "username",
                    password = "password",
                    confirmPassword = "password"
                ).also { userService.register(it) }
            }
        }
    }

    @Nested
    inner class AuthUserTest {
        @Test
        fun success() {
            val expectedId = UserEntity(
                name = "name",
                username = "username",
                password = bcryptEncoder.encode("password")
            ).let { userRepository.save(it) }.id

            val actualId = UserAuthRequest(
                username = "username",
                password = "password"
            ).let {
                val access = userService.auth(it).accessToken
                tokenService.getUserId(access)
            }
            assertEquals(expectedId, actualId)
        }

        @Test
        fun `user not found`() {
            assertThrows<UserException> {
                userService.auth(UserAuthRequest(username = "username", password = "password"))
            }
        }
    }

    private fun TokenService.getUserId(token: String): Int =
        JWT.require(Algorithm.HMAC256(secret))
            .build()
            .verify(token)
            .getClaim("id").asInt()
}