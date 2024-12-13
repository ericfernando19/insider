package com.dicoding.malanginsider.data.di

import android.content.Context
import com.dicoding.malanginsider.data.pref.UserPreference
import com.dicoding.malanginsider.data.pref.dataStore
import com.dicoding.malanginsider.data.repository.UserRepository

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}