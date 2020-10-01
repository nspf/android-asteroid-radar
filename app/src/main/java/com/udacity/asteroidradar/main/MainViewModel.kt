package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.model.PictureOfDay
import com.udacity.asteroidradar.database.AsteroidDatabaseDao
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(
    dao: AsteroidDatabaseDao,
    application: Application,
    repository: AsteroidRepository) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    val asteroids = repository.asteroids
    val status = repository.status

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    private val _navigateToSelectedAsteroid = MutableLiveData<Asteroid>()

    val navigateToSelectedAsteroid: LiveData<Asteroid>
        get() = _navigateToSelectedAsteroid

    fun displayAsteroidDetails(asteroid: Asteroid) {
        _navigateToSelectedAsteroid.value = asteroid
    }

    fun displayAsteroidDetailsComplete() {
        _navigateToSelectedAsteroid.value = null
    }

    init {
        coroutineScope.launch {
            repository.refreshAsteroids()
            _pictureOfDay.value = repository.getPictureOfDay()
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}