package com.example.myapplication.ui.post

import androidx.lifecycle.MutableLiveData
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.model.Post

class PostViewModel : BaseViewModel() {
    private val postTitle = MutableLiveData<String>()
    private val postBody = MutableLiveData<String>()

    fun bind (post : Post){
        postTitle.value = post.title
        postBody.value = post.body
    }
    fun getPostTitle() : MutableLiveData<String>{
        return postTitle
    }
    fun getPostBody():MutableLiveData<String>{
        return postBody
    }

}