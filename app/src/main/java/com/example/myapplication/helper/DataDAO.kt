package com.example.myapplication.helper

import androidx.room.*
import com.example.myapplication.data.RoomTask

@Dao
interface DataDAO {
    @Query("SELECT * from app_table")
    fun getData(): List<RoomTask>

    @Update
    fun update(data: List<RoomTask>)

    @Insert
    fun insert(data: List<RoomTask>)

    @Query("DELETE FROM app_table WHERE time = :data")
    fun delete(data: String)
}