package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AsteroidDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(asteroid: AsteroidEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(asteroids: List<AsteroidEntity>)

    @Transaction
    fun updateAsteroidList(asteroids: List<AsteroidEntity>) {
        deleteAll()
        insertAll(asteroids)
    }

    @Query("DELETE FROM asteroid_table")
    fun deleteAll()

    @Query("SELECT * from asteroid_table order by close_approach_date ASC")
    fun getAsteroids(): LiveData<List<AsteroidEntity>>

    @Query("SELECT * from asteroid_table where code_name = :codename")
    fun getAsteroidByCodeName(codename: String): AsteroidEntity
}