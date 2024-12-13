package com.dicoding.malanginsider.data.repository

import com.dicoding.malanginsider.data.pref.UserModel
import com.dicoding.malanginsider.data.pref.UserPreference

class UserRepository private constructor(
    private val userPreference: UserPreference, ) {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession() = userPreference.getSession()

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(userPreference: UserPreference): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference)
            }.also { instance = it }
    }
}
