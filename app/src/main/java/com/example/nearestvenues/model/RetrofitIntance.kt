package com.example.nearestvenues.model

import com.example.nearestvenues.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitIntance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
    val restApi : FoursquareAPI by lazy {
        retrofit.create(FoursquareAPI::class.java)
    }
}