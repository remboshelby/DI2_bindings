package com.example.myapplication.network

import com.example.myapplication.model.Post
import io.reactivex.Observable
import retrofit2.http.GET

interface PostApi {
    @GET("/posts")
    fun getPosts() : Observable<List<Post>>
}