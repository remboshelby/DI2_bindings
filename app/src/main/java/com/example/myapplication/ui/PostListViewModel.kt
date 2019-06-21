package com.example.myapplication.ui

import android.telephony.euicc.DownloadableSubscription
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.R
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.model.Post
import com.example.myapplication.model.PostDao
import com.example.myapplication.network.PostApi
import com.example.myapplication.ui.post.PostListAdapter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class PostListViewModel(private val postDao: PostDao) : BaseViewModel() {
    @Inject
    lateinit var postApi: PostApi

    var errorMessage : MutableLiveData<Int> = MutableLiveData()
    val postListAdapter: PostListAdapter = PostListAdapter()

    val errorClickListner = View.OnClickListener { loadPosts() }

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    private lateinit var subscription: Disposable

    init {
        loadPosts()
    }

    private fun loadPosts(){
        subscription = Observable.fromCallable { postDao.all }
            .concatMap {
                    dbPostList ->
                if(dbPostList.isEmpty())
                    postApi.getPosts().concatMap {
                        apiPostList-> postDao.insertAll(*apiPostList.toTypedArray())
                        Observable.just(apiPostList)
                    }
                else
                    Observable.just(dbPostList)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrievePostListStart() }
            .doOnTerminate { onRetrievePostListFinish() }
            .subscribe(
                { result -> onRetrievePostListSuccess(result) },
                { onRetrievePostListError() }
            )
    }

    private fun onRetrievePostListStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrievePostListFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrievePostListSuccess(postList:List<Post>) {
        postListAdapter.updatePostList(postList)
    }

    private fun onRetrievePostListError() {
        errorMessage.value = R.string.post_error
    }
    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }
}