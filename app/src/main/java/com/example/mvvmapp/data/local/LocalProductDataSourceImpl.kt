package com.example.mvvmapp.data.local

import com.example.labworkmanager.Product
import com.example.mvvmapp.db.ProductDao
import kotlinx.coroutines.flow.Flow

class LocalProductDataSourceImpl(private val productDao: ProductDao) : LocalProductDataSource {

    companion object {
        private var INSTANCE: LocalProductDataSourceImpl? = null
        fun getInstance(productDao: ProductDao): LocalProductDataSourceImpl {
            if (INSTANCE == null) {
                INSTANCE = LocalProductDataSourceImpl(productDao)
            }
            return INSTANCE!!
        }
    }

    override suspend fun getFavoriteProducts(): Flow<List<Product>?> {
        return productDao.getProducts()
    }

    override suspend fun addToFavorite(product: Product) {
        return productDao.addToFavorite(product)
    }

    override suspend fun deleteFromFavorite(product: Product) {
        return productDao.deleteFromFavorite(product)
    }
}
