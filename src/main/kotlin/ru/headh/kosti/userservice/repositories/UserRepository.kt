package ru.headh.kosti.userservice.repositories

import org.springframework.data.jpa.repository.JpaRepository
import ru.headh.kosti.userservice.entity.UserEntity

interface UserRepository : JpaRepository<UserEntity, Int> {
    fun findByUsername(username: String) : UserEntity?
}