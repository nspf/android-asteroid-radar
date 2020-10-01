package com.udacity.asteroidradar.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.model.Asteroid

class DetailViewModelFactory(
    val asteroid: Asteroid
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(asteroid) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}