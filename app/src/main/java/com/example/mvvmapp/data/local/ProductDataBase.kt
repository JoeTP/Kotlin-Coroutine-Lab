package com.example.mvvmapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.labworkmanager.Product
import com.example.mvvmapp.shared.AppStrings.Companion.DATABASE_NAME

@Database(entities = [Product::class], version = 1)
abstract class ProductDataBase : RoomDatabase() {

    abstract fun getProductDao(): ProductDao

    companion object {
        private var instance: ProductDataBase? = null

        fun getInstance(context: Context): ProductDataBase {
            return instance ?: synchronized(this) {
                val inst = Room.databaseBuilder(
                    context.applicationContext, ProductDataBase::class.java, DATABASE_NAME
                ).build()
                instance = inst
                inst
            }
        }
    }
}