package com.example.myapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_table")
class RoomTask (@PrimaryKey
                @ColumnInfo(name = "time") var time: String,
                @ColumnInfo(name = "name") var name: String,
                @ColumnInfo(name = "note") var note: String)