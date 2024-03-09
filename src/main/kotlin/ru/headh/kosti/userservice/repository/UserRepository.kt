package ru.headh.kosti.userservice.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.headh.kosti.userservice.entity.UserEntity

@Repository
interface UserRepository : JpaRepository<UserEntity, Int> {
    fun findByUsername(username: String) : UserEntity?
}