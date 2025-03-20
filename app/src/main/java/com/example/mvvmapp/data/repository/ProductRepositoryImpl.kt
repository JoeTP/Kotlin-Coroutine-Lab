package com.example.mvvmapp.data.repository

import com.example.labworkmanager.Product
import com.example.mvvmapp.data.local.LocalProductDataSourceImpl
import com.example.mvvmapp.data.remote.RemoteProductDataSourceImpl
import kotlinx.coroutines.flow.Flow

class ProductRepositoryImp private constructor(
    private val remoteDataSource: RemoteProductDataSourceImpl,
    private val localDataSource: LocalProductDataSourceImpl
) : ProductRepository {

    companion object {
        @Volatile
        private var instance: ProductRepositoryImp? = null

        fun getInstance(
            remoteDataSource: RemoteProductDataSourceImpl,
            localDataSource: LocalProductDataSourceImpl
        ): ProductRepositoryImp {
            return instance ?: synchronized(this) {
                instance ?: ProductRepositoryImp(remoteDataSource, localDataSource).also {
                    instance = it
                }
            }
        }
    }

    override suspend fun getProducts(isOnline: Boolean): Flow<List<Product>?> {
        return if(isOnline){
            remoteDataSource.getProducts()
//            localDataSource.getFavoriteProducts()
        }else{
            localDataSource.getFavoriteProducts()
        }
    }

    override suspend fun addProduct(p: Product) {
        localDataSource.addToFavorite(p)
    }


    suspend override fun deleteProduct(p: Product) {
        localDataSource.deleteFromFavorite(p)
    }
}