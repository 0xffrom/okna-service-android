package com.goga133.oknaservice.models.product

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.goga133.oknaservice.models.product.Product

@Dao
interface ProductDao {
    @Query("SELECT * FROM product")
    fun getAll(): LiveData<List<Product>>

    @Insert
    fun insert(product: Product)

    @Query("DELETE FROM product")
    fun deleteAll()

    @Delete
    fun delete(product: Product)
}
