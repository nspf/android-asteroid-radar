package com.udacity.asteroidradar.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.Constants.BASE_URL
import com.udacity.asteroidradar.model.PictureOfDay
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//creating a Network Interceptor to add api_key in all the request as authInterceptor
private val interceptor = Interceptor { chain ->
    val url = chain.request().url().newBuilder().addQueryParameter("api_key", API_KEY).build()
    val request = chain.request()
        .newBuilder()
        .url(url)
        .build()
    chain.proceed(request)
}

// we are creating a networking client using OkHttp and add our authInterceptor.
private val apiClient = OkHttpClient().newBuilder().addInterceptor(interceptor).build()

val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(apiClient)
    .addConverterFactory(ScalarsConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()

val moshi: Moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val retrofitMoshi: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(apiClient)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()

interface AsteroidsApiService {

    @GET("neo/rest/v1/feed")
    fun getAsteroids(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String
    ): Deferred<String>

    @GET("planetary/apod")
    fun getPictureOfDay(): Deferred<PictureOfDay>
}