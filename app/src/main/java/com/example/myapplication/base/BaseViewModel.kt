package com.example.myapplication.base

import androidx.lifecycle.ViewModel
import com.example.myapplication.injection.component.DaggerViewModelInjector
import com.example.myapplication.injection.module.NetworkModule
import com.example.myapplication.ui.PostListViewModel

abstract class BaseViewModel : ViewModel() {
    private val injector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()
    init {
        inject()
    }

    private fun inject() {
        when (this){
            is PostListViewModel -> injector.inject(this)
        }
    }
}