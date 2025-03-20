package com.example.mvvmapp.network

import com.example.mvvmapp.data.model.ProductResponse
import retrofit2.Response
import retrofit2.http.GET


interface ProductsService {

    @GET("products")
    suspend fun getProducts() : Response<ProductResponse?>
}
