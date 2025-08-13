package com.daniella.recyclerviews

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//Converter factory - Translates the json files to Kotlin objects

object ApiClient {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> buildApiClient(apiInterface: Class<T>): T {
        return retrofit.create(apiInterface)
    }
}

