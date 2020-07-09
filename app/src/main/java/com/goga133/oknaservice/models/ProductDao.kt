package com.goga133.oknaservice.models

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductDao {
    @Query("SELECT * FROM product")
    fun getAll(): List<Product>

    @Insert
    fun insertAll(vararg products: Product)

    @Delete
    fun delete(product: Product)
}
