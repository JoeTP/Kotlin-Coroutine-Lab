package com.example.mvvmapp.data.remote

import com.example.labworkmanager.Product
import kotlinx.coroutines.flow.Flow

interface RemoteProductDataSource {
    fun getProducts(): Flow<List<Product>?>
}