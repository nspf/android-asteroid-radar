package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.database.AsteroidDatabaseDao
import com.udacity.asteroidradar.repository.AsteroidRepository

class MainViewModelFactory(
    private val dao: AsteroidDatabaseDao,
    private val app: Application,
    private val repository: AsteroidRepository
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(dao, app, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}