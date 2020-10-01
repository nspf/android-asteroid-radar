package com.udacity.asteroidradar.api

object NasaApi {
    val retrofitService : AsteroidsApiService by lazy {
        retrofit.create(AsteroidsApiService::class.java)
    }

    val retrofitMoshiService : AsteroidsApiService by lazy {
        retrofitMoshi.create(AsteroidsApiService::class.java)
    }
}