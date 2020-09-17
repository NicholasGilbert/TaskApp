package com.example.myapplication.helper

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.data.RoomTask

@Database(entities = arrayOf(RoomTask::class), exportSchema = false, version = 1)
abstract class DataDatabase : RoomDatabase() {
    abstract fun DataDAO(): DataDAO

    companion object{
        @Volatile
        lateinit var INSTANCE: DataDatabase

        val LOCK = Any()

        operator fun invoke(context: Context)= INSTANCE ?: synchronized(LOCK){
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            DataDatabase::class.java, "todo-list.db").build()
    }
}