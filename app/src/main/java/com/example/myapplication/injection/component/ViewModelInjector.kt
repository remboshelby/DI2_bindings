package com.example.myapplication.injection.component

import com.example.myapplication.injection.module.NetworkModule
import com.example.myapplication.ui.PostListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {
    fun inject (postListViewModel: PostListViewModel)

    @Component.Builder
    interface Builder{
        fun build() : ViewModelInjector

        fun networkModule(networkModule: NetworkModule) : Builder
    }

}