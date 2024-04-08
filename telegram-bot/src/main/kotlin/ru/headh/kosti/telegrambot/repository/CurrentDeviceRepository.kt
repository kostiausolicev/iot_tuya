package ru.headh.kosti.telegrambot.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.headh.kosti.telegrambot.entity.CurrentDevice

@Repository
interface CurrentDeviceRepository: CrudRepository<CurrentDevice, String>