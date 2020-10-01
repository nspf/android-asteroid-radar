package com.udacity.asteroidradar

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.AsteroidDatabaseDao
import com.udacity.asteroidradar.database.AsteroidEntity
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AsteroidDatabaseTest {

    private lateinit var asteroidDatabaseDao: AsteroidDatabaseDao
    private lateinit var db: AsteroidDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, AsteroidDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        asteroidDatabaseDao = db.asteroidDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetAsteroid() {
        val asteroid = AsteroidEntity(
            id=3922358,
            codename="(2019 XG3)",
            closeApproachDate="2020-03-17",
            absoluteMagnitude=18.8,
            estimatedDiameter=1.0328564805,
            relativeVelocity=25.3296817197,
            distanceFromEarth=0.4078227357,
            isPotentiallyHazardous=false
        )
        asteroidDatabaseDao.insert(asteroid)
        val asteroidFromDatabase = asteroidDatabaseDao.getAsteroidByCodeName("(2019 XG3)")
        assertEquals(asteroidFromDatabase.codename, "(2019 XG3)")
    }
}