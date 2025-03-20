package com.example.mvvmapp.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.labworkmanager.Product
import com.example.mvvmapp.shared.AppStrings.Companion.PRODUCT_TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM ${PRODUCT_TABLE_NAME}")
    fun getProducts(): Flow<List<Product>?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToFavorite(product: Product)

    @Delete
    suspend fun deleteFromFavorite(product: Product)

}