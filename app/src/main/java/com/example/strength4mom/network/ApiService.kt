package com.example.strength4mom.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query

// Configure OkHttp client with logging
val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY // Can be: NONE, BASIC, HEADERS, BODY
}

val parameterLoggingInterceptor = Interceptor { chain ->
    val request = chain.request()
    val url = request.url
    Log.d("RetrofitParams", "Request URL: $url")
    chain.proceed(request)
}

val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(loggingInterceptor)
    .addInterceptor(parameterLoggingInterceptor)
    .build()

object RetrofitInstance {
    private const val BASE_URL = "https://api.api-ninjas.com/v1/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient) // Add the custom OkHttp client
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

interface ApiService {
    @GET("exercises")
    suspend fun getExercises(
        @Header("X-Api-Key") apiKey: String,
        @Query("muscle") muscle: String? = null,
        @Query("name") name: String? = null,
        @Query("type") type: String? = null
    ): Response<List<Exercise>>
}
