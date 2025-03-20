package com.example.mvvmapp.data.remote

import com.example.labworkmanager.Product
import com.example.mvvmapp.network.ProductsService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteProductDataSourceImpl(private val productService: ProductsService) : RemoteProductDataSource {

    companion object {
        @Volatile
        private var instance: RemoteProductDataSourceImpl? = null

        fun getInstance(productService: ProductsService): RemoteProductDataSourceImpl {
            return instance ?: synchronized(this) {
                instance ?: RemoteProductDataSourceImpl(productService).also {
                    instance = it
                }
            }
        }
    }

    override fun getProducts(): Flow<List<Product>?> = flow{
        emit(productService.getProducts().body()?.products)
    }

}