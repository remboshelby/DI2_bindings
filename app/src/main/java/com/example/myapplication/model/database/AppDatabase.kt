package com.example.myapplication.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.model.Post
import com.example.myapplication.model.PostDao

@Database(entities = arrayOf(Post::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao() : PostDao
}