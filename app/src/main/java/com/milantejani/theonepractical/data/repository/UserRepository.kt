package com.milantejani.theonepractical.data.repository

import androidx.lifecycle.LiveData
import com.milantejani.theonepractical.data.model.User
import com.milantejani.theonepractical.data.room.UserDao

class UserRepository(private val userDao: UserDao) {
    val readAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User) {
        userDao.addUser(user)
    }

    suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }
}