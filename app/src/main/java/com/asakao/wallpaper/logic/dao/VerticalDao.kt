package com.asakao.wallpaper.logic.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.asakao.wallpaper.logic.model.Vertical

@Dao
interface VerticalDao {

    @Query("SELECT * FROM Vertical")
    fun getAllVertical(): List<Vertical>

    @Insert
    fun insertVertical(vertical: Vertical): Long

    @Query("select * from Vertical where id = :id")
    fun getVerticalById(id: String): Vertical

    @Query("delete from Vertical where id = :id")
    fun deleteVerticalById(id: String): Int

}