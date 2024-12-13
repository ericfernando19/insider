package com.dicoding.malanginsider.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.malanginsider.data.pref.UserModel
import com.dicoding.malanginsider.data.repository.UserRepository
import kotlinx.coroutines.launch

class HomeViewModel (private val repository: UserRepository) : ViewModel() {
    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}