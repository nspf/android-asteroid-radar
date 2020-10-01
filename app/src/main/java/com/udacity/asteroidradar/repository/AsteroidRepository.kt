package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.api.NasaApi
import com.udacity.asteroidradar.api.NasaApiStatus
import com.udacity.asteroidradar.api.getNextSeventhDayFormattedDate
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.api.NetworkAsteroidContainer
import com.udacity.asteroidradar.model.PictureOfDay
import com.udacity.asteroidradar.api.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidRepository(private val database: AsteroidDatabase) {

    private val _status = MutableLiveData<NasaApiStatus>()
    val status: LiveData<NasaApiStatus>
        get() = _status

    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDatabaseDao.getAsteroids()) {
            it.asDomainModel()
        }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val asteroidList = getAsteroids()
            database.asteroidDatabaseDao.updateAsteroidList(
                NetworkAsteroidContainer(
                    asteroidList
                ).asDatabaseModel())
        }
    }

    private suspend fun getAsteroids(): List<Asteroid> {
        val getAsteroidsDeferred = NasaApi.retrofitService
            .getAsteroids("", getNextSeventhDayFormattedDate())
        return try {
            _status.postValue(NasaApiStatus.LOADING)
            val result = getAsteroidsDeferred.await()
            _status.postValue(NasaApiStatus.DONE)
            parseAsteroidsJsonResult(JSONObject(result))
        } catch (e: Exception) {
            _status.postValue(NasaApiStatus.ERROR)
            e.printStackTrace()
            ArrayList()
        }
    }

    suspend fun getPictureOfDay(): PictureOfDay? {
        val getPictureOfDayDeferred = NasaApi.retrofitMoshiService.getPictureOfDay()
        return try {
            val result = getPictureOfDayDeferred.await()
            result
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}