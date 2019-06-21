package com.example.myapplication.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PostDao {
    @get:Query("Select * from post")
    val all : List<Post>

    @Insert
    fun insertAll(vararg post: Post)
}