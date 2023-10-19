package ru.headh.kosti.homeservice.services

import org.springframework.stereotype.Service
import ru.headh.kosti.homeservice.repositoties.Home
import ru.headh.kosti.homeservice.repositoties.dao.HomeDao

@Service
class HomeService(
    val homeDao: HomeDao
) {
    fun creatingHome(name: String, address: String?) : Home {
        val home = homeDao.save(Home(name=name, address=address))
        return home
    }

    fun getHome(id: Int) : Home {
        val home = homeDao.findById(id).orElseThrow()
        return home
    }
    fun getHomeList(): List<Home> {
        return homeDao.findAll()
    }

    fun deleteHome(id: Int) {
        val home = homeDao.findById(id).orElseThrow()
        homeDao.delete(home)
    }

    fun updateHome(id: Int, name: String, address: String?) : Home {
        val home = homeDao.findById(id).orElseThrow()
        home.name = name
        home.address = address

        homeDao.updateHomeById(id, home.name, home.address)
        return home
    }
}