package com.example.labworkmanager

import androidx.room.Dao
import androidx.room.Entity
import com.example.mvvmapp.shared.AppStrings.Companion.PRODUCT_TABLE_NAME


@Entity(tableName = PRODUCT_TABLE_NAME, primaryKeys = ["id"])
data class Product(
    val id: Long,
    val title: String,
    val category: String,
    val price: Double,
    val thumbnail: String,
)