package com.example.myapplication.core.db

import android.provider.MediaStore
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(), version = 1)
abstract class AppDatabase: RoomDatabase() {
}