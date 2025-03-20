package com.example.mvvmapp.data.repository

import com.example.labworkmanager.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProducts (isOnline : Boolean): Flow<List<Product>?>
    suspend fun deleteProduct(p: Product)
    suspend fun addProduct (p: Product)
}