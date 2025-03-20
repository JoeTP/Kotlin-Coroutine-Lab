package com.example.mvvmapp.data.local

import com.example.labworkmanager.Product
import kotlinx.coroutines.flow.Flow

interface LocalProductDataSource {
    suspend fun getFavoriteProducts(): Flow<List<Product>?>
    suspend fun addToFavorite(product: Product)
    suspend fun deleteFromFavorite(product: Product)
}