package com.example.mvvmapp.network

import com.example.mvvmapp.shared.AppStrings.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ProductClient {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val productsAPIService: ProductsService by lazy {
        retrofit.create(ProductsService::class.java)
    }
}
